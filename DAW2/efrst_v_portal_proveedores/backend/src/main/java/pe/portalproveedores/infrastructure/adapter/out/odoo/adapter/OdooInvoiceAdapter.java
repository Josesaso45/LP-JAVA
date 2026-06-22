package pe.portalproveedores.infrastructure.adapter.out.odoo.adapter;

import org.springframework.stereotype.Component;
import pe.portalproveedores.application.port.out.ErpInvoicePort;
import pe.portalproveedores.domain.exception.ErpIntegrationException;
import pe.portalproveedores.domain.model.ErpInvoiceReference;
import pe.portalproveedores.domain.model.ErpInvoiceStatus;
import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.infrastructure.adapter.out.odoo.client.OdooJsonRpcClient;
import pe.portalproveedores.infrastructure.adapter.out.odoo.mapper.AccountMovePayloadBuilder;
import pe.portalproveedores.infrastructure.adapter.out.odoo.mapper.OdooPartnerResolver;
import pe.portalproveedores.infrastructure.adapter.out.odoo.mapper.OdooProductResolver;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OdooInvoiceAdapter implements ErpInvoicePort {

    private final OdooJsonRpcClient odooClient;
    private final AccountMovePayloadBuilder payloadBuilder;
    private final OdooPartnerResolver partnerResolver;
    private final OdooProductResolver productResolver;

    public OdooInvoiceAdapter(OdooJsonRpcClient odooClient,
                              AccountMovePayloadBuilder payloadBuilder,
                              OdooPartnerResolver partnerResolver,
                              OdooProductResolver productResolver) {
        this.odooClient = odooClient;
        this.payloadBuilder = payloadBuilder;
        this.partnerResolver = partnerResolver;
        this.productResolver = productResolver;
    }

    @Override
    public ErpInvoiceReference createSupplierInvoice(SupplierInvoice invoice) {
        int partnerId = invoice.getErpPartnerId() != null
                ? invoice.getErpPartnerId()
                : partnerResolver.resolveByRuc(invoice.getSupplierRuc())
                .orElseThrow(() -> new ErpIntegrationException(
                        "Proveedor no encontrado en Odoo: " + invoice.getSupplierRuc()));

        resolveProductIds(invoice);
        int currencyId = resolveCurrencyId(invoice.getCurrencyCode());
        Map<String, Object> payload = payloadBuilder.build(invoice, partnerId, currencyId);
        int moveId = odooClient.create("account.move", payload);
        return new ErpInvoiceReference(moveId, invoice.getFullNumber(), "draft");
    }

    @Override
    public Optional<ErpInvoiceStatus> findByReference(String serialNumber, String invoiceNumber) {
        String ref = serialNumber + "-" + invoiceNumber;
        List<Map<String, Object>> moves = odooClient.searchRead(
                "account.move",
                List.of(
                        List.of("move_type", "=", "in_invoice"),
                        List.of("ref", "=", ref)
                ),
                List.of("id", "ref", "state", "payment_state", "amount_residual")
        );
        if (moves.isEmpty()) {
            return Optional.empty();
        }
        Map<String, Object> move = moves.getFirst();
        return Optional.of(new ErpInvoiceStatus(
                ((Number) move.get("id")).intValue(),
                (String) move.get("ref"),
                (String) move.get("state"),
                (String) move.get("payment_state"),
                new BigDecimal(move.get("amount_residual").toString())
        ));
    }

    private void resolveProductIds(SupplierInvoice invoice) {
        for (InvoiceLine line : invoice.getLines()) {
            if (line.getErpProductId() == null && line.getProductCode() != null) {
                productResolver.resolveByDefaultCode(line.getProductCode())
                        .ifPresent(line::setErpProductId);
            }
        }
    }

    private int resolveCurrencyId(String currencyCode) {
        String code = currencyCode != null ? currencyCode : "PEN";
        List<Map<String, Object>> currencies = odooClient.searchRead(
                "res.currency",
                List.of(List.of("name", "=", code)),
                List.of("id")
        );
        if (currencies.isEmpty()) {
            return 1;
        }
        return ((Number) currencies.getFirst().get("id")).intValue();
    }
}
