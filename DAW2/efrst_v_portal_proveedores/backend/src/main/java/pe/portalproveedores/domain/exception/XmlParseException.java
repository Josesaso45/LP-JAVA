package pe.portalproveedores.domain.exception;

public class XmlParseException extends DomainException {

    public XmlParseException(String message) {
        super(message);
    }

    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
