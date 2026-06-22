package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import pe.portalproveedores.domain.model.ThreeWayMatchResult;

import java.util.List;

public record MatchStatusResponse(
        String status,
        String purchaseOrderNumber,
        List<DiscrepancyResponse> discrepancies
) {
    public static MatchStatusResponse from(ThreeWayMatchResult result) {
        return new MatchStatusResponse(
                result.getStatus().name(),
                result.getPurchaseOrderNumber(),
                result.getDiscrepancies().stream().map(DiscrepancyResponse::from).toList()
        );
    }
}
