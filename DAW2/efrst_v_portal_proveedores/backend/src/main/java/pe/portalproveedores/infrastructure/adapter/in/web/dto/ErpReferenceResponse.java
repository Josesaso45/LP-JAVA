package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import pe.portalproveedores.domain.model.ErpInvoiceReference;

public record ErpReferenceResponse(int erpId, String reference, String state) {
    public static ErpReferenceResponse from(ErpInvoiceReference ref) {
        return new ErpReferenceResponse(ref.erpId(), ref.reference(), ref.state());
    }
}
