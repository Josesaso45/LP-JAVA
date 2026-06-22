package pe.portalproveedores.domain.model;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderSnapshot {

    private int id;
    private String name;
    private String state;
    private String invoicePolicy;
    private String partnerRuc;
    private String partnerName;
    private List<PurchaseOrderLine> lines = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInvoicePolicy() {
        return invoicePolicy;
    }

    public void setInvoicePolicy(String invoicePolicy) {
        this.invoicePolicy = invoicePolicy;
    }

    public String getPartnerRuc() {
        return partnerRuc;
    }

    public void setPartnerRuc(String partnerRuc) {
        this.partnerRuc = partnerRuc;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public List<PurchaseOrderLine> getLines() {
        return lines;
    }

    public void setLines(List<PurchaseOrderLine> lines) {
        this.lines = lines != null ? lines : new ArrayList<>();
    }

    public boolean isValidForInvoicing() {
        return "purchase".equals(state) || "done".equals(state);
    }
}
