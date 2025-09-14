package co.uniquindio.alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para registrar e iniciar sesión de usuarios")
public class AuthController {

    // DTO de ejemplo interno (puedes moverlo a un paquete dto)
    static class LoginRequest {
        @Schema(example = "usuario@correo.com")
        public String email;
        @Schema(example = "contraseñaSegura123")
        public String password;
    }

    static class LoginResponse {
        @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
        public String token;
        @Schema(example = "Usuario autenticado correctamente")
        public String mensaje;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Permite a un usuario autenticarse y obtener un token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"token\": \"eyJhbGciOiJIUzI1NiJ9...\", \"mensaje\": \"Usuario autenticado correctamente\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        LoginResponse response = new LoginResponse();
        response.token = "eyJhbGciOiJIUzI1NiJ9...";
        response.mensaje = "Usuario autenticado correctamente";
        return ResponseEntity.ok(response);
    }
}
