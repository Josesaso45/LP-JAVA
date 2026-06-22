package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record ErpInvoiceStatus(
        int erpId,
        String reference,
        String state,
        String paymentState,
        BigDecimal amountResidual
) {
}
