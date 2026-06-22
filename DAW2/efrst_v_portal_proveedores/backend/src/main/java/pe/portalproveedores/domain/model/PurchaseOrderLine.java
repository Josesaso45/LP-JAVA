package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record PurchaseOrderLine(
        int lineId,
        String productCode,
        String description,
        BigDecimal orderedQuantity,
        BigDecimal receivedQuantity,
        BigDecimal invoicedQuantity,
        BigDecimal unitPrice,
        Integer productId
) {
    public PurchaseOrderLine {
        orderedQuantity = orderedQuantity != null ? orderedQuantity : BigDecimal.ZERO;
        receivedQuantity = receivedQuantity != null ? receivedQuantity : BigDecimal.ZERO;
        invoicedQuantity = invoicedQuantity != null ? invoicedQuantity : BigDecimal.ZERO;
        unitPrice = unitPrice != null ? unitPrice : BigDecimal.ZERO;
    }

    public BigDecimal availableToInvoice() {
        return receivedQuantity.subtract(invoicedQuantity);
    }
}
