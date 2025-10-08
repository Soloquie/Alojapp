package co.uniquindio.alojapp.negocio.DTO.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RegistrarPerfilAnfitrionRequest {
    private String descripcionPersonal;
    private String documentosLegalesUrl;
    private LocalDate fechaRegistro; // opcional; si no, lo setea el DAO
}
