package pe.portalproveedores.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentBatch {

    private String id;
    private LocalDate paymentDate;
    private String currencyCode;
    private String originAccount;
    private List<PaymentBatchLine> lines = new ArrayList<>();

    public PaymentBatch() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public List<PaymentBatchLine> getLines() {
        return lines;
    }

    public void setLines(List<PaymentBatchLine> lines) {
        this.lines = lines != null ? lines : new ArrayList<>();
    }

    public BigDecimal totalAmount() {
        return lines.stream()
                .map(PaymentBatchLine::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int lineCount() {
        return lines.size();
    }
}
