package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record DetractionConfig(
        String sunatProductCode,
        BigDecimal percent,
        Integer erpTaxId
) {
}
