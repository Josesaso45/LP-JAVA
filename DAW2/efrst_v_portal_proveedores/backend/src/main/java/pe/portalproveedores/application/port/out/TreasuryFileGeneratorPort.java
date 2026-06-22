package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.BankFormat;
import pe.portalproveedores.domain.model.GeneratedFile;
import pe.portalproveedores.domain.model.PaymentBatch;

public interface TreasuryFileGeneratorPort {

    GeneratedFile generate(PaymentBatch batch, BankFormat format);
}
