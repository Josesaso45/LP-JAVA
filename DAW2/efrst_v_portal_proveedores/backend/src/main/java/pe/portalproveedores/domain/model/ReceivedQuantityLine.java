package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record ReceivedQuantityLine(
        int purchaseOrderLineId,
        String productCode,
        BigDecimal qtyReceived,
        BigDecimal qtyInvoiced,
        BigDecimal unitPrice
) {
    public ReceivedQuantityLine {
        qtyReceived = qtyReceived != null ? qtyReceived : BigDecimal.ZERO;
        qtyInvoiced = qtyInvoiced != null ? qtyInvoiced : BigDecimal.ZERO;
        unitPrice = unitPrice != null ? unitPrice : BigDecimal.ZERO;
    }

    public BigDecimal availableToInvoice() {
        return qtyReceived.subtract(qtyInvoiced);
    }
}
