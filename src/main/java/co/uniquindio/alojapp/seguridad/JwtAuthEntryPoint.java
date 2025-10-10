package co.uniquindio.alojapp.seguridad;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    public void commence(HttpServletRequest req, HttpServletResponse res, org.springframework.security.core.AuthenticationException ex)
            throws IOException {
        // 401
        ApiError body = ApiError.builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code("UNAUTHORIZED")
                .message("No autenticado o token inválido")
                .path(req.getRequestURI())
                .build();
        write(res, HttpStatus.UNAUTHORIZED, body);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, org.springframework.security.access.AccessDeniedException ex)
            throws IOException {
        // 403
        ApiError body = ApiError.builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .code("ACCESS_DENIED")
                .message("No tiene permisos para acceder a este recurso")
                .path(req.getRequestURI())
                .build();
        write(res, HttpStatus.FORBIDDEN, body);
    }

    public void commenceWithCode(HttpServletRequest req, HttpServletResponse res, String code, String message) throws IOException {
        ApiError body = ApiError.builder()
                .timestamp(Instant.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code(code)
                .message(message)
                .path(req.getRequestURI())
                .build();
        write(res, HttpStatus.UNAUTHORIZED, body);
    }

    private void write(HttpServletResponse res, HttpStatus status, ApiError body) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json");
        mapper.writeValue(res.getOutputStream(), body);
    }
}
