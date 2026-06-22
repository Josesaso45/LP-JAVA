package pe.portalproveedores.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoiceLine {

    private String lineId;
    private String productCode;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineExtensionAmount;
    private String affectationReason;
    private List<TaxBreakdown> taxes = new ArrayList<>();
    private DetractionInfo detraction;
    private Integer erpProductId;
    private Integer erpTaxId;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getLineExtensionAmount() {
        return lineExtensionAmount;
    }

    public void setLineExtensionAmount(BigDecimal lineExtensionAmount) {
        this.lineExtensionAmount = lineExtensionAmount;
    }

    public String getAffectationReason() {
        return affectationReason;
    }

    public void setAffectationReason(String affectationReason) {
        this.affectationReason = affectationReason;
    }

    public List<TaxBreakdown> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxBreakdown> taxes) {
        this.taxes = taxes != null ? taxes : new ArrayList<>();
    }

    public DetractionInfo getDetraction() {
        return detraction;
    }

    public void setDetraction(DetractionInfo detraction) {
        this.detraction = detraction;
    }

    public Integer getErpProductId() {
        return erpProductId;
    }

    public void setErpProductId(Integer erpProductId) {
        this.erpProductId = erpProductId;
    }

    public Integer getErpTaxId() {
        return erpTaxId;
    }

    public void setErpTaxId(Integer erpTaxId) {
        this.erpTaxId = erpTaxId;
    }
}
