package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.InvoiceSubmissionResult;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;

import java.util.Optional;

public interface InvoiceSubmissionRepositoryPort {

    InvoiceSubmissionResult save(InvoiceSubmissionResult result);

    Optional<InvoiceSubmissionResult> findById(String submissionId);

    void updateMatchResult(String submissionId, ThreeWayMatchResult matchResult);
}
