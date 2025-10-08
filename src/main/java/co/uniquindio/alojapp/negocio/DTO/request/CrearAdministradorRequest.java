package co.uniquindio.alojapp.negocio.DTO.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CrearAdministradorRequest {
    private Integer usuarioId;
    private String  nivelAcceso;
    private String  permisosJson;
}
