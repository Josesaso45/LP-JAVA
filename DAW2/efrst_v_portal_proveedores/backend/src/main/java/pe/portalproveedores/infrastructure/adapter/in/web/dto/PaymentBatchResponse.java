package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import pe.portalproveedores.domain.model.PaymentBatchFile;

public record PaymentBatchResponse(
        String batchId,
        String fileName,
        int lineCount,
        String totalAmount
) {
    public static PaymentBatchResponse from(PaymentBatchFile batchFile) {
        return new PaymentBatchResponse(
                batchFile.batch().getId(),
                batchFile.file().fileName(),
                batchFile.batch().lineCount(),
                batchFile.batch().totalAmount().toPlainString()
        );
    }
}
