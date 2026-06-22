package pe.portalproveedores.domain.service;

import pe.portalproveedores.domain.model.InvoiceLine;
import pe.portalproveedores.domain.model.MatchDiscrepancy;
import pe.portalproveedores.domain.model.MatchStatus;
import pe.portalproveedores.domain.model.PurchaseOrderLine;
import pe.portalproveedores.domain.model.PurchaseOrderSnapshot;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ThreeWayMatchingService {

    private final BigDecimal priceTolerancePercent;

    public ThreeWayMatchingService(BigDecimal priceTolerancePercent) {
        this.priceTolerancePercent = priceTolerancePercent != null
                ? priceTolerancePercent
                : new BigDecimal("0.01");
    }

    public ThreeWayMatchResult validate(SupplierInvoice invoice, PurchaseOrderSnapshot purchaseOrder) {
        ThreeWayMatchResult result = new ThreeWayMatchResult();
        result.setPurchaseOrderNumber(purchaseOrder != null ? purchaseOrder.getName() : null);
        List<MatchDiscrepancy> discrepancies = new ArrayList<>();

        if (purchaseOrder == null) {
            discrepancies.add(MatchDiscrepancy.general("purchase_order", "Orden de compra no encontrada"));
            result.setStatus(MatchStatus.REJECTED);
            result.setDiscrepancies(discrepancies);
            return result;
        }

        if (!purchaseOrder.isValidForInvoicing()) {
            discrepancies.add(MatchDiscrepancy.general(
                    "purchase_order_state",
                    "La OC no está en estado válido para facturar: " + purchaseOrder.getState()));
        }

        if (!"receive".equalsIgnoreCase(purchaseOrder.getInvoicePolicy())) {
            discrepancies.add(MatchDiscrepancy.general(
                    "invoice_policy",
                    "Política de facturación debe ser 'receive', actual: " + purchaseOrder.getInvoicePolicy()));
        }

        if (!Objects.equals(normalizeRuc(invoice.getSupplierRuc()), normalizeRuc(purchaseOrder.getPartnerRuc()))) {
            discrepancies.add(MatchDiscrepancy.general(
                    "supplier_ruc",
                    "RUC del proveedor no coincide con la OC"));
        }

        Map<String, PurchaseOrderLine> poLinesByProduct = purchaseOrder.getLines().stream()
                .filter(line -> line.productCode() != null)
                .collect(Collectors.toMap(
                        line -> line.productCode().toUpperCase(),
                        Function.identity(),
                        (a, b) -> a));

        for (InvoiceLine invoiceLine : invoice.getLines()) {
            String productKey = invoiceLine.getProductCode() != null
                    ? invoiceLine.getProductCode().toUpperCase()
                    : invoiceLine.getLineId();
            PurchaseOrderLine poLine = poLinesByProduct.get(
                    invoiceLine.getProductCode() != null ? invoiceLine.getProductCode().toUpperCase() : null);

            if (poLine == null) {
                discrepancies.add(MatchDiscrepancy.general(
                        "product_code",
                        "Producto no encontrado en OC: " + productKey));
                continue;
            }

            BigDecimal invoiceQty = invoiceLine.getQuantity() != null ? invoiceLine.getQuantity() : BigDecimal.ZERO;
            BigDecimal available = poLine.availableToInvoice();
            if (invoiceQty.compareTo(available) > 0) {
                discrepancies.add(MatchDiscrepancy.quantity(productKey, available, invoiceQty));
            }

            BigDecimal invoicePrice = invoiceLine.getUnitPrice() != null ? invoiceLine.getUnitPrice() : BigDecimal.ZERO;
            if (!isPriceWithinTolerance(invoicePrice, poLine.unitPrice())) {
                discrepancies.add(MatchDiscrepancy.price(productKey, poLine.unitPrice(), invoicePrice));
            }
        }

        result.setDiscrepancies(discrepancies);
        if (discrepancies.isEmpty()) {
            result.setStatus(MatchStatus.APPROVED);
        } else if (hasBlockingDiscrepancies(discrepancies)) {
            result.setStatus(MatchStatus.REJECTED);
        } else {
            result.setStatus(MatchStatus.PARTIAL);
        }
        return result;
    }

    private boolean hasBlockingDiscrepancies(List<MatchDiscrepancy> discrepancies) {
        return discrepancies.stream().anyMatch(d ->
                "quantity".equals(d.field()) || "purchase_order".equals(d.field())
                        || "invoice_policy".equals(d.field()) || "supplier_ruc".equals(d.field()));
    }

    private boolean isPriceWithinTolerance(BigDecimal invoicePrice, BigDecimal poPrice) {
        if (poPrice.compareTo(BigDecimal.ZERO) == 0) {
            return invoicePrice.compareTo(BigDecimal.ZERO) == 0;
        }
        BigDecimal diff = invoicePrice.subtract(poPrice).abs();
        BigDecimal tolerance = poPrice.multiply(priceTolerancePercent).setScale(4, RoundingMode.HALF_UP);
        return diff.compareTo(tolerance) <= 0;
    }

    private String normalizeRuc(String ruc) {
        return ruc != null ? ruc.trim().replace("-", "") : null;
    }
}
