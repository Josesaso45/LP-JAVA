package pe.portalproveedores.domain.service;

import pe.portalproveedores.application.port.out.ErpTaxCatalogPort;
import pe.portalproveedores.domain.model.DetractionConfig;
import pe.portalproveedores.domain.model.DetractionInfo;
import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.TaxBreakdown;
import pe.portalproveedores.domain.model.TaxIdentifier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PeruvianTaxEngine {

    private static final BigDecimal DEFAULT_IGV_RATE = new BigDecimal("0.18");

    private final ErpTaxCatalogPort taxCatalogPort;

    public PeruvianTaxEngine(ErpTaxCatalogPort taxCatalogPort) {
        this.taxCatalogPort = taxCatalogPort;
    }

    public SupplierInvoice enrich(SupplierInvoice invoice) {
        for (InvoiceLine line : invoice.getLines()) {
            enrichLineTaxes(line);
            resolveErpTaxIds(line);
            resolveDetraction(line);
        }
        resolveHeaderDetraction(invoice);
        return invoice;
    }

    private void enrichLineTaxes(InvoiceLine line) {
        List<TaxBreakdown> taxes = line.getTaxes();
        if (taxes.isEmpty() && line.getLineExtensionAmount() != null) {
            BigDecimal base = line.getLineExtensionAmount();
            BigDecimal igvAmount = base.multiply(DEFAULT_IGV_RATE).setScale(2, RoundingMode.HALF_UP);
            taxes.add(new TaxBreakdown(
                    TaxIdentifier.IGV,
                    TaxIdentifier.IGV.getSunatCode(),
                    base,
                    igvAmount,
                    DEFAULT_IGV_RATE.multiply(BigDecimal.valueOf(100))
            ));
        }
    }

    private void resolveErpTaxIds(InvoiceLine line) {
        for (TaxBreakdown tax : line.getTaxes()) {
            if (tax.taxType() == TaxIdentifier.IGV && line.getErpTaxId() == null) {
                taxCatalogPort.resolveTaxId(tax.taxType())
                        .ifPresent(line::setErpTaxId);
            }
        }
    }

    private void resolveDetraction(InvoiceLine line) {
        DetractionInfo detraction = line.getDetraction();
        if (detraction != null && detraction.hasDetraction()) {
            taxCatalogPort.resolveDetraction(detraction.sunatProductCode())
                    .ifPresent(config -> line.setDetraction(new DetractionInfo(
                            detraction.sunatProductCode(),
                            config.percent(),
                            detraction.amount(),
                            config.erpTaxId()
                    )));
        }
    }

    private void resolveHeaderDetraction(SupplierInvoice invoice) {
        DetractionInfo header = invoice.getHeaderDetraction();
        if (header != null && header.hasDetraction()) {
            taxCatalogPort.resolveDetraction(header.sunatProductCode())
                    .ifPresent(config -> invoice.setHeaderDetraction(new DetractionInfo(
                            header.sunatProductCode(),
                            config.percent(),
                            header.amount(),
                            config.erpTaxId()
                    )));
        }
    }

    public BigDecimal calculateIgvTotal(SupplierInvoice invoice) {
        return invoice.getLines().stream()
                .flatMap(line -> line.getTaxes().stream())
                .filter(t -> t.taxType() == TaxIdentifier.IGV)
                .map(TaxBreakdown::taxAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
