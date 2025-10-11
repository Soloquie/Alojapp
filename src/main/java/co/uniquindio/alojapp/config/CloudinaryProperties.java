package co.uniquindio.alojapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {
    private String cloudName;
    private String apiKey;
    private String apiSecret;
    /** Carpeta base opcional: p.ej. "alojapp/alojamientos" */
    private String folder = "alojapp";
}
