package co.uniquindio.alojapp.negocio.excepciones;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}