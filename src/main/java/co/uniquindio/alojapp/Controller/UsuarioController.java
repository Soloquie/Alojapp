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
@Tag(name = "Usuarios", description = "Gestión del perfil, datos personales y métodos de pago del usuario")
@SecurityRequirement(name = "bearerAuth") // requiere JWT
public class UsuarioController {

    // ===== DTOs simulados para ejemplos =====
    static class UsuarioPerfilResponse {
        @Schema(example = "123") public String id;
        @Schema(example = "usuario@correo.com") public String email;
        @Schema(example = "Juan Pérez") public String nombre;
        @Schema(example = "HUESPED") public String rol;
        @Schema(example = "ACTIVO") public String estado;
    }

    static class ActualizarPerfilRequest {
        @Schema(example = "Juan Pérez") public String nombre;
        @Schema(example = "nueva_clave123") public String password;
    }

    static class MetodoPagoRequest {
        @Schema(example = "VISA") public String tipo;
        @Schema(example = "4111111111111111") public String numero;
        @Schema(example = "12/27") public String fechaExpiracion;
    }

    static class MetodoPagoResponse {
        @Schema(example = "88") public String id;
        @Schema(example = "VISA") public String tipo;
        @Schema(example = "****1111") public String numeroEnmascarado;
        @Schema(example = "12/27") public String fechaExpiracion;
    }

    // =========================
    // ENDPOINT 1: GET /api/usuarios/perfil
    // =========================
    @GetMapping("/perfil")
    @Operation(summary = "Obtener perfil del usuario autenticado",
            description = "Retorna la información completa del usuario actualmente autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioPerfilResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"123\", \"email\": \"usuario@correo.com\", \"nombre\": \"Juan Pérez\", \"rol\": \"HUESPED\", \"estado\": \"ACTIVO\" }"
                            ))),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<UsuarioPerfilResponse> obtenerPerfil() {
        UsuarioPerfilResponse perfil = new UsuarioPerfilResponse();
        perfil.id = "123";
        perfil.email = "usuario@correo.com";
        perfil.nombre = "Juan Pérez";
        perfil.rol = "HUESPED";
        perfil.estado = "ACTIVO";
        return ResponseEntity.ok(perfil);
    }

    // =========================
    // ENDPOINT 2: PUT /api/usuarios/perfil
    // =========================
    @PutMapping("/perfil")
    @Operation(summary = "Actualizar perfil del usuario autenticado",
            description = "Permite modificar los datos personales del usuario actual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<String> actualizarPerfil(@RequestBody ActualizarPerfilRequest datos) {
        return ResponseEntity.ok("Perfil actualizado correctamente");
    }

    // =========================
    // ENDPOINT 3: GET /api/usuarios
    // =========================
    @GetMapping
    @Operation(summary = "Listar todos los usuarios (solo ADMIN)",
            description = "Devuelve un listado general de todos los usuarios registrados en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    public ResponseEntity<List<UsuarioPerfilResponse>> listarUsuarios() {
        List<UsuarioPerfilResponse> lista = new ArrayList<>();
        UsuarioPerfilResponse u = new UsuarioPerfilResponse();
        u.id = "123";
        u.email = "usuario@correo.com";
        u.nombre = "Juan Pérez";
        u.rol = "HUESPED";
        u.estado = "ACTIVO";
        lista.add(u);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 4: POST /api/usuarios/metodos-pago
    // =========================
    @PostMapping("/metodos-pago")
    @Operation(summary = "Agregar un método de pago",
            description = "Permite al usuario añadir un nuevo método de pago para sus reservas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Método de pago agregado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetodoPagoResponse.class),
                            examples = @ExampleObject(
                                    value = "{ \"id\": \"88\", \"tipo\": \"VISA\", \"numeroEnmascarado\": \"****1111\", \"fechaExpiracion\": \"12/27\" }"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<MetodoPagoResponse> agregarMetodoPago(@RequestBody MetodoPagoRequest datos) {
        MetodoPagoResponse m = new MetodoPagoResponse();
        m.id = "88";
        m.tipo = datos.tipo;
        m.numeroEnmascarado = "****1111";
        m.fechaExpiracion = datos.fechaExpiracion;
        return ResponseEntity.status(201).body(m);
    }

    // =========================
    // ENDPOINT 5: GET /api/usuarios/metodos-pago
    // =========================
    @GetMapping("/metodos-pago")
    @Operation(summary = "Listar métodos de pago del usuario",
            description = "Devuelve todos los métodos de pago registrados por el usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Métodos de pago obtenidos correctamente")
    })
    public ResponseEntity<List<MetodoPagoResponse>> listarMetodosPago() {
        List<MetodoPagoResponse> lista = new ArrayList<>();
        MetodoPagoResponse m = new MetodoPagoResponse();
        m.id = "88";
        m.tipo = "VISA";
        m.numeroEnmascarado = "****1111";
        m.fechaExpiracion = "12/27";
        lista.add(m);
        return ResponseEntity.ok(lista);
    }

    // =========================
    // ENDPOINT 6: DELETE /api/usuarios/metodos-pago/{id}
    // =========================
    @DeleteMapping("/metodos-pago/{id}")
    @Operation(summary = "Eliminar método de pago",
            description = "Permite al usuario eliminar un método de pago registrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Método de pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    public ResponseEntity<Void> eliminarMetodoPago(
            @Parameter(description = "ID del método de pago a eliminar", example = "88")
            @PathVariable String id
    ) {
        return ResponseEntity.noContent().build();
    }
}
