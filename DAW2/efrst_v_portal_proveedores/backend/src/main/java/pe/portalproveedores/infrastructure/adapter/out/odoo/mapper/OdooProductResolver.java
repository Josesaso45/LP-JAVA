package pe.portalproveedores.infrastructure.adapter.out.odoo.mapper;

import org.springframework.stereotype.Component;
import pe.portalproveedores.infrastructure.adapter.out.odoo.client.OdooJsonRpcClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OdooProductResolver {

    private final OdooJsonRpcClient odooClient;

    public OdooProductResolver(OdooJsonRpcClient odooClient) {
        this.odooClient = odooClient;
    }

    public Optional<Integer> resolveByDefaultCode(String productCode) {
        if (productCode == null || productCode.isBlank()) {
            return Optional.empty();
        }
        List<Map<String, Object>> products = odooClient.searchRead(
                "product.product",
                List.of(List.of("default_code", "=", productCode)),
                List.of("id", "default_code", "name")
        );
        if (products.isEmpty()) {
            return Optional.empty();
        }
        Object id = products.getFirst().get("id");
        return Optional.of(((Number) id).intValue());
    }
}
