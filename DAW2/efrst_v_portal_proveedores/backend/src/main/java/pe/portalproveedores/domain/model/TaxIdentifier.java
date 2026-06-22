package pe.portalproveedores.domain.model;

public enum TaxIdentifier {
    IGV("1000", "IGV"),
    RETENCION("2000", "Retención"),
    DETRACCION("9999", "Detracción");

    private final String sunatCode;
    private final String description;

    TaxIdentifier(String sunatCode, String description) {
        this.sunatCode = sunatCode;
        this.description = description;
    }

    public String getSunatCode() {
        return sunatCode;
    }

    public String getDescription() {
        return description;
    }

    public static TaxIdentifier fromSunatCode(String code) {
        if (code == null) {
            return null;
        }
        for (TaxIdentifier identifier : values()) {
            if (identifier.sunatCode.equals(code)) {
                return identifier;
            }
        }
        return null;
    }
}
