package pe.portalproveedores.infrastructure.adapter.out.odoo.adapter;

import org.springframework.stereotype.Component;
import pe.portalproveedores.application.port.out.ErpPaymentPort;
import pe.portalproveedores.domain.model.OpenVendorBill;
import pe.portalproveedores.domain.model.PaymentBatchCriteria;
import pe.portalproveedores.infrastructure.adapter.out.odoo.client.OdooJsonRpcClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OdooPaymentAdapter implements ErpPaymentPort {

    private final OdooJsonRpcClient odooClient;

    public OdooPaymentAdapter(OdooJsonRpcClient odooClient) {
        this.odooClient = odooClient;
    }

    @Override
    public List<OpenVendorBill> findOpenVendorBills(PaymentBatchCriteria criteria) {
        List<List<Object>> domain = new ArrayList<>();
        domain.add(List.of("move_type", "=", "in_invoice"));
        domain.add(List.of("payment_state", "=", "not_paid"));
        domain.add(List.of("state", "=", "posted"));

        if (criteria.currencyCode() != null) {
            domain.add(List.of("currency_id.name", "=", criteria.currencyCode()));
        }
        if (criteria.dueDateFrom() != null) {
            domain.add(List.of("invoice_date_due", ">=", criteria.dueDateFrom().toString()));
        }
        if (criteria.dueDateTo() != null) {
            domain.add(List.of("invoice_date_due", "<=", criteria.dueDateTo().toString()));
        }
        if (criteria.invoiceIds() != null && !criteria.invoiceIds().isEmpty()) {
            domain.add(List.of("id", "in", criteria.invoiceIds()));
        }

        List<Map<String, Object>> moves = odooClient.searchRead(
                "account.move",
                domain,
                List.of("id", "ref", "partner_id", "amount_residual", "currency_id", "invoice_date_due")
        );

        List<OpenVendorBill> bills = new ArrayList<>();
        for (Map<String, Object> move : moves) {
            PartnerBankInfo bankInfo = resolvePartnerBank(move.get("partner_id"));
            bills.add(new OpenVendorBill(
                    ((Number) move.get("id")).intValue(),
                    (String) move.get("ref"),
                    bankInfo.ruc(),
                    bankInfo.name(),
                    bankInfo.account(),
                    bankInfo.cci(),
                    new BigDecimal(move.get("amount_residual").toString()),
                    extractCurrency(move.get("currency_id")),
                    parseDate(move.get("invoice_date_due"))
            ));
        }
        return bills;
    }

    private PartnerBankInfo resolvePartnerBank(Object partnerField) {
        Integer partnerId = extractId(partnerField);
        if (partnerId == null) {
            return new PartnerBankInfo("", "", "", "");
        }
        List<Map<String, Object>> partners = odooClient.searchRead(
                "res.partner",
                List.of(List.of("id", "=", partnerId)),
                List.of("vat", "name", "bank_ids")
        );
        if (partners.isEmpty()) {
            return new PartnerBankInfo("", "", "", "");
        }
        Map<String, Object> partner = partners.getFirst();
        String ruc = partner.get("vat") != null ? partner.get("vat").toString() : "";
        String name = partner.get("name") != null ? partner.get("name").toString() : "";

        String account = "";
        String cci = "";
        Object bankIds = partner.get("bank_ids");
        if (bankIds instanceof List<?> ids && !ids.isEmpty()) {
            Integer bankId = ((Number) ids.getFirst()).intValue();
            List<Map<String, Object>> banks = odooClient.searchRead(
                    "res.partner.bank",
                    List.of(List.of("id", "=", bankId)),
                    List.of("acc_number")
            );
            if (!banks.isEmpty()) {
                account = banks.getFirst().get("acc_number") != null
                        ? banks.getFirst().get("acc_number").toString() : "";
                cci = account;
            }
        }
        return new PartnerBankInfo(ruc, name, account, cci);
    }

    private Integer extractId(Object many2one) {
        if (many2one instanceof List<?> list && !list.isEmpty()) {
            return ((Number) list.getFirst()).intValue();
        }
        if (many2one instanceof Number number) {
            return number.intValue();
        }
        return null;
    }

    private String extractCurrency(Object currencyField) {
        if (currencyField instanceof List<?> list && list.size() > 1) {
            return list.get(1).toString();
        }
        return "PEN";
    }

    private LocalDate parseDate(Object value) {
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        return LocalDate.parse(value.toString());
    }

    private record PartnerBankInfo(String ruc, String name, String account, String cci) {
    }
}
