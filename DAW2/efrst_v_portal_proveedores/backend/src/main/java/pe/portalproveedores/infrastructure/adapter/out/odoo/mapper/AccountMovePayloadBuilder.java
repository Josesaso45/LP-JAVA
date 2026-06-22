package pe.portalproveedores.infrastructure.adapter.out.odoo.mapper;

import org.springframework.stereotype.Component;
import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.infrastructure.adapter.out.odoo.config.OdooProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccountMovePayloadBuilder {

    private final OdooProperties odooProperties;

    public AccountMovePayloadBuilder(OdooProperties odooProperties) {
        this.odooProperties = odooProperties;
    }

    public Map<String, Object> build(SupplierInvoice invoice, int partnerId, int currencyId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("move_type", "in_invoice");
        payload.put("partner_id", partnerId);
        payload.put("ref", invoice.getFullNumber());
        payload.put("invoice_date", invoice.getIssueDate() != null ? invoice.getIssueDate().toString() : null);
        payload.put("currency_id", currencyId);

        if (invoice.getErpPurchaseOrderId() != null) {
            payload.put("purchase_id", invoice.getErpPurchaseOrderId());
        }
        if (invoice.getErpDocumentTypeId() != null) {
            payload.put(odooProperties.getFieldMapping().getDocumentType(), invoice.getErpDocumentTypeId());
        }

        payload.put("invoice_line_ids", buildLineCommands(invoice.getLines()));
        return payload;
    }

    private List<Object> buildLineCommands(List<InvoiceLine> lines) {
        List<Object> commands = new ArrayList<>();
        for (InvoiceLine line : lines) {
            commands.add(List.of(0, 0, buildLineMap(line)));
        }
        return commands;
    }

    private Map<String, Object> buildLineMap(InvoiceLine line) {
        Map<String, Object> lineMap = new HashMap<>();
        lineMap.put("name", line.getDescription());
        lineMap.put("quantity", line.getQuantity());
        lineMap.put("price_unit", line.getUnitPrice());

        if (line.getErpProductId() != null) {
            lineMap.put("product_id", line.getErpProductId());
        }
        if (line.getErpTaxId() != null) {
            lineMap.put("tax_ids", List.of(List.of(6, 0, List.of(line.getErpTaxId()))));
        }
        if (line.getAffectationReason() != null) {
            lineMap.put(odooProperties.getFieldMapping().getAffectationReason(), line.getAffectationReason());
        }
        if (line.getDetraction() != null && line.getDetraction().hasDetraction()) {
            lineMap.put(odooProperties.getFieldMapping().getDetractionCode(), line.getDetraction().sunatProductCode());
            lineMap.put(odooProperties.getFieldMapping().getDetractionPercent(), line.getDetraction().percent());
        }
        return lineMap;
    }
}
