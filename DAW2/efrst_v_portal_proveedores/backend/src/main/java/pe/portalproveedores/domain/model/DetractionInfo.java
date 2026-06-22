package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record DetractionInfo(
        String sunatProductCode,
        BigDecimal percent,
        BigDecimal amount,
        Integer erpDetractionId
) {
    public DetractionInfo {
        percent = percent != null ? percent : BigDecimal.ZERO;
        amount = amount != null ? amount : BigDecimal.ZERO;
    }

    public boolean hasDetraction() {
        return sunatProductCode != null && !sunatProductCode.isBlank();
    }
}
