package pe.portalproveedores.infrastructure.adapter.out.odoo.adapter;

import org.springframework.stereotype.Component;
import pe.portalproveedores.application.port.out.ErpTaxCatalogPort;
import pe.portalproveedores.domain.model.DetractionConfig;
import pe.portalproveedores.domain.model.TaxIdentifier;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
public class OdooTaxCatalogAdapter implements ErpTaxCatalogPort {

    private static final Map<TaxIdentifier, String> TAX_NAMES = Map.of(
            TaxIdentifier.IGV, "IGV",
            TaxIdentifier.RETENCION, "Retención",
            TaxIdentifier.DETRACCION, "Detracción"
    );

    private static final Map<String, BigDecimal> DETRACTION_RATES = Map.of(
            "027", new BigDecimal("4.00"),
            "022", new BigDecimal("10.00"),
            "012", new BigDecimal("12.00")
    );

    @Override
    public Optional<Integer> resolveTaxId(TaxIdentifier taxIdentifier) {
        return Optional.of(switch (taxIdentifier) {
            case IGV -> 15;
            case RETENCION -> 16;
            case DETRACCION -> 17;
        });
    }

    @Override
    public Optional<DetractionConfig> resolveDetraction(String sunatProductCode) {
        if (sunatProductCode == null || sunatProductCode.isBlank()) {
            return Optional.empty();
        }
        BigDecimal percent = DETRACTION_RATES.getOrDefault(sunatProductCode, new BigDecimal("4.00"));
        return Optional.of(new DetractionConfig(sunatProductCode, percent, 17));
    }
}
