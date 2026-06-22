package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public class ValidateMatchRequest {

    @NotBlank
    private String xmlContent;

    @NotBlank
    private String purchaseOrderNumber;

    public String getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }
}
