package co.uniquindio.alojapp.negocio.excepciones;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}