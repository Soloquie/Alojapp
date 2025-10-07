package co.uniquindio.alojapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // factor de costo por defecto = 10; si quieres más seguridad puedes subirlo (12, 14…)
        return new BCryptPasswordEncoder(/* strength = 10 */);
    }
}
