package co.uniquindio.Alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones de registro e inicio de sesión de usuarios")
public class AuthController {

    // =========================
    // ENDPOINT 1: POST /api/auth/register
    // =========================
    @PostMapping("/register")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Permite a un usuario registrarse como huésped o anfitrión en la plataforma"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> registrarUsuario(
            @Parameter(description = "Nombre completo del usuario", example = "Juan Pérez") @RequestParam String nombre,
            @Parameter(description = "Correo electrónico", example = "juan@example.com") @RequestParam String email,
            @Parameter(description = "Contraseña del usuario", example = "123456") @RequestParam String contrasena,
            @Parameter(description = "Rol del usuario (HUESPED o ANFITRION)", example = "HUESPED") @RequestParam String rol
    ) {
        return ResponseEntity.status(201).body("Usuario registrado: " + nombre + " (" + rol + ")");
    }

    // =========================
    // ENDPOINT 2: POST /api/auth/login
    // =========================
    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Permite a un usuario autenticarse y obtener un token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> iniciarSesion(
            @Parameter(description = "Correo electrónico del usuario", example = "juan@example.com") @RequestParam String email,
            @Parameter(description = "Contraseña del usuario", example = "123456") @RequestParam String contrasena
    ) {
        return ResponseEntity.ok("Inicio de sesión exitoso para: " + email);
    }
}
