package pe.portalproveedores.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.portalproveedores.application.port.out.ErpTaxCatalogPort;
import pe.portalproveedores.domain.service.PeruvianTaxEngine;
import pe.portalproveedores.domain.service.ThreeWayMatchingService;

@Configuration
public class DomainServiceConfig {

    @Bean
    public PeruvianTaxEngine peruvianTaxEngine(ErpTaxCatalogPort taxCatalogPort) {
        return new PeruvianTaxEngine(taxCatalogPort);
    }

    @Bean
    public ThreeWayMatchingService threeWayMatchingService(MatchingProperties matchingProperties) {
        return new ThreeWayMatchingService(matchingProperties.getPriceTolerancePercent());
    }
}
