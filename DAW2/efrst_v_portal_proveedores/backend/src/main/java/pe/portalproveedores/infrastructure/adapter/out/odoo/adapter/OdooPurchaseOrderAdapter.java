package pe.portalproveedores.infrastructure.adapter.out.odoo.adapter;

import org.springframework.stereotype.Component;
import pe.portalproveedores.application.port.out.ErpPurchaseOrderPort;
import pe.portalproveedores.domain.model.PurchaseOrderLine;
import pe.portalproveedores.domain.model.PurchaseOrderSnapshot;
import pe.portalproveedores.domain.model.ReceivedQuantityLine;
import pe.portalproveedores.infrastructure.adapter.out.odoo.client.OdooJsonRpcClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OdooPurchaseOrderAdapter implements ErpPurchaseOrderPort {

    private final OdooJsonRpcClient odooClient;

    public OdooPurchaseOrderAdapter(OdooJsonRpcClient odooClient) {
        this.odooClient = odooClient;
    }

    @Override
    public Optional<PurchaseOrderSnapshot> findByName(String poNumber) {
        List<Map<String, Object>> orders = odooClient.searchRead(
                "purchase.order",
                List.of(List.of("name", "=", poNumber)),
                List.of("id", "name", "state", "invoice_policy", "partner_id")
        );
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        Map<String, Object> order = orders.getFirst();
        PurchaseOrderSnapshot snapshot = new PurchaseOrderSnapshot();
        snapshot.setId(((Number) order.get("id")).intValue());
        snapshot.setName((String) order.get("name"));
        snapshot.setState((String) order.get("state"));
        snapshot.setInvoicePolicy((String) order.get("invoice_policy"));

        resolvePartner(snapshot, order.get("partner_id"));
        snapshot.setLines(loadOrderLines(snapshot.getId()));
        return Optional.of(snapshot);
    }

    @Override
    public List<ReceivedQuantityLine> getReceivedQuantities(int purchaseOrderId) {
        return loadOrderLines(purchaseOrderId).stream()
                .map(line -> new ReceivedQuantityLine(
                        line.lineId(),
                        line.productCode(),
                        line.receivedQuantity(),
                        line.invoicedQuantity(),
                        line.unitPrice()
                ))
                .toList();
    }

    private List<PurchaseOrderLine> loadOrderLines(int purchaseOrderId) {
        List<Map<String, Object>> lines = odooClient.searchRead(
                "purchase.order.line",
                List.of(List.of("order_id", "=", purchaseOrderId)),
                List.of("id", "product_id", "name", "product_qty", "qty_received", "qty_invoiced", "price_unit")
        );
        List<PurchaseOrderLine> result = new ArrayList<>();
        for (Map<String, Object> line : lines) {
            String productCode = extractProductCode(line.get("product_id"));
            result.add(new PurchaseOrderLine(
                    ((Number) line.get("id")).intValue(),
                    productCode,
                    (String) line.get("name"),
                    toBigDecimal(line.get("product_qty")),
                    toBigDecimal(line.get("qty_received")),
                    toBigDecimal(line.get("qty_invoiced")),
                    toBigDecimal(line.get("price_unit")),
                    extractProductId(line.get("product_id"))
            ));
        }
        return result;
    }

    private void resolvePartner(PurchaseOrderSnapshot snapshot, Object partnerField) {
        Integer partnerId = extractProductId(partnerField);
        if (partnerId == null) {
            return;
        }
        List<Map<String, Object>> partners = odooClient.searchRead(
                "res.partner",
                List.of(List.of("id", "=", partnerId)),
                List.of("vat", "name")
        );
        if (!partners.isEmpty()) {
            snapshot.setPartnerRuc((String) partners.getFirst().get("vat"));
            snapshot.setPartnerName((String) partners.getFirst().get("name"));
        }
    }

    private String extractProductCode(Object productField) {
        Integer productId = extractProductId(productField);
        if (productId == null) {
            return null;
        }
        List<Map<String, Object>> products = odooClient.searchRead(
                "product.product",
                List.of(List.of("id", "=", productId)),
                List.of("default_code")
        );
        if (products.isEmpty()) {
            return null;
        }
        return (String) products.getFirst().get("default_code");
    }

    private Integer extractProductId(Object many2one) {
        if (many2one instanceof List<?> list && !list.isEmpty()) {
            return ((Number) list.getFirst()).intValue();
        }
        if (many2one instanceof Number number) {
            return number.intValue();
        }
        return null;
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.toString());
    }
}
