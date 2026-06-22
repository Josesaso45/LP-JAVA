package pe.portalproveedores.infrastructure.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.List;

public record GenerateBatchRequest(
        LocalDate paymentDate,
        String currencyCode,
        List<Integer> invoiceIds
) {
}
