package pe.portalproveedores.infrastructure.adapter.out.odoo.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.portalproveedores.domain.exception.ErpIntegrationException;
import pe.portalproveedores.infrastructure.adapter.out.odoo.config.OdooProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OdooJsonRpcClient {

    private static final Logger log = LoggerFactory.getLogger(OdooJsonRpcClient.class);

    private final WebClient webClient;
    private final OdooProperties properties;
    private final OdooSessionManager sessionManager;
    private final ObjectMapper objectMapper;

    public OdooJsonRpcClient(WebClient webClient,
                             OdooProperties properties,
                             OdooSessionManager sessionManager,
                             ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.properties = properties;
        this.sessionManager = sessionManager;
        this.objectMapper = objectMapper;
    }

    public JsonNode executeKw(String model, String method, List<Object> args) {
        return executeKw(model, method, args, Map.of());
    }

    public JsonNode executeKw(String model, String method, List<Object> args, Map<String, Object> kwargs) {
        int uid = sessionManager.getUid();
        List<Object> executeArgs = List.of(
                properties.getDatabase(),
                uid,
                properties.getPassword(),
                model,
                method,
                args,
                kwargs != null ? kwargs : Map.of()
        );

        Map<String, Object> request = new HashMap<>();
        request.put("jsonrpc", "2.0");
        request.put("method", "call");
        request.put("params", Map.of(
                "service", "object",
                "method", "execute_kw",
                "args", executeArgs
        ));
        request.put("id", System.currentTimeMillis());

        log.debug("Odoo execute_kw: model={}, method={}", model, method);

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
                throw new ErpIntegrationException("Odoo error: " + root.get("error").toString());
            }
            return root.get("result");
        } catch (ErpIntegrationException e) {
            throw e;
        } catch (Exception e) {
            throw new ErpIntegrationException("Error comunicando con Odoo: " + e.getMessage(), e);
        }
    }

    public int create(String model, Map<String, Object> values) {
        JsonNode result = executeKw(model, "create", List.of(values));
        return result.asInt();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> searchRead(String model, List<List<Object>> domain, List<String> fields) {
        JsonNode result = executeKw(model, "search_read", List.of(domain, fields));
        return objectMapper.convertValue(result, List.class);
    }
}
