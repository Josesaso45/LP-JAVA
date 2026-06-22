package pe.portalproveedores.application.port.in.command;

public record SubmitInvoiceCommand(
        byte[] xmlContent,
        String purchaseOrderNumber,
        boolean skipErpCreation
) {}
