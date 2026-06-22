package pe.portalproveedores.infrastructure.adapter.out.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pe.portalproveedores.application.port.out.InvoiceSubmissionRepositoryPort;
import pe.portalproveedores.domain.exception.DomainException;
import pe.portalproveedores.domain.model.ErpInvoiceReference;
import pe.portalproveedores.domain.model.InvoiceSubmissionResult;
import pe.portalproveedores.domain.model.MatchDiscrepancy;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class InvoiceSubmissionRepositoryAdapter implements InvoiceSubmissionRepositoryPort {

    private final InvoiceSubmissionJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;

    public InvoiceSubmissionRepositoryAdapter(InvoiceSubmissionJpaRepository jpaRepository,
                                              ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public InvoiceSubmissionResult save(InvoiceSubmissionResult result) {
        InvoiceSubmissionEntity entity = new InvoiceSubmissionEntity();
        entity.setId(result.submissionId() != null ? result.submissionId() : UUID.randomUUID().toString());
        entity.setInvoiceReference(result.invoice().getFullNumber());
        entity.setPurchaseOrderNumber(result.invoice().getPurchaseOrderNumber());
        entity.setSupplierRuc(result.invoice().getSupplierRuc());
        entity.setMatchStatus(result.matchResult().getStatus().name());
        entity.setMatchDiscrepanciesJson(serializeDiscrepancies(result.matchResult().getDiscrepancies()));
        entity.setSubmittedToErp(result.submittedToErp());
        entity.setSubmittedBy(null);
        if (result.erpReference() != null) {
            entity.setErpInvoiceId(result.erpReference().erpId());
        }
        entity.setCreatedAt(LocalDateTime.now());
        jpaRepository.save(entity);

        return new InvoiceSubmissionResult(
                entity.getId(),
                result.invoice(),
                result.matchResult(),
                result.erpReference(),
                result.submittedToErp()
        );
    }

    @Override
    public Optional<InvoiceSubmissionResult> findById(String submissionId) {
        return jpaRepository.findById(submissionId).map(this::toResult);
    }

    @Override
    public void updateMatchResult(String submissionId, ThreeWayMatchResult matchResult) {
        InvoiceSubmissionEntity entity = jpaRepository.findById(submissionId)
                .orElseThrow(() -> new DomainException("Submission no encontrada: " + submissionId));
        entity.setMatchStatus(matchResult.getStatus().name());
        entity.setMatchDiscrepanciesJson(serializeDiscrepancies(matchResult.getDiscrepancies()));
        jpaRepository.save(entity);
    }

    private InvoiceSubmissionResult toResult(InvoiceSubmissionEntity entity) {
        SupplierInvoice invoice = new SupplierInvoice();
        invoice.setPurchaseOrderNumber(entity.getPurchaseOrderNumber());
        invoice.setSupplierRuc(entity.getSupplierRuc());
        if (entity.getInvoiceReference() != null && entity.getInvoiceReference().contains("-")) {
            String[] parts = entity.getInvoiceReference().split("-", 2);
            invoice.setSerialNumber(parts[0]);
            invoice.setInvoiceNumber(parts[1]);
        }

        ThreeWayMatchResult matchResult = new ThreeWayMatchResult();
        matchResult.setStatus(pe.portalproveedores.domain.model.MatchStatus.valueOf(entity.getMatchStatus()));
        matchResult.setPurchaseOrderNumber(entity.getPurchaseOrderNumber());
        matchResult.setDiscrepancies(deserializeDiscrepancies(entity.getMatchDiscrepanciesJson()));

        ErpInvoiceReference erpRef = entity.getErpInvoiceId() != null
                ? new ErpInvoiceReference(entity.getErpInvoiceId(), entity.getInvoiceReference(), "draft")
                : null;

        return new InvoiceSubmissionResult(
                entity.getId(),
                invoice,
                matchResult,
                erpRef,
                entity.isSubmittedToErp()
        );
    }

    private String serializeDiscrepancies(List<MatchDiscrepancy> discrepancies) {
        try {
            return objectMapper.writeValueAsString(discrepancies);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    private List<MatchDiscrepancy> deserializeDiscrepancies(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }
}
