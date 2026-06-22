package pe.portalproveedores.application.port.in;

import pe.portalproveedores.domain.model.InvoiceSubmissionResult;
import pe.portalproveedores.domain.model.SubmitInvoiceCommand;

public interface SubmitSupplierInvoiceUseCase {

    InvoiceSubmissionResult submit(SubmitInvoiceCommand command);
}
