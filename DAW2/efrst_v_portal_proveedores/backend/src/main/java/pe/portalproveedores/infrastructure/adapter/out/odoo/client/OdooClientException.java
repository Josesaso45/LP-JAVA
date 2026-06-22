package pe.portalproveedores.infrastructure.adapter.out.odoo.client;

public class OdooClientException extends RuntimeException {

    public OdooClientException(String message) {
        super(message);
    }

    public OdooClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
