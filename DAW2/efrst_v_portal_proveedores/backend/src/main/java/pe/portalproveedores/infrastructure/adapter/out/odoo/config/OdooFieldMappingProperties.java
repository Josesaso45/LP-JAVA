package pe.portalproveedores.infrastructure.adapter.out.odoo.config;

public class OdooFieldMappingProperties {

    private String detractionCode = "l10n_pe_detraction_code";
    private String detractionPercent = "l10n_pe_detraction_percent";
    private String affectationReason = "l10n_pe_edi_affectation_reason";
    private String documentType = "l10n_latam_document_type_id";

    public String getDetractionCode() {
        return detractionCode;
    }

    public void setDetractionCode(String detractionCode) {
        this.detractionCode = detractionCode;
    }

    public String getDetractionPercent() {
        return detractionPercent;
    }

    public void setDetractionPercent(String detractionPercent) {
        this.detractionPercent = detractionPercent;
    }

    public String getAffectationReason() {
        return affectationReason;
    }

    public void setAffectationReason(String affectationReason) {
        this.affectationReason = affectationReason;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}
