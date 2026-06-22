package pe.portalproveedores.domain.model;

import java.time.LocalDate;
import java.util.List;

public record PaymentBatchCriteria(
        String currencyCode,
        LocalDate dueDateFrom,
        LocalDate dueDateTo,
        List<Integer> invoiceIds
) {
}
