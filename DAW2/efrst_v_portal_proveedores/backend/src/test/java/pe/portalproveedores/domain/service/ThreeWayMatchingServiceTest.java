package pe.portalproveedores.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.MatchStatus;
import pe.portalproveedores.domain.model.PurchaseOrderLine;
import pe.portalproveedores.domain.model.PurchaseOrderSnapshot;
import pe.portalproveedores.domain.model.SupplierInvoice;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreeWayMatchingServiceTest {

    private ThreeWayMatchingService matchingService;

    @BeforeEach
    void setUp() {
        matchingService = new ThreeWayMatchingService(new BigDecimal("1.00"));
    }

    @Test
    void rejectsWhenPurchaseOrderNotFound() {
        SupplierInvoice invoice = buildInvoice("20123456789", "PROD01", new BigDecimal("5"), new BigDecimal("100"));

        var result = matchingService.validate(invoice, null);

        assertEquals(MatchStatus.REJECTED, result.getStatus());
        assertFalse(result.getDiscrepancies().isEmpty());
    }

    @Test
    void approvesWhenAllRulesPass() {
        SupplierInvoice invoice = buildInvoice("20123456789", "PROD01", new BigDecimal("5"), new BigDecimal("100"));
        PurchaseOrderSnapshot po = buildPo("20123456789", "PROD01", new BigDecimal("10"), new BigDecimal("0"), new BigDecimal("100"));

        var result = matchingService.validate(invoice, po);

        assertTrue(result.isApproved());
    }

    @Test
    void rejectsQuantityExceeded() {
        SupplierInvoice invoice = buildInvoice("20123456789", "PROD01", new BigDecimal("15"), new BigDecimal("100"));
        PurchaseOrderSnapshot po = buildPo("20123456789", "PROD01", new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("100"));

        var result = matchingService.validate(invoice, po);

        assertEquals(MatchStatus.REJECTED, result.getStatus());
    }

    @Test
    void rejectsSupplierMismatch() {
        SupplierInvoice invoice = buildInvoice("20999999999", "PROD01", new BigDecimal("5"), new BigDecimal("100"));
        PurchaseOrderSnapshot po = buildPo("20123456789", "PROD01", new BigDecimal("10"), new BigDecimal("0"), new BigDecimal("100"));

        var result = matchingService.validate(invoice, po);

        assertFalse(result.isApproved());
    }

    private SupplierInvoice buildInvoice(String ruc, String productCode, BigDecimal qty, BigDecimal price) {
        SupplierInvoice invoice = new SupplierInvoice();
        invoice.setSupplierRuc(ruc);
        invoice.setPurchaseOrderNumber("PO00045");
        InvoiceLine line = new InvoiceLine();
        line.setLineId("1");
        line.setProductCode(productCode);
        line.setQuantity(qty);
        line.setUnitPrice(price);
        invoice.getLines().add(line);
        return invoice;
    }

    private PurchaseOrderSnapshot buildPo(String ruc, String productCode, BigDecimal received, BigDecimal invoiced, BigDecimal price) {
        PurchaseOrderSnapshot po = new PurchaseOrderSnapshot();
        po.setId(1);
        po.setName("PO00045");
        po.setState("purchase");
        po.setInvoicePolicy("receive");
        po.setPartnerRuc(ruc);
        po.setLines(List.of(new PurchaseOrderLine(
                1, productCode, "Producto Test",
                received, received, invoiced, price, 1
        )));
        return po;
    }
}
