package pe.portalproveedores.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.portalproveedores.application.port.out.ErpTaxCatalogPort;
import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.TaxBreakdown;
import pe.portalproveedores.domain.model.TaxIdentifier;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeruvianTaxEngineTest {

    @Mock
    private ErpTaxCatalogPort taxCatalogPort;

    private PeruvianTaxEngine taxEngine;

    @BeforeEach
    void setUp() {
        taxEngine = new PeruvianTaxEngine(taxCatalogPort);
        lenient().when(taxCatalogPort.resolveTaxId(any())).thenReturn(Optional.of(15));
    }

    @Test
    void enrichCalculatesIgvWhenMissing() {
        SupplierInvoice invoice = new SupplierInvoice();
        InvoiceLine line = new InvoiceLine();
        line.setLineExtensionAmount(new BigDecimal("1000.00"));
        invoice.getLines().add(line);

        taxEngine.enrich(invoice);

        assertEquals(1, line.getTaxes().size());
        assertEquals(TaxIdentifier.IGV, line.getTaxes().get(0).taxType());
        assertEquals(new BigDecimal("180.00"), line.getTaxes().get(0).taxAmount());
        assertEquals(15, line.getErpTaxId());
    }

    @Test
    void enrichPreservesExistingTaxes() {
        SupplierInvoice invoice = new SupplierInvoice();
        InvoiceLine line = new InvoiceLine();
        line.getTaxes().add(new TaxBreakdown(
                TaxIdentifier.IGV,
                "1000",
                new BigDecimal("500"),
                new BigDecimal("90"),
                new BigDecimal("18")
        ));
        invoice.getLines().add(line);

        taxEngine.enrich(invoice);

        assertEquals(1, line.getTaxes().size());
        assertEquals(new BigDecimal("90"), line.getTaxes().get(0).taxAmount());
    }

    @Test
    void calculateIgvTotal() {
        SupplierInvoice invoice = new SupplierInvoice();
        InvoiceLine line = new InvoiceLine();
        line.getTaxes().add(new TaxBreakdown(
                TaxIdentifier.IGV, "1000",
                new BigDecimal("100"), new BigDecimal("18"), new BigDecimal("18")
        ));
        invoice.getLines().add(line);

        assertEquals(new BigDecimal("18"), taxEngine.calculateIgvTotal(invoice));
    }
}
