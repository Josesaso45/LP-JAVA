package pe.portalproveedores.domain.exception;

public class ErpIntegrationException extends DomainException {

    public ErpIntegrationException(String message) {
        super(message);
    }

    public ErpIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
