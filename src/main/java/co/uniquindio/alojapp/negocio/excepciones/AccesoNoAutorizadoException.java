package co.uniquindio.alojapp.negocio.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccesoNoAutorizadoException extends DominioException {
    public AccesoNoAutorizadoException(String m) { super(m); }
}
