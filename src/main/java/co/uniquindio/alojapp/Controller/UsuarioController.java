package co.uniquindio.alojapp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión del perfil y datos personales del usuario")
@SecurityRequirement(name = "bearerAuth") // ← requiere JWT
public class UsuarioController {

    // ===== DTOs simulados para ejemplos =====
    static class UsuarioPerfilResponse {
        @Schema(example = "123")
        public String id;
        @Schema(example = "usuario@correo.com")
        public String email;
        @Schema(example = "Juan Pérez")
        public String nombre;
        @Schema(example = "Huésped")
        public String rol;
    }

    static class ActualizarPerfilRequest {
        @Schema(example = "Juan Pérez")
        public String nombre;
        @Schema(example = "nueva_clave123")
        public String password;
    }

    // =========================
    // ENDPOINT 1: GET /api/usuarios/perfil
    // =========================
    @GetMapping("/perfil")
    @Operation(
            summary = "Obtener perfil del usuario autenticado",
            description = "Retorna la información completa del usuario actualmente autenticado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil obtenido correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioPerfilResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"123\", \"email\": \"usuario@correo.com\", \"nombre\": \"Juan Pérez\", \"rol\": \"Huésped\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<UsuarioPerfilResponse> obtenerPerfil() {
        UsuarioPerfilResponse perfil = new UsuarioPerfilResponse();
        perfil.id = "123";
        perfil.email = "usuario@correo.com";
        perfil.nombre = "Juan Pérez";
        perfil.rol = "Huésped";
        return ResponseEntity.ok(perfil);
    }

    // =========================
    // ENDPOINT 2: PUT /api/usuarios/perfil
    // =========================
    @PutMapping("/perfil")
    @Operation(
            summary = "Actualizar perfil del usuario autenticado",
            description = "Permite modificar los datos personales del usuario actual"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<String> actualizarPerfil(
            @RequestBody ActualizarPerfilRequest datos
    ) {
        return ResponseEntity.ok("Perfil actualizado correctamente");
    }

    // =========================
    // ENDPOINT 3: GET /api/usuarios
    // =========================
    @GetMapping
    @Operation(
            summary = "Listar todos los usuarios (solo ADMIN)",
            description = "Devuelve un listado general de todos los usuarios registrados en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    public ResponseEntity<List<UsuarioPerfilResponse>> listarUsuarios() {
        List<UsuarioPerfilResponse> lista = new ArrayList<>();
        UsuarioPerfilResponse u = new UsuarioPerfilResponse();
        u.id = "123";
        u.email = "usuario@correo.com";
        u.nombre = "Juan Pérez";
        u.rol = "Huésped";
        lista.add(u);
        return ResponseEntity.ok(lista);
    }
}
