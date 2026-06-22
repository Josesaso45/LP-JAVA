package pe.portalproveedores.application.port.in.result;

public record PaymentBatchFile(
        String batchId,
        String fileName,
        String contentType,
        byte[] content
) {}
