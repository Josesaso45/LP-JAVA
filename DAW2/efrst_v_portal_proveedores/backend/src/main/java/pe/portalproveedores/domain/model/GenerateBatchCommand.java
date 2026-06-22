package pe.portalproveedores.domain.model;

import java.time.LocalDate;
import java.util.List;

public record GenerateBatchCommand(
        LocalDate paymentDate,
        String currencyCode,
        List<Integer> invoiceIds
) {
}
