package pe.portalproveedores.infrastructure.adapter.out.odoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OdooJsonRpcResponse {

    private JsonNode result;
    private OdooJsonRpcError error;

    public JsonNode getResult() {
        return result;
    }

    public void setResult(JsonNode result) {
        this.result = result;
    }

    public OdooJsonRpcError getError() {
        return error;
    }

    public void setError(OdooJsonRpcError error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
