package pe.portalproveedores.infrastructure.adapter.out.odoo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "odoo")
public class OdooProperties {

    private String url;
    private String database;
    private String username;
    private String password;
    private OdooFieldMappingProperties fieldMapping = new OdooFieldMappingProperties();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OdooFieldMappingProperties getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(OdooFieldMappingProperties fieldMapping) {
        this.fieldMapping = fieldMapping;
    }
}
