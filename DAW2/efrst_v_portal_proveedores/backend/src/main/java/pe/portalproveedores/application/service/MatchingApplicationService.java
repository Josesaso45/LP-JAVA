package pe.portalproveedores.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.portalproveedores.application.port.in.ValidateThreeWayMatchUseCase;
import pe.portalproveedores.application.port.out.ErpPurchaseOrderPort;
import pe.portalproveedores.application.port.out.InvoiceSubmissionRepositoryPort;
import pe.portalproveedores.domain.exception.DomainException;
import pe.portalproveedores.domain.model.PurchaseOrderSnapshot;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;
import pe.portalproveedores.domain.model.ValidateMatchCommand;
import pe.portalproveedores.domain.service.ThreeWayMatchingService;

@Service
@Transactional(readOnly = true)
public class MatchingApplicationService implements ValidateThreeWayMatchUseCase {

    private final ThreeWayMatchingService matchingService;
    private final ErpPurchaseOrderPort purchaseOrderPort;
    private final InvoiceSubmissionRepositoryPort submissionRepository;

    public MatchingApplicationService(ThreeWayMatchingService matchingService,
                                      ErpPurchaseOrderPort purchaseOrderPort,
                                      InvoiceSubmissionRepositoryPort submissionRepository) {
        this.matchingService = matchingService;
        this.purchaseOrderPort = purchaseOrderPort;
        this.submissionRepository = submissionRepository;
    }

    @Override
    @Transactional
    public ThreeWayMatchResult validate(ValidateMatchCommand command) {
        if (command.submissionId() != null) {
            return submissionRepository.findById(command.submissionId())
                    .map(r -> r.matchResult())
                    .orElseThrow(() -> new DomainException("Submission no encontrada: " + command.submissionId()));
        }

        String poNumber = command.purchaseOrderNumber();
        PurchaseOrderSnapshot po = purchaseOrderPort.findByName(poNumber).orElse(null);
        ThreeWayMatchResult result = matchingService.validate(command.invoice(), po);
        result.setPurchaseOrderNumber(poNumber);
        return result;
    }
}
