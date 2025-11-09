package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.config.JwtProperties;
import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.LoginRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroAnfitrionRequest;
import co.uniquindio.alojapp.negocio.DTO.response.LoginResponse;
import co.uniquindio.alojapp.negocio.Service.FotoPerfilService;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.Service.AnfitrionService;
// la que te propuse
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.JwtService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login, registro de usuarios y refresh token")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final AnfitrionService anfitrionService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;
    private final JwtProperties jwtProps;
    private final FotoPerfilService fotoPerfilService;

    // ====== LOGIN (placeholder: implementa tu JWT cuando lo tengas) ======
    @Operation(summary = "Iniciar sesión (JWT)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        try {
            // autentica contra tu UserDetailsService + PasswordEncoder
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Contraseña Incorrectos"); // 401
        }

        // Si pasó, credenciales OK
        UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtService.generateToken(user, Map.of());

        Usuario u = usuarioRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Derivar un rol legible para el front (ajústalo a tu modelo real)
        String rol = (u.getPerfilAdministrador() != null) ? "ADMIN"
                : (u.getPerfilAnfitrion() != null)   ? "ANFITRION"
                : "HUESPED";

        LoginResponse resp = LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .usuarioId(u.getId())
                .nombre(u.getNombre())
                .email(u.getEmail())
                .rol(rol)
                .expiresIn(jwtProps.getExpirationMs()) // si no usas JwtProperties, elimina esta línea
                .build();

        return ResponseEntity.ok(resp);
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

    @PostMapping(
            value = "/registro-huesped",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Registrar nuevo huésped (multipart/form-data con foto opcional)",
            description = "Envia la parte 'request' (JSON de RegistroUsuarioRequest) y opcionalmente la parte 'foto' (image/*)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "409", description = "Email duplicado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioDTO> registrarHuespedMultipart(
            @Valid @RequestPart("request") RegistroUsuarioRequest request,
            @RequestPart(value = "foto", required = false) MultipartFile foto
    ) throws Exception {

        UsuarioDTO creado = usuarioService.registrar(request);

        if (foto != null && !foto.isEmpty()) {
            // Subir a Cloudinary
            String url = fotoPerfilService.subirFotoPerfil(creado.getId(), foto);
            // Guardar URL en el usuario (y opcionalmente eliminar foto anterior si existía)
            usuarioService.actualizarFotoPerfil(creado.getId(), url);
            // Reflejar en la respuesta
            creado.setFotoPerfilUrl(url);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ====== REFRESH TOKEN (placeholder) ======
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token (JWT)")
    public ResponseEntity<?> refresh() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("TODO: implementar refresh de JWT");
    }
}
