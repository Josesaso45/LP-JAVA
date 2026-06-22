package pe.portalproveedores.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "matching")
public class MatchingProperties {

    private BigDecimal priceTolerancePercent = new BigDecimal("0.01");

    public BigDecimal getPriceTolerancePercent() {
        return priceTolerancePercent;
    }

    public void setPriceTolerancePercent(BigDecimal priceTolerancePercent) {
        this.priceTolerancePercent = priceTolerancePercent;
    }
}
