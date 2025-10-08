package co.uniquindio.alojapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    /**
     * Clave HS256 (≥ 32 bytes)
     */
    private String secret;
    /**
     * Vigencia del access token en ms
     */
    private long expirationMs;
}
