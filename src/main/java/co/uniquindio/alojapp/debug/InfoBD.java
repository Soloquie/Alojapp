package co.uniquindio.alojapp.debug;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
@Slf4j
public class InfoBD {
    private final JdbcTemplate jdbc;

    @PostConstruct
    public void logInfoBD() {
        String db = jdbc.queryForObject("SELECT DATABASE()", String.class);
        log.info("Conectado al esquema BD: {}", db);
    }
}
