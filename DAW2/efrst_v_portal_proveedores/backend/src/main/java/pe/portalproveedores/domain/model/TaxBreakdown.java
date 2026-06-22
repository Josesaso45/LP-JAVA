package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record TaxBreakdown(
        TaxIdentifier taxType,
        String sunatCode,
        BigDecimal taxableAmount,
        BigDecimal taxAmount,
        BigDecimal percent
) {
    public TaxBreakdown {
        taxableAmount = taxableAmount != null ? taxableAmount : BigDecimal.ZERO;
        taxAmount = taxAmount != null ? taxAmount : BigDecimal.ZERO;
        percent = percent != null ? percent : BigDecimal.ZERO;
    }
}
