package co.uniquindio.alojapp.config;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorPayload {
    private String timestamp;
    private int status;
    private String error;
    private String code;      // opcional
    private String message;
    private String path;
    private Map<String, String> validation; // para errores de @Valid
}