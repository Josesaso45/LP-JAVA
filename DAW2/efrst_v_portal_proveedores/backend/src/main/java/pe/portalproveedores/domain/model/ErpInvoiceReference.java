package pe.portalproveedores.domain.model;

public record ErpInvoiceReference(
        int erpId,
        String reference,
        String state
) {
}
