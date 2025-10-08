package co.uniquindio.alojapp.seguridad;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {}

    /** Email/username del autenticado (viene del subject de tu JWT). */
    public static Optional<String> getEmailActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();

        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails ud) {
            String username = ud.getUsername();
            return (username == null || username.isBlank()) ? Optional.empty() : Optional.of(username);
        }
        String name = auth.getName();
        return (name == null || name.isBlank()) ? Optional.empty() : Optional.of(name);
    }

    /** Si guardas el userId en Authentication#details (ver paso 2), lo recuperas aquí. */
    public static Optional<Integer> getUsuarioIdDesdeDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return Optional.empty();
        Object details = auth.getDetails();
        if (details instanceof Map<?, ?> map) {
            Object val = map.get("userId");
            if (val instanceof Integer i) return Optional.of(i);
            if (val instanceof Number n) return Optional.of(n.intValue());
            if (val instanceof String s && s.matches("\\d+")) return Optional.of(Integer.parseInt(s));
        }
        return Optional.empty();
    }

    /** Verifica rol con el prefijo ROLE_. Ej: tieneRol("ANFITRION") */
    public static boolean tieneRol(String rolSinPrefijo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        String esperado = "ROLE_" + rolSinPrefijo;
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(esperado::equals);
    }
}
