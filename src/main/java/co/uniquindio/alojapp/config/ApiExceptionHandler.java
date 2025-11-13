package co.uniquindio.alojapp.config;

import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.BadRequestException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(RecursoNoEncontradoException ex, HttpServletRequest req) {
        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 404,
                "error", "Recurso no encontrado",   // pon aquí el texto que tú quieras
                "message", ex.getMessage(),
                "validation", null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AccesoNoAutorizadoException.class)
    public ResponseEntity<Map<String,Object>> handleForbidden(AccesoNoAutorizadoException ex) {
        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 403,
                "error", "Acceso denegado",
                "message", ex.getMessage(),
                "validation", null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,Object>> handleBadRequest(BadRequestException ex) {
        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 400,
                "error", "Bad Request",
                "message", ex.getMessage(),
                "validation", null
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage, (a, b)->a));
        Map<String,Object> body = Map.of(
                "timestamp", OffsetDateTime.now().toString(),
                "status", 400,
                "error", "Datos inválidos",
                "message", "Hay errores de validación",
                "validation", fieldErrors
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex,
                                                                HttpServletRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Error inesperado");
        body.put("message", Objects.toString(ex.getMessage(), "Ocurrió un error en el servidor"));
        body.put("path", request.getRequestURI());
        body.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
