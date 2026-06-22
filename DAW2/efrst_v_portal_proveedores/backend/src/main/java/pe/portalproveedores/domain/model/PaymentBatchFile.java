package pe.portalproveedores.domain.model;

public record PaymentBatchFile(
        PaymentBatch batch,
        GeneratedFile file
) {
}
