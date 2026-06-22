package pe.portalproveedores.infrastructure.adapter.in.web.dto;

public record GenerateBatchResponse(
        String batchId,
        String fileName,
        int lineCount
) {}
