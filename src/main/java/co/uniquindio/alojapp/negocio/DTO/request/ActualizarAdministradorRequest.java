package co.uniquindio.alojapp.negocio.DTO.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ActualizarAdministradorRequest {
    private String nivelAcceso;   // opcional
    private String permisosJson;  // opcional
}
