package pe.portalproveedores.domain.model;

import java.math.BigDecimal;

public record MatchDiscrepancy(
        String lineReference,
        String field,
        String expectedValue,
        String actualValue,
        String message
) {
    public static MatchDiscrepancy quantity(String lineRef, BigDecimal expected, BigDecimal actual) {
        return new MatchDiscrepancy(
                lineRef,
                "quantity",
                expected.toPlainString(),
                actual.toPlainString(),
                "Cantidad facturada excede lo recibido pendiente de facturar"
        );
    }

    public static MatchDiscrepancy price(String lineRef, BigDecimal expected, BigDecimal actual) {
        return new MatchDiscrepancy(
                lineRef,
                "price",
                expected.toPlainString(),
                actual.toPlainString(),
                "Precio unitario difiere de la orden de compra"
        );
    }

    public static MatchDiscrepancy general(String field, String message) {
        return new MatchDiscrepancy(null, field, null, null, message);
    }
}
