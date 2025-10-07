package co.uniquindio.alojapp.negocio.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response de autenticación con token JWT")
public class LoginResponse {

    @Schema(description = "Token JWT para autenticación", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Tipo de token", example = "Bearer")
    private String tokenType;

    @Schema(description = "ID del usuario autenticado", example = "123")
    private Integer usuarioId;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Email del usuario", example = "juan.perez@correo.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "HUESPED")
    private String rol;

    @Schema(description = "Tiempo de expiración del token en milisegundos", example = "900000")
    private Long expiresIn;
}