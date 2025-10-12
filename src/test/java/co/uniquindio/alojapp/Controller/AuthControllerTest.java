package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.config.JwtProperties;
import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.LoginRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroAnfitrionRequest;
import co.uniquindio.alojapp.negocio.DTO.response.LoginResponse;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.Service.AnfitrionService;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para AuthController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController - Unit Tests")
public class AuthControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AnfitrionService anfitrionService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtProperties jwtProps;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private RegistroUsuarioRequest registroHuespedRequest;
    private RegistroAnfitrionRequest registroAnfitrionRequest;
    private UsuarioDTO usuarioDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@correo.com");
        loginRequest.setPassword("password123");

        registroHuespedRequest = new RegistroUsuarioRequest();
        registroHuespedRequest.setNombre("Juan Pérez");
        registroHuespedRequest.setEmail("juan@correo.com");
        registroHuespedRequest.setPassword("password123");
        registroHuespedRequest.setTelefono("+573001234567");
        registroHuespedRequest.setFechaNacimiento(LocalDate.of(1990, 5, 15));

        registroAnfitrionRequest = new RegistroAnfitrionRequest();
        registroAnfitrionRequest.setNombre("María López");
        registroAnfitrionRequest.setEmail("maria@correo.com");
        registroAnfitrionRequest.setPassword("password123");
        registroAnfitrionRequest.setTelefono("+573007654321");
        registroAnfitrionRequest.setFechaNacimiento(LocalDate.of(1985, 8, 20));
        registroAnfitrionRequest.setDescripcionPersonal("Anfitrión experimentado");
        registroAnfitrionRequest.setDocumentosLegalesUrl("https://docs.com/maria.pdf");

        usuarioDTO = UsuarioDTO.builder()
                .id(1)
                .nombre("Juan Pérez")
                .email("juan@correo.com")
                .telefono("+573001234567")
                .estado("ACTIVO")
                .fechaRegistro(LocalDateTime.now())
                .fechaNacimiento(LocalDate.of(1990, 5, 15))
                .build();

        usuario = Usuario.builder()
                .id(1)
                .nombre("Juan Pérez")
                .email("juan@correo.com")
                .contrasenaHash("encodedPassword")
                .telefono("+573001234567")
                .fechaNacimiento(LocalDate.of(1990, 5, 15))
                .fechaRegistro(LocalDateTime.now())
                .estado(EstadoUsuario.ACTIVO)
                .build();
    }

    // =========================================================================
    // TESTS PARA LOGIN
    // =========================================================================

    @Test
    @DisplayName("Login - Credenciales válidas debería retornar 200 OK con token")
    void login_CredencialesValidas_DeberiaRetornar200ConToken() {
        // Arrange
        String token = "jwt.token.here";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername(loginRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn(token);
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isEqualTo(token);
        assertThat(response.getBody().getTokenType()).isEqualTo("Bearer");
        assertThat(response.getBody().getUsuarioId()).isEqualTo(1);
        assertThat(response.getBody().getNombre()).isEqualTo("Juan Pérez");
        assertThat(response.getBody().getEmail()).isEqualTo("juan@correo.com");
        assertThat(response.getBody().getRol()).isEqualTo("HUESPED");
        assertThat(response.getBody().getExpiresIn()).isEqualTo(3600000L);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(loginRequest.getEmail());
        verify(jwtService).generateToken(any(UserDetails.class), anyMap());
        verify(usuarioRepository).findByEmail(loginRequest.getEmail());
    }

    @Test
    @DisplayName("Login - Credenciales inválidas debería retornar 401 UNAUTHORIZED")
    void login_CredencialesInvalidas_DeberiaRetornar401Unauthorized() {
        // Arrange
        doThrow(new BadCredentialsException("Credenciales inválidas"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(userDetailsService, jwtService, usuarioRepository);
    }

    @Test
    @DisplayName("Login - Usuario no encontrado después de autenticación debería lanzar excepción")
    void login_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(loginRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("token");
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authController.login(loginRequest))
                .isInstanceOf(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
                .hasMessage("Usuario no encontrado");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(loginRequest.getEmail());
        verify(jwtService).generateToken(any(UserDetails.class), anyMap());
        verify(usuarioRepository).findByEmail(loginRequest.getEmail());
    }

    @Test
    @DisplayName("Login - Rol ADMIN cuando usuario tiene perfil administrador")
    void login_UsuarioConPerfilAdministrador_DeberiaRetornarRolAdmin() {
        // Arrange
        Administrador perfilAdmin = mock(Administrador.class);
        Usuario usuarioAdmin = Usuario.builder()
                .id(1)
                .nombre("Admin User")
                .email("admin@correo.com")
                .contrasenaHash("encodedPassword")
                .estado(EstadoUsuario.ACTIVO)
                .perfilAdministrador(perfilAdmin)
                .build();

        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername("admin@correo.com")).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("token");
        when(usuarioRepository.findByEmail("admin@correo.com")).thenReturn(Optional.of(usuarioAdmin));

        LoginRequest adminLoginRequest = new LoginRequest();
        adminLoginRequest.setEmail("admin@correo.com");
        adminLoginRequest.setPassword("password123");

        // Act
        ResponseEntity<LoginResponse> response = authController.login(adminLoginRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRol()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Login - Rol ANFITRION cuando usuario tiene perfil anfitrión")
    void login_UsuarioConPerfilAnfitrion_DeberiaRetornarRolAnfitrion() {
        // Arrange
        Anfitrion perfilAnfitrion = mock(Anfitrion.class);
        Usuario usuarioAnfitrion = Usuario.builder()
                .id(1)
                .nombre("Anfitrion User")
                .email("anfitrion@correo.com")
                .contrasenaHash("encodedPassword")
                .estado(EstadoUsuario.ACTIVO)
                .perfilAnfitrion(perfilAnfitrion)
                .build();

        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername("anfitrion@correo.com")).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("token");
        when(usuarioRepository.findByEmail("anfitrion@correo.com")).thenReturn(Optional.of(usuarioAnfitrion));

        LoginRequest anfitrionLoginRequest = new LoginRequest();
        anfitrionLoginRequest.setEmail("anfitrion@correo.com");
        anfitrionLoginRequest.setPassword("password123");

        // Act
        ResponseEntity<LoginResponse> response = authController.login(anfitrionLoginRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRol()).isEqualTo("ANFITRION");
    }

    @Test
    @DisplayName("Login - Rol HUESPED cuando usuario no tiene perfiles especiales")
    void login_UsuarioSinPerfilesEspeciales_DeberiaRetornarRolHuesped() {
        // Arrange
        Usuario usuarioHuesped = Usuario.builder()
                .id(1)
                .nombre("Huesped User")
                .email("huesped@correo.com")
                .contrasenaHash("encodedPassword")
                .estado(EstadoUsuario.ACTIVO)
                // Sin perfilAnfitrion ni perfilAdministrador
                .build();

        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername("huesped@correo.com")).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("token");
        when(usuarioRepository.findByEmail("huesped@correo.com")).thenReturn(Optional.of(usuarioHuesped));

        LoginRequest huespedLoginRequest = new LoginRequest();
        huespedLoginRequest.setEmail("huesped@correo.com");
        huespedLoginRequest.setPassword("password123");

        // Act
        ResponseEntity<LoginResponse> response = authController.login(huespedLoginRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRol()).isEqualTo("HUESPED");
    }

    // =========================================================================
    // TESTS PARA REGISTRO HUÉSPED
    // =========================================================================

    @Test
    @DisplayName("Registrar huésped - Debería retornar 201 CREATED")
    void registrarHuesped_DeberiaRetornar201Created() {
        // Arrange
        when(usuarioService.registrar(registroHuespedRequest)).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = authController.registrarHuesped(registroHuespedRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(usuarioDTO);
        assertThat(response.getBody().getNombre()).isEqualTo("Juan Pérez");
        assertThat(response.getBody().getEmail()).isEqualTo("juan@correo.com");
        assertThat(response.getBody().getEstado()).isEqualTo("ACTIVO");

        verify(usuarioService, times(1)).registrar(registroHuespedRequest);
        verifyNoInteractions(anfitrionService);
    }

    @Test
    @DisplayName("Registrar huésped - Campos completos tienen valores correctos")
    void registrarHuesped_CamposCompletos_TienenValoresCorrectos() {
        // Arrange
        when(usuarioService.registrar(registroHuespedRequest)).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = authController.registrarHuesped(registroHuespedRequest);

        // Assert
        UsuarioDTO responseBody = response.getBody();
        assertThat(responseBody.getId()).isEqualTo(1);
        assertThat(responseBody.getNombre()).isEqualTo("Juan Pérez");
        assertThat(responseBody.getEmail()).isEqualTo("juan@correo.com");
        assertThat(responseBody.getTelefono()).isEqualTo("+573001234567");
        assertThat(responseBody.getEstado()).isEqualTo("ACTIVO");
        assertThat(responseBody.getFechaNacimiento()).isEqualTo(LocalDate.of(1990, 5, 15));
        assertThat(responseBody.getFechaRegistro()).isNotNull();
    }

    // =========================================================================
    // TESTS PARA REGISTRO ANFITRIÓN
    // =========================================================================

    @Test
    @DisplayName("Registrar anfitrión - Debería retornar 201 CREATED y crear perfil")
    void registrarAnfitrion_DeberiaRetornar201CreatedYCrearPerfil() {
        // Arrange
        when(usuarioService.registrar(registroAnfitrionRequest)).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = authController.registrarAnfitrion(registroAnfitrionRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(usuarioDTO);

        verify(usuarioService, times(1)).registrar(registroAnfitrionRequest);
        verify(anfitrionService, times(1)).crearPerfil(
                usuarioDTO.getId(),
                registroAnfitrionRequest.getDescripcionPersonal(),
                registroAnfitrionRequest.getDocumentosLegalesUrl(),
                registroAnfitrionRequest.getFechaRegistro()
        );
    }

    @Test
    @DisplayName("Registrar anfitrión - Con fecha registro nula debería procesar correctamente")
    void registrarAnfitrion_FechaRegistroNula_DeberiaProcesarCorrectamente() {
        // Arrange
        registroAnfitrionRequest.setFechaRegistro(null);
        when(usuarioService.registrar(registroAnfitrionRequest)).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = authController.registrarAnfitrion(registroAnfitrionRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        verify(anfitrionService, times(1)).crearPerfil(
                usuarioDTO.getId(),
                registroAnfitrionRequest.getDescripcionPersonal(),
                registroAnfitrionRequest.getDocumentosLegalesUrl(),
                null
        );
    }

    @Test
    @DisplayName("Registrar anfitrión - Campos específicos de anfitrión se pasan correctamente")
    void registrarAnfitrion_CamposEspecificos_SePasanCorrectamente() {
        // Arrange
        LocalDate fechaRegistro = LocalDate.now();
        registroAnfitrionRequest.setFechaRegistro(fechaRegistro);
        when(usuarioService.registrar(registroAnfitrionRequest)).thenReturn(usuarioDTO);

        // Act
        ResponseEntity<UsuarioDTO> response = authController.registrarAnfitrion(registroAnfitrionRequest);

        // Assert
        verify(anfitrionService, times(1)).crearPerfil(
                usuarioDTO.getId(),
                "Anfitrión experimentado",
                "https://docs.com/maria.pdf",
                fechaRegistro
        );
    }

    // =========================================================================
    // TESTS PARA REFRESH TOKEN
    // =========================================================================

    @Test
    @DisplayName("Refresh token - Debería retornar 501 NOT_IMPLEMENTED")
    void refreshToken_DeberiaRetornar501NotImplemented() {
        // Act
        ResponseEntity<?> response = authController.refresh();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_IMPLEMENTED);
        assertThat(response.getBody()).isEqualTo("TODO: implementar refresh de JWT");
    }

    // =========================================================================
    // TESTS DE CASOS BORDE Y VALIDACIONES
    // =========================================================================

    @Test
    @DisplayName("Login - Diferentes emails y passwords")
    void login_DiferentesEmailsYPasswords_DeberiaProcesarCorrectamente() {
        // Arrange
        LoginRequest request1 = new LoginRequest();
        request1.setEmail("user1@test.com");
        request1.setPassword("pass1");

        LoginRequest request2 = new LoginRequest();
        request2.setEmail("user2@test.com");
        request2.setPassword("pass2");

        UserDetails userDetails1 = mock(UserDetails.class);
        UserDetails userDetails2 = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername("user1@test.com")).thenReturn(userDetails1);
        when(userDetailsService.loadUserByUsername("user2@test.com")).thenReturn(userDetails2);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("token");
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<LoginResponse> response1 = authController.login(request1);
        ResponseEntity<LoginResponse> response2 = authController.login(request2);

        // Assert
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(userDetailsService).loadUserByUsername("user1@test.com");
        verify(userDetailsService).loadUserByUsername("user2@test.com");
    }

    @Test
    @DisplayName("Registros - Verificar que no se mezclan los servicios")
    void registros_NoDeberianMezclarServicios() {
        // Arrange
        when(usuarioService.registrar(registroHuespedRequest)).thenReturn(usuarioDTO);
        when(usuarioService.registrar(registroAnfitrionRequest)).thenReturn(usuarioDTO);

        // Act
        authController.registrarHuesped(registroHuespedRequest);
        authController.registrarAnfitrion(registroAnfitrionRequest);

        // Assert
        verify(usuarioService, times(1)).registrar(registroHuespedRequest);
        verify(usuarioService, times(1)).registrar(registroAnfitrionRequest);
        verify(anfitrionService, times(1)).crearPerfil(anyInt(), anyString(), anyString(), any());
        verifyNoMoreInteractions(anfitrionService);
    }

    @Test
    @DisplayName("Login Response - Estructura completa del objeto")
    void loginResponse_EstructuraCompleta_EsCorrecta() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(7200000L);
        when(userDetailsService.loadUserByUsername(loginRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("mock.jwt.token");
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);
        LoginResponse loginResponse = response.getBody();

        // Assert
        assertThat(loginResponse.getToken()).isEqualTo("mock.jwt.token");
        assertThat(loginResponse.getTokenType()).isEqualTo("Bearer");
        assertThat(loginResponse.getUsuarioId()).isEqualTo(1);
        assertThat(loginResponse.getNombre()).isEqualTo("Juan Pérez");
        assertThat(loginResponse.getEmail()).isEqualTo("juan@correo.com");
        assertThat(loginResponse.getRol()).isEqualTo("HUESPED");
        assertThat(loginResponse.getExpiresIn()).isEqualTo(7200000L);
    }

    @Test
    @DisplayName("Autenticación - Flujo completo de login verificado")
    void autenticacion_FlujoCompleto_Verificado() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername(loginRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("generated.token");
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // Assert - Verificar que se llamaron todos los servicios en el orden correcto
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService).loadUserByUsername(loginRequest.getEmail());
        verify(jwtService).generateToken(any(UserDetails.class), anyMap());
        verify(usuarioRepository).findByEmail(loginRequest.getEmail());
        verify(jwtProps).getExpirationMs();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Usuario - Estados diferentes se manejan correctamente")
    void usuario_EstadosDiferentes_SeManejanCorrectamente() {
        // Arrange
        Usuario usuarioInactivo = Usuario.builder()
                .id(2)
                .nombre("Usuario Inactivo")
                .email("inactivo@correo.com")
                .contrasenaHash("encodedPassword")
                .estado(EstadoUsuario.INACTIVO)
                .build();

        UserDetails userDetails = mock(UserDetails.class);

        when(jwtProps.getExpirationMs()).thenReturn(3600000L);
        when(userDetailsService.loadUserByUsername("inactivo@correo.com")).thenReturn(userDetails);
        when(jwtService.generateToken(any(UserDetails.class), anyMap())).thenReturn("token");
        when(usuarioRepository.findByEmail("inactivo@correo.com")).thenReturn(Optional.of(usuarioInactivo));

        LoginRequest request = new LoginRequest();
        request.setEmail("inactivo@correo.com");
        request.setPassword("password123");

        // Act
        ResponseEntity<LoginResponse> response = authController.login(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsuarioId()).isEqualTo(2);
    }
}