package pe.portalproveedores.domain.model;

public record InvoiceSubmissionResult(
        String submissionId,
        SupplierInvoice invoice,
        ThreeWayMatchResult matchResult,
        ErpInvoiceReference erpReference,
        boolean submittedToErp
) {
}
