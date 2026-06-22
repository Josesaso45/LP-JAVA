package pe.portalproveedores.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceSubmissionJpaRepository extends JpaRepository<InvoiceSubmissionEntity, String> {
}
