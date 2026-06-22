package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import pe.portalproveedores.domain.model.ErpInvoiceReference;
import pe.portalproveedores.domain.model.InvoiceSubmissionResult;
import pe.portalproveedores.domain.model.MatchDiscrepancy;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;

import java.util.List;

public record InvoiceUploadResponse(
        String submissionId,
        String invoiceReference,
        String supplierRuc,
        String purchaseOrderNumber,
        MatchStatusResponse matchResult,
        ErpReferenceResponse erpReference,
        boolean submittedToErp
) {
    public static InvoiceUploadResponse from(InvoiceSubmissionResult result) {
        return new InvoiceUploadResponse(
                result.submissionId(),
                result.invoice().getFullNumber(),
                result.invoice().getSupplierRuc(),
                result.invoice().getPurchaseOrderNumber(),
                MatchStatusResponse.from(result.matchResult()),
                result.erpReference() != null ? ErpReferenceResponse.from(result.erpReference()) : null,
                result.submittedToErp()
        );
    }
}
