package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.DetractionConfig;
import pe.portalproveedores.domain.model.TaxIdentifier;

import java.util.Optional;

public interface ErpTaxCatalogPort {

    Optional<Integer> resolveTaxId(TaxIdentifier taxIdentifier);

    Optional<DetractionConfig> resolveDetraction(String sunatProductCode);
}
