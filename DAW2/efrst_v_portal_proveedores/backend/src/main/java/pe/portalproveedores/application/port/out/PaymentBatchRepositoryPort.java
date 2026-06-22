package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.PaymentBatch;

import java.util.Optional;

public interface PaymentBatchRepositoryPort {

    String save(PaymentBatch batch, byte[] fileContent, String fileName);

    Optional<StoredBatch> findById(String id);

    record StoredBatch(
            String id,
            PaymentBatch batch,
            byte[] fileContent,
            String fileName,
            String contentType
    ) {}
}
