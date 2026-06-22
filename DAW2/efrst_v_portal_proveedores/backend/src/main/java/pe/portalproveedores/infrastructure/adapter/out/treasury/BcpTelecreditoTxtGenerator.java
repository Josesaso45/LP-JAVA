package pe.portalproveedores.infrastructure.adapter.out.treasury;

import org.springframework.stereotype.Component;
import pe.portalproveedores.application.port.out.TreasuryFileGeneratorPort;
import pe.portalproveedores.domain.model.BankFormat;
import pe.portalproveedores.domain.model.GeneratedFile;
import pe.portalproveedores.domain.model.PaymentBatch;
import pe.portalproveedores.domain.model.PaymentBatchLine;
import pe.portalproveedores.infrastructure.config.TreasuryProperties;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class BcpTelecreditoTxtGenerator implements TreasuryFileGeneratorPort {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final TreasuryProperties treasuryProperties;

    public BcpTelecreditoTxtGenerator(TreasuryProperties treasuryProperties) {
        this.treasuryProperties = treasuryProperties;
    }

    @Override
    public GeneratedFile generate(PaymentBatch batch, BankFormat format) {
        if (format != BankFormat.BCP_TELECREDITO) {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }

        StringBuilder content = new StringBuilder();
        String paymentDate = batch.getPaymentDate() != null
                ? batch.getPaymentDate().format(DATE_FMT)
                : DATE_FMT.format(java.time.LocalDate.now());

        content.append(buildHeader(batch, paymentDate)).append("\r\n");
        for (PaymentBatchLine line : batch.getLines()) {
            content.append(buildDetail(line, paymentDate)).append("\r\n");
        }

        String fileName = "LOTE_" + paymentDate + ".txt";
        return new GeneratedFile(fileName, content.toString().getBytes(StandardCharsets.UTF_8), "text/plain");
    }

    private String buildHeader(PaymentBatch batch, String paymentDate) {
        return padRight("H", 1)
                + padRight(treasuryProperties.getCompanyRuc(), 11)
                + padRight(treasuryProperties.getCompanyName(), 40)
                + padRight(batch.getOriginAccount() != null ? batch.getOriginAccount() : treasuryProperties.getOriginAccount(), 20)
                + padRight(paymentDate, 8)
                + padLeft(formatAmount(batch.totalAmount()), 15)
                + padLeft(String.valueOf(batch.lineCount()), 6)
                + padRight(batch.getCurrencyCode() != null ? batch.getCurrencyCode() : treasuryProperties.getCurrencyCode(), 3);
    }

    private String buildDetail(PaymentBatchLine line, String paymentDate) {
        return padRight("D", 1)
                + padRight("001", 3)
                + padRight(line.destinationAccount(), 20)
                + padRight(line.cci() != null ? line.cci() : line.destinationAccount(), 20)
                + padLeft(formatAmount(line.amount()), 15)
                + padRight(line.beneficiaryRuc(), 11)
                + padRight(line.beneficiaryName(), 40)
                + padRight(line.invoiceReference(), 20)
                + padRight(paymentDate, 8)
                + padRight(line.reference() != null ? line.reference() : line.invoiceReference(), 30);
    }

    private String formatAmount(BigDecimal amount) {
        return String.format(Locale.US, "%.2f", amount != null ? amount : BigDecimal.ZERO);
    }

    private String padRight(String value, int length) {
        String v = value != null ? value : "";
        if (v.length() >= length) {
            return v.substring(0, length);
        }
        return v + " ".repeat(length - v.length());
    }

    private String padLeft(String value, int length) {
        String v = value != null ? value : "";
        if (v.length() >= length) {
            return v.substring(v.length() - length);
        }
        return " ".repeat(length - v.length()) + v;
    }
}
