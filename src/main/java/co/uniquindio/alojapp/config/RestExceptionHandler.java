// co.uniquindio.alojapp.config.RestExceptionHandler
package co.uniquindio.alojapp.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String,Object>> handleRSE(ResponseStatusException ex) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getStatusCode().toString()); // 200 OK / 400 BAD_REQUEST...
        body.put("message", ex.getReason());
        body.put("exception", ex.getClass().getName());
        body.put("cause", ex.getCause() != null ? ex.getCause().toString() : null);
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleAny(Exception ex) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        body.put("exception", ex.getClass().getName());
        body.put("cause", ex.getCause() != null ? ex.getCause().toString() : null);
        ex.printStackTrace(); // asegura stack en consola
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
