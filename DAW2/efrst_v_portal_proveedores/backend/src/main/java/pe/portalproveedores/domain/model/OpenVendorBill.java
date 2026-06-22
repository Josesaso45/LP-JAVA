package pe.portalproveedores.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OpenVendorBill(
        int erpInvoiceId,
        String invoiceReference,
        String supplierRuc,
        String supplierName,
        String bankAccount,
        String cci,
        BigDecimal amountDue,
        String currencyCode,
        LocalDate dueDate
) {
}
