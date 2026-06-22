package pe.portalproveedores.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_submissions")
public class InvoiceSubmissionEntity {

    @Id
    private String id;

    @Column(name = "invoice_reference")
    private String invoiceReference;

    @Column(name = "purchase_order_number")
    private String purchaseOrderNumber;

    @Column(name = "supplier_ruc")
    private String supplierRuc;

    @Column(name = "match_status")
    private String matchStatus;

    @Lob
    @Column(name = "match_discrepancies_json")
    private String matchDiscrepanciesJson;

    @Column(name = "erp_invoice_id")
    private Integer erpInvoiceId;

    @Column(name = "submitted_to_erp")
    private boolean submittedToErp;

    @Column(name = "submitted_by")
    private String submittedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getSupplierRuc() {
        return supplierRuc;
    }

    public void setSupplierRuc(String supplierRuc) {
        this.supplierRuc = supplierRuc;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getMatchDiscrepanciesJson() {
        return matchDiscrepanciesJson;
    }

    public void setMatchDiscrepanciesJson(String matchDiscrepanciesJson) {
        this.matchDiscrepanciesJson = matchDiscrepanciesJson;
    }

    public Integer getErpInvoiceId() {
        return erpInvoiceId;
    }

    public void setErpInvoiceId(Integer erpInvoiceId) {
        this.erpInvoiceId = erpInvoiceId;
    }

    public boolean isSubmittedToErp() {
        return submittedToErp;
    }

    public void setSubmittedToErp(boolean submittedToErp) {
        this.submittedToErp = submittedToErp;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
