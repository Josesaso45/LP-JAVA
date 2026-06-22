package pe.portalproveedores.domain.model;

public record SubmitInvoiceCommand(
        byte[] xmlContent,
        String purchaseOrderNumber,
        String submittedBy
) {
}
