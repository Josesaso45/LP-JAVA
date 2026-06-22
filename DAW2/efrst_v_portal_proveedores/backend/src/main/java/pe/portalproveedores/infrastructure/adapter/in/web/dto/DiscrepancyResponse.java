package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import pe.portalproveedores.domain.model.MatchDiscrepancy;

public record DiscrepancyResponse(
        String lineReference,
        String field,
        String expectedValue,
        String actualValue,
        String message
) {
    public static DiscrepancyResponse from(MatchDiscrepancy d) {
        return new DiscrepancyResponse(d.lineReference(), d.field(), d.expectedValue(), d.actualValue(), d.message());
    }
}
