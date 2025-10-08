package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroAnfitrionRequest;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.Service.AnfitrionService;
// la que te propuse
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login, registro de usuarios y refresh token")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final AnfitrionService anfitrionService;

    // ====== LOGIN (placeholder: implementa tu JWT cuando lo tengas) ======
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión (JWT)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<?> login() {
        // Aquí iría tu authenticate + generación de JWT (cuando integres JWT)
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("TODO: implementar login con JWT");
    }

    // ====== REGISTRO HUESPED ======
    @PostMapping("/registro-huesped")
    @Operation(summary = "Registrar nuevo huésped")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "409", description = "Email duplicado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioDTO> registrarHuesped(@Valid @RequestBody RegistroUsuarioRequest request) {
        // UsuarioServiceIMPL.registrar(...) ya valida duplicados y lanza DuplicateEmailException
        UsuarioDTO creado = usuarioService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ====== REGISTRO ANFITRIÓN ======
    @PostMapping("/registro-anfitrion")
    @Operation(summary = "Registrar nuevo anfitrión (crea el usuario y su perfil de anfitrión)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "409", description = "Email duplicado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioDTO> registrarAnfitrion(@Valid @RequestBody RegistroAnfitrionRequest request) {
        // 1) Crear el usuario base (huésped)
        UsuarioDTO user = usuarioService.registrar(request);

        // 2) Crear el perfil de anfitrión
        anfitrionService.crearPerfil(
                user.getId(),
                request.getDescripcionPersonal(),
                request.getDocumentosLegalesUrl(),
                request.getFechaRegistro() // puede ser null
        );

        // Devuelvo el usuario creado; si tienes un AnfitrionDTO, también puedes retornarlo
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // ====== REFRESH TOKEN (placeholder) ======
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token (JWT)")
    public ResponseEntity<?> refresh() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("TODO: implementar refresh de JWT");
    }
}
