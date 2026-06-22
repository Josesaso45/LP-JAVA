package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import pe.portalproveedores.domain.model.OpenVendorBill;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OpenVendorBillResponse(
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
    public static OpenVendorBillResponse from(OpenVendorBill bill) {
        return new OpenVendorBillResponse(
                bill.erpInvoiceId(),
                bill.invoiceReference(),
                bill.supplierRuc(),
                bill.supplierName(),
                bill.bankAccount(),
                bill.cci(),
                bill.amountDue(),
                bill.currencyCode(),
                bill.dueDate()
        );
    }
}
