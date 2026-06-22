package pe.portalproveedores.application.port.in.command;

import pe.portalproveedores.domain.model.SupplierInvoice;

public record ValidateMatchCommand(
        SupplierInvoice invoice,
        String purchaseOrderNumber
) {}
