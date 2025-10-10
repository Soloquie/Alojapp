package co.uniquindio.alojapp.seguridad;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String timestamp;
    private int status;
    private String error;     // "Unauthorized"
    private String code;      // "TOKEN_EXPIRED" | "TOKEN_INVALID"
    private String message;   // "El token JWT ha expirado"
    private String path;
}
