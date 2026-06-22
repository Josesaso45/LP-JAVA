package pe.portalproveedores.infrastructure.adapter.out.odoo.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.portalproveedores.domain.exception.ErpIntegrationException;
import pe.portalproveedores.infrastructure.adapter.out.odoo.config.OdooProperties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OdooSessionManager {

    private final WebClient webClient;
    private final OdooProperties properties;
    private final ObjectMapper objectMapper;
    private final AtomicInteger cachedUid = new AtomicInteger(-1);

    public OdooSessionManager(WebClient webClient, OdooProperties properties, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    public int getUid() {
        int uid = cachedUid.get();
        if (uid > 0) {
            return uid;
        }
        synchronized (this) {
            if (cachedUid.get() > 0) {
                return cachedUid.get();
            }
            int authenticated = authenticate();
            cachedUid.set(authenticated);
            return authenticated;
        }
    }

    public void invalidate() {
        cachedUid.set(-1);
    }

    private int authenticate() {
        Map<String, Object> request = Map.of(
                "jsonrpc", "2.0",
                "method", "call",
                "params", Map.of(
                        "service", "common",
                        "method", "authenticate",
                        "args", List.of(
                                properties.getDatabase(),
                                properties.getUsername(),
                                properties.getPassword(),
                                Map.of()
                        )
                ),
                "id", System.currentTimeMillis()
        );

        try {
            String responseBody = webClient.post()
                    .uri("/jsonrpc")
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(responseBody);
            if (root.has("error")) {
                throw new ErpIntegrationException("Odoo auth error: " + root.get("error").toString());
            }
            JsonNode result = root.get("result");
            if (result == null || result.isNull() || !result.isNumber()) {
                throw new ErpIntegrationException("Autenticación Odoo fallida: credenciales inválidas");
            }
            return result.asInt();
        } catch (ErpIntegrationException e) {
            throw e;
        } catch (Exception e) {
            throw new ErpIntegrationException("Error autenticando con Odoo: " + e.getMessage(), e);
        }
    }
}
