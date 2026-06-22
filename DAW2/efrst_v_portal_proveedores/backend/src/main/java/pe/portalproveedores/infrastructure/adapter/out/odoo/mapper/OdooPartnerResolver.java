package pe.portalproveedores.infrastructure.adapter.out.odoo.mapper;

import org.springframework.stereotype.Component;
import pe.portalproveedores.infrastructure.adapter.out.odoo.client.OdooJsonRpcClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OdooPartnerResolver {

    private final OdooJsonRpcClient odooClient;

    public OdooPartnerResolver(OdooJsonRpcClient odooClient) {
        this.odooClient = odooClient;
    }

    public Optional<Integer> resolveByRuc(String ruc) {
        if (ruc == null || ruc.isBlank()) {
            return Optional.empty();
        }
        List<Map<String, Object>> partners = odooClient.searchRead(
                "res.partner",
                List.of(List.of("vat", "=", ruc)),
                List.of("id", "name", "vat")
        );
        if (partners.isEmpty()) {
            return Optional.empty();
        }
        Object id = partners.getFirst().get("id");
        return Optional.of(((Number) id).intValue());
    }
}
