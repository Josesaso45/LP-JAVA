package pe.portalproveedores.application.port.in;

import pe.portalproveedores.domain.model.GenerateBatchCommand;
import pe.portalproveedores.domain.model.PaymentBatchFile;

public interface GeneratePaymentBatchUseCase {

    PaymentBatchFile generate(GenerateBatchCommand command);
}
