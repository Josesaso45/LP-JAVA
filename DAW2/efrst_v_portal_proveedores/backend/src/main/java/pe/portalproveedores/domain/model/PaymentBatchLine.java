package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record PaymentBatchLine(
        int erpInvoiceId,
        String invoiceReference,
        String beneficiaryRuc,
        String beneficiaryName,
        String destinationAccount,
        String cci,
        BigDecimal amount,
        String reference
) {
    public PaymentBatchLine {
        amount = amount != null ? amount : BigDecimal.ZERO;
    }
}
