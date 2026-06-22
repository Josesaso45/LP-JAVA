package pe.portalproveedores.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierInvoice {

    private String serialNumber;
    private String invoiceNumber;
    private String supplierRuc;
    private String supplierName;
    private LocalDate issueDate;
    private String currencyCode;
    private BigDecimal totalAmount;
    private BigDecimal taxInclusiveAmount;
    private String purchaseOrderNumber;
    private List<InvoiceLine> lines = new ArrayList<>();
    private DetractionInfo headerDetraction;
    private Integer erpPartnerId;
    private Integer erpPurchaseOrderId;
    private Integer erpDocumentTypeId;

    public String getFullNumber() {
        if (serialNumber == null || invoiceNumber == null) {
            return null;
        }
        return serialNumber + "-" + invoiceNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getSupplierRuc() {
        return supplierRuc;
    }

    public void setSupplierRuc(String supplierRuc) {
        this.supplierRuc = supplierRuc;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTaxInclusiveAmount() {
        return taxInclusiveAmount;
    }

    public void setTaxInclusiveAmount(BigDecimal taxInclusiveAmount) {
        this.taxInclusiveAmount = taxInclusiveAmount;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(List<InvoiceLine> lines) {
        this.lines = lines != null ? lines : new ArrayList<>();
    }

    public DetractionInfo getHeaderDetraction() {
        return headerDetraction;
    }

    public void setHeaderDetraction(DetractionInfo headerDetraction) {
        this.headerDetraction = headerDetraction;
    }

    public Integer getErpPartnerId() {
        return erpPartnerId;
    }

    public void setErpPartnerId(Integer erpPartnerId) {
        this.erpPartnerId = erpPartnerId;
    }

    public Integer getErpPurchaseOrderId() {
        return erpPurchaseOrderId;
    }

    public void setErpPurchaseOrderId(Integer erpPurchaseOrderId) {
        this.erpPurchaseOrderId = erpPurchaseOrderId;
    }

    public Integer getErpDocumentTypeId() {
        return erpDocumentTypeId;
    }

    public void setErpDocumentTypeId(Integer erpDocumentTypeId) {
        this.erpDocumentTypeId = erpDocumentTypeId;
    }
}
