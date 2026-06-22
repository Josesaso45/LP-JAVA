package pe.portalproveedores.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ThreeWayMatchResult {

    private MatchStatus status;
    private List<MatchDiscrepancy> discrepancies = new ArrayList<>();
    private String purchaseOrderNumber;

    public ThreeWayMatchResult() {
    }

    public ThreeWayMatchResult(MatchStatus status, List<MatchDiscrepancy> discrepancies, String purchaseOrderNumber) {
        this.status = status;
        this.discrepancies = discrepancies != null ? discrepancies : new ArrayList<>();
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public List<MatchDiscrepancy> getDiscrepancies() {
        return discrepancies;
    }

    public void setDiscrepancies(List<MatchDiscrepancy> discrepancies) {
        this.discrepancies = discrepancies != null ? discrepancies : new ArrayList<>();
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public boolean isApproved() {
        return status == MatchStatus.APPROVED;
    }

    public void addDiscrepancy(MatchDiscrepancy discrepancy) {
        this.discrepancies.add(discrepancy);
    }
}
