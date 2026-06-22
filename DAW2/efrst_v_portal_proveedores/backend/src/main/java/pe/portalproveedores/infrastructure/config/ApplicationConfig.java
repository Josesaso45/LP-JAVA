package pe.portalproveedores.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import pe.portalproveedores.infrastructure.adapter.out.odoo.config.OdooProperties;

@Configuration
public class ApplicationConfig {

    @Bean
    public WebClient odooWebClient(OdooProperties odooProperties) {
        return WebClient.builder()
                .baseUrl(odooProperties.getUrl())
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
