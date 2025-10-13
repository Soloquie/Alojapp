package co.uniquindio.alojapp.config;

import co.uniquindio.alojapp.negocio.excepciones.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 -> con el texto EXACTO que quieres en "error"
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorPayload> notFound(RecursoNoEncontradoException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(base(HttpStatus.NOT_FOUND, "Acceso denegado", ex.getMessage(), null));
    }

    // 403
    @ExceptionHandler(AccesoNoAutorizadoException.class)
    public ResponseEntity<ErrorPayload> forbidden(AccesoNoAutorizadoException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(base(HttpStatus.FORBIDDEN, "Acceso denegado", ex.getMessage(), null));
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorPayload> badRequest(BadRequestException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(base(HttpStatus.BAD_REQUEST, "Solicitud inválida", ex.getMessage(), null));
    }

    // @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorPayload> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String,String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a,b)->a));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(base(HttpStatus.BAD_REQUEST, "Datos inválidos", "Error de validación", fields));
    }

    // fallback -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPayload> fallback(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(base(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", "Ocurrió un error inesperado", null));
    }

    private ErrorPayload base(HttpStatus status, String errorText, String message, Map<String,String> validation) {
        return ErrorPayload.builder()
                .timestamp(OffsetDateTime.now().toString())
                .status(status.value())
                .error(errorText)
                .message(message)
                .validation(validation)
                .build();
    }
}
