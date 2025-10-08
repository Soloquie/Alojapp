package co.uniquindio.alojapp.negocio.excepciones;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) { super(message); }
}