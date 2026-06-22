package pe.portalproveedores.application.port.in.result;

import pe.portalproveedores.domain.model.ErpInvoiceReference;
import pe.portalproveedores.domain.model.MatchStatus;
import pe.portalproveedores.domain.model.MatchDiscrepancy;
import pe.portalproveedores.domain.model.SupplierInvoice;

import java.util.List;

public record InvoiceSubmissionResult(
        String submissionId,
        SupplierInvoice invoice,
        MatchStatus matchStatus,
        List<MatchDiscrepancy> discrepancies,
        ErpInvoiceReference erpReference,
        boolean erpCreated
) {}
