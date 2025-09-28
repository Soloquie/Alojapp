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

    // ===== DTOs simulados para ejemplos =====
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

    static class RegistroRequest {
        @Schema(example = "usuario@correo.com") public String email;
        @Schema(example = "Juan Pérez") public String nombre;
        @Schema(example = "contraseñaSegura123") public String password;
        @Schema(example = "3124567890") public String telefono;
        @Schema(example = "1998-05-12") public String fechaNacimiento;
    }

    static class RegistroAnfitrionRequest extends RegistroRequest {
        @Schema(example = "Descripción personal del anfitrión")
        public String descripcion;
        @Schema(example = "CC123456789")
        public String documentoIdentidad;
    }

    static class RegistroResponse {
        @Schema(example = "Usuario registrado exitosamente")
        public String mensaje;
    }

    static class RefreshTokenRequest {
        @Schema(example = "eyJhbGciOiJIUzI1NiJ9.refresh...")
        public String refreshToken;
    }

    static class RefreshTokenResponse {
        @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
        public String accessToken;
    }

    // =========================
    // ENDPOINT 1: POST /api/auth/login
    // =========================
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse y obtener un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"token\": \"eyJhbGciOiJIUzI1NiJ9...\", \"mensaje\": \"Usuario autenticado correctamente\" }"
                            )
                    )),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        response.token = "eyJhbGciOiJIUzI1NiJ9...";
        response.mensaje = "Usuario autenticado correctamente";
        return ResponseEntity.ok(response);
    }

    // =========================
    // ENDPOINT 2: POST /api/auth/registro-huesped
    // =========================
    @PostMapping("/registro-huesped")
    @Operation(summary = "Registrar nuevo huésped", description = "Permite a un usuario registrarse como huésped en la plataforma")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Huésped registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<RegistroResponse> registrarHuesped(@RequestBody RegistroRequest datos) {
        RegistroResponse r = new RegistroResponse();
        r.mensaje = "Usuario registrado exitosamente como HUESPED";
        return ResponseEntity.status(201).body(r);
    }

    // =========================
    // ENDPOINT 3: POST /api/auth/registro-anfitrion
    // =========================
    @PostMapping("/registro-anfitrion")
    @Operation(summary = "Registrar nuevo anfitrión", description = "Permite a un usuario registrarse como anfitrión en la plataforma")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Anfitrión registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<RegistroResponse> registrarAnfitrion(@RequestBody RegistroAnfitrionRequest datos) {
        RegistroResponse r = new RegistroResponse();
        r.mensaje = "Usuario registrado exitosamente como ANFITRION";
        return ResponseEntity.status(201).body(r);
    }

    // =========================
    // ENDPOINT 4: POST /api/auth/refresh
    // =========================
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token JWT", description = "Permite obtener un nuevo access token usando un refresh token válido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refrescado correctamente"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido o expirado")
    })
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest datos) {
        RefreshTokenResponse r = new RefreshTokenResponse();
        r.accessToken = "eyJhbGciOiJIUzI1NiJ9.newToken...";
        return ResponseEntity.ok(r);
    }
}
