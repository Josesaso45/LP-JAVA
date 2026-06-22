package pe.portalproveedores.domain.model;

public record ValidateMatchCommand(
        String submissionId,
        SupplierInvoice invoice,
        String purchaseOrderNumber
) {
}
