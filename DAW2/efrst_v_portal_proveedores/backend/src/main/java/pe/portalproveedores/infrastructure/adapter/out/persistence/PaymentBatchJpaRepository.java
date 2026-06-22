package pe.portalproveedores.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentBatchJpaRepository extends JpaRepository<PaymentBatchEntity, String> {
}
