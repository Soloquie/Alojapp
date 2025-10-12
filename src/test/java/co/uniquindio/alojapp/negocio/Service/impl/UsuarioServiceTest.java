package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistrarPerfilAnfitrionRequest;
import co.uniquindio.alojapp.negocio.DTO.request.RegistroUsuarioRequest;
import co.uniquindio.alojapp.negocio.Service.impl.UsuarioServiceIMPL;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.UsuarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para UsuarioServiceIMPL
 *
 * OBJETIVO: Probar la lógica de negocio del servicio de usuarios de forma aislada
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService - Unit Tests")
public class UsuarioServiceTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceIMPL usuarioService;

    // DATOS DE PRUEBA
    private RegistroUsuarioRequest registroRequestValido;
    private ActualizarPerfilRequest actualizarRequestValido;
    private RegistrarPerfilAnfitrionRequest anfitrionRequestValido;
    private Usuario usuarioMock;
    private UsuarioDTO usuarioDTOMock;

    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer USUARIO_ID_INEXISTENTE = 999;
    private final String EMAIL_VALIDO = "ana.gomez@ejemplo.com";
    private final String EMAIL_DUPLICADO = "existente@ejemplo.com";
    private final String PASSWORD_VALIDA = "ClaveSegura1";
    private final String PASSWORD_INVALIDA = "clavedebil";
    private final String PASSWORD_HASH = "$2a$10$hashedpassword";

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        registroRequestValido = new RegistroUsuarioRequest();
        registroRequestValido.setNombre("Ana Gómez");
        registroRequestValido.setEmail(EMAIL_VALIDO);
        registroRequestValido.setPassword(PASSWORD_VALIDA);
        registroRequestValido.setTelefono("+573001112233");
        registroRequestValido.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        actualizarRequestValido = new ActualizarPerfilRequest();
        actualizarRequestValido.setNombre("Ana Gómez Actualizada");
        actualizarRequestValido.setTelefono("+573001112244");
        actualizarRequestValido.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        anfitrionRequestValido = new RegistrarPerfilAnfitrionRequest();
        anfitrionRequestValido.setDescripcionPersonal("Anfitrión experimentado");
        anfitrionRequestValido.setDocumentosLegalesUrl("https://example.com/docs.pdf");

        usuarioMock = Usuario.builder()
                .id(USUARIO_ID_VALIDO)
                .nombre("Ana Gómez")
                .email(EMAIL_VALIDO)
                .contrasenaHash(PASSWORD_HASH)
                .telefono("+573001112233")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .estado(EstadoUsuario.ACTIVO)
                .build();

        usuarioDTOMock = UsuarioDTO.builder()
                .id(USUARIO_ID_VALIDO)
                .nombre("Ana Gómez")
                .email(EMAIL_VALIDO)
                .telefono("+573001112233")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .estado(EstadoUsuario.ACTIVO.name())
                .esAnfitrion(false)
                .esAdmin(false)
                .build();
    }

    // ==================== REGISTRAR TESTS ====================

    @Test
    @DisplayName("REGISTRAR - Datos válidos crea usuario exitosamente")
    void registrar_DatosValidos_CreaUsuarioExitosamente() {
        // ARRANGE
        when(usuarioDAO.existsByEmail(EMAIL_VALIDO)).thenReturn(false);
        when(passwordEncoder.encode(PASSWORD_VALIDA)).thenReturn(PASSWORD_HASH);
        when(usuarioDAO.save(any(RegistroUsuarioRequest.class), eq(PASSWORD_HASH)))
                .thenReturn(usuarioDTOMock);

        // ACT
        UsuarioDTO resultado = usuarioService.registrar(registroRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(USUARIO_ID_VALIDO);

        verify(usuarioDAO, times(1)).existsByEmail(EMAIL_VALIDO);
        verify(passwordEncoder, times(1)).encode(PASSWORD_VALIDA);
        verify(usuarioDAO, times(1)).save(registroRequestValido, PASSWORD_HASH);
    }

    @Test
    @DisplayName("REGISTRAR - Email duplicado lanza IllegalArgumentException")
    void registrar_EmailDuplicado_LanzaIllegalArgumentException() {
        // ARRANGE
        when(usuarioDAO.existsByEmail(EMAIL_VALIDO)).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.registrar(registroRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El email ya está registrado");

        verify(usuarioDAO, times(1)).existsByEmail(EMAIL_VALIDO);
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioDAO, never()).save(any(), anyString());
    }

    @Test
    @DisplayName("REGISTRAR - Nombre muy corto lanza IllegalArgumentException")
    void registrar_NombreMuyCorto_LanzaIllegalArgumentException() {
        // ARRANGE
        registroRequestValido.setNombre("Ab");

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.registrar(registroRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre debe tener entre 3 y 100 caracteres");

        verify(usuarioDAO, never()).existsByEmail(anyString());
    }

    @Test
    @DisplayName("REGISTRAR - Contraseña débil lanza IllegalArgumentException")
    void registrar_ContrasenaDebil_LanzaIllegalArgumentException() {
        // ARRANGE
        registroRequestValido.setPassword(PASSWORD_INVALIDA);
        when(usuarioDAO.existsByEmail(EMAIL_VALIDO)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.registrar(registroRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La contraseña debe incluir al menos una mayúscula y un número");

        verify(usuarioDAO, atLeastOnce()).existsByEmail(EMAIL_VALIDO);
    }


    @Test
    @DisplayName("REGISTRAR - Menor de edad lanza IllegalArgumentException")
    void registrar_MenorDeEdad_LanzaIllegalArgumentException() {
        // ARRANGE
        registroRequestValido.setFechaNacimiento(LocalDate.now().minusYears(17));

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.registrar(registroRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario debe ser mayor de 18 años");

        verify(usuarioDAO, atLeastOnce()).existsByEmail(EMAIL_VALIDO);
    }

    // ==================== OBTENER TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Usuario existe retorna DTO")
    void obtenerPorId_UsuarioExiste_RetornaDTO() {
        // ARRANGE
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = usuarioService.obtenerPorId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR ID - Usuario no existe lanza RuntimeException")
    void obtenerPorId_UsuarioNoExiste_LanzaRuntimeException() {
        // ARRANGE
        when(usuarioDAO.findById(USUARIO_ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.obtenerPorId(USUARIO_ID_INEXISTENTE))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado con ID");

        verify(usuarioDAO, times(1)).findById(USUARIO_ID_INEXISTENTE);
    }

    @Test
    @DisplayName("OBTENER POR EMAIL - Email válido retorna Optional")
    void obtenerPorEmail_EmailValido_RetornaOptional() {
        // ARRANGE
        when(usuarioDAO.findByEmail(EMAIL_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        Optional<UsuarioDTO> resultado = usuarioService.obtenerPorEmail(EMAIL_VALIDO);

        // ASSERT
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getEmail()).isEqualTo(EMAIL_VALIDO);
        verify(usuarioDAO, times(1)).findByEmail(EMAIL_VALIDO);
    }

    // ==================== ACTUALIZAR PERFIL TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR PERFIL - Datos válidos actualiza usuario")
    void actualizarPerfil_DatosValidos_ActualizaUsuario() {
        // ARRANGE
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));
        when(usuarioDAO.actualizarPerfil(eq(USUARIO_ID_VALIDO), any(ActualizarPerfilRequest.class)))
                .thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = usuarioService.actualizarPerfil(USUARIO_ID_VALIDO, actualizarRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).actualizarPerfil(eq(USUARIO_ID_VALIDO), any(ActualizarPerfilRequest.class));
    }

    @Test
    @DisplayName("ACTUALIZAR PERFIL - Nombre inválido lanza IllegalArgumentException")
    void actualizarPerfil_NombreInvalido_LanzaIllegalArgumentException() {
        // ARRANGE
        actualizarRequestValido.setNombre("Ab"); // Nombre muy corto

        // Simular que el usuario existe
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.actualizarPerfil(USUARIO_ID_VALIDO, actualizarRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre debe tener entre 3 y 100 caracteres");

        verify(usuarioDAO, never()).actualizarPerfil(anyInt(), any(ActualizarPerfilRequest.class));
    }


    // ==================== CAMBIAR PASSWORD TESTS ====================

    @Test
    @DisplayName("CAMBIAR PASSWORD - Contraseña actual válida actualiza password")
    void cambiarPassword_ContrasenaActualValida_ActualizaPassword() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches(PASSWORD_VALIDA, PASSWORD_HASH)).thenReturn(true);
        when(passwordEncoder.encode(PASSWORD_VALIDA)).thenReturn(PASSWORD_HASH);
        when(usuarioDAO.actualizarPassword(USUARIO_ID_VALIDO, PASSWORD_HASH)).thenReturn(true);

        // ACT & ASSERT
        assertThatCode(() ->
                usuarioService.cambiarPassword(USUARIO_ID_VALIDO, PASSWORD_VALIDA, PASSWORD_VALIDA, false)
        ).doesNotThrowAnyException();

        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(passwordEncoder, times(1)).matches(PASSWORD_VALIDA, PASSWORD_HASH);
        verify(usuarioDAO, times(1)).actualizarPassword(USUARIO_ID_VALIDO, PASSWORD_HASH);
    }

    @Test
    @DisplayName("CAMBIAR PASSWORD - Contraseña actual inválida lanza IllegalArgumentException")
    void cambiarPassword_ContrasenaActualInvalida_LanzaIllegalArgumentException() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches(PASSWORD_INVALIDA, PASSWORD_HASH)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() ->
                usuarioService.cambiarPassword(USUARIO_ID_VALIDO, PASSWORD_INVALIDA, PASSWORD_VALIDA, false)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La contraseña actual no es válida");

        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(passwordEncoder, times(1)).matches(PASSWORD_INVALIDA, PASSWORD_HASH);
        verify(usuarioDAO, never()).actualizarPassword(anyInt(), anyString());
    }

    @Test
    @DisplayName("CAMBIAR PASSWORD - Admin override ignora contraseña actual")
    void cambiarPassword_AdminOverride_IgnoraContrasenaActual() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.encode(PASSWORD_VALIDA)).thenReturn(PASSWORD_HASH);
        when(usuarioDAO.actualizarPassword(USUARIO_ID_VALIDO, PASSWORD_HASH)).thenReturn(true);

        // ACT & ASSERT
        assertThatCode(() ->
                usuarioService.cambiarPassword(USUARIO_ID_VALIDO, null, PASSWORD_VALIDA, true)
        ).doesNotThrowAnyException();

        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(usuarioDAO, times(1)).actualizarPassword(USUARIO_ID_VALIDO, PASSWORD_HASH);
    }

    // ==================== ACTIVAR/DESACTIVAR TESTS ====================

    @Test
    @DisplayName("DESACTIVAR - Usuario existe lo desactiva correctamente")
    void desactivar_UsuarioExiste_LoDesactivaCorrectamente() {
        // ARRANGE
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));
        when(usuarioDAO.desactivar(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT
        usuarioService.desactivar(USUARIO_ID_VALIDO);

        // ASSERT
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).desactivar(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ACTIVAR - Usuario inactivo lo activa correctamente")
    void activar_UsuarioInactivo_LoActivaCorrectamente() {
        // ARRANGE
        usuarioMock.setEstado(EstadoUsuario.INACTIVO);
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(usuarioDAO.activar(USUARIO_ID_VALIDO)).thenReturn(true); // ← Asegurar que devuelve true

        // ACT
        usuarioService.activar(USUARIO_ID_VALIDO);

        // ASSERT
        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).activar(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ACTIVAR - Fallo en activación lanza RuntimeException")
    void activar_FalloEnActivacion_LanzaRuntimeException() {
        // ARRANGE
        usuarioMock.setEstado(EstadoUsuario.INACTIVO);
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(usuarioDAO.activar(USUARIO_ID_VALIDO)).thenReturn(false); // ← Simular fallo

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.activar(USUARIO_ID_VALIDO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Implementa usuarioDAO.activar(id)");

        verify(usuarioDAO, times(1)).activar(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ACTIVAR - Usuario ya activo no hace nada")
    void activar_UsuarioYaActivo_NoHaceNada() {
        // ARRANGE
        usuarioMock.setEstado(EstadoUsuario.ACTIVO);
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));

        // ACT
        usuarioService.activar(USUARIO_ID_VALIDO);

        // ASSERT
        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, never()).activar(anyInt());
    }

    // ==================== PROMOVER/QUITAR ADMIN TESTS ====================

    @Test
    @DisplayName("PROMOVER A ADMIN - Usuario existe y no es admin lo promueve")
    void promoverAAdmin_UsuarioExisteNoEsAdmin_LoPromueve() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(usuarioDAO.existeAdminPorUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);
        when(usuarioDAO.crearAdmin(USUARIO_ID_VALIDO)).thenReturn(true);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = usuarioService.promoverAAdmin(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).existeAdminPorUsuarioId(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).crearAdmin(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("PROMOVER A ADMIN - Usuario ya es admin retorna DTO sin cambios")
    void promoverAAdmin_UsuarioYaEsAdmin_RetornaDTOSinCambios() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(usuarioDAO.existeAdminPorUsuarioId(USUARIO_ID_VALIDO)).thenReturn(true);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = usuarioService.promoverAAdmin(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioDAO, times(1)).existeAdminPorUsuarioId(USUARIO_ID_VALIDO);
        verify(usuarioDAO, never()).crearAdmin(anyInt());
    }

    @Test
    @DisplayName("QUITAR ADMIN - Usuario es admin lo quita correctamente")
    void quitarAdmin_UsuarioEsAdmin_LoQuitaCorrectamente() {
        // ARRANGE
        when(usuarioDAO.eliminarAdmin(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT
        usuarioService.quitarAdmin(USUARIO_ID_VALIDO);

        // ASSERT
        verify(usuarioDAO, times(1)).eliminarAdmin(USUARIO_ID_VALIDO);
    }

    // ==================== REGISTRAR/QUITAR ANFITRION TESTS ====================

    @Test
    @DisplayName("REGISTRAR COMO ANFITRION - Usuario existe y no es anfitrión lo registra")
    void registrarComoAnfitrion_UsuarioExisteNoEsAnfitrion_LoRegistra() {
        // ARRANGE
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(usuarioDAO.existeAnfitrionPorUsuarioId(USUARIO_ID_VALIDO)).thenReturn(false);
        when(usuarioDAO.crearAnfitrion(eq(USUARIO_ID_VALIDO), anyString(), anyString(), any()))
                .thenReturn(true);
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));

        // ACT
        UsuarioDTO resultado = usuarioService.registrarComoAnfitrion(USUARIO_ID_VALIDO, anfitrionRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioDAO, times(1)).findEntityById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).existeAnfitrionPorUsuarioId(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).crearAnfitrion(eq(USUARIO_ID_VALIDO), anyString(), anyString(), any());
    }

    @Test
    @DisplayName("REGISTRAR COMO ANFITRION - Descripción muy larga lanza excepción")
    void registrarComoAnfitrion_DescripcionMuyLarga_LanzaExcepcion() {
        // ARRANGE
        String descripcionLarga = "a".repeat(501);
        anfitrionRequestValido.setDescripcionPersonal(descripcionLarga);

        // Simular que el usuario existe
        when(usuarioDAO.findEntityById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));

        // ACT & ASSERT
        assertThatThrownBy(() ->
                usuarioService.registrarComoAnfitrion(USUARIO_ID_VALIDO, anfitrionRequestValido)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción personal no puede exceder 500 caracteres");

        verify(usuarioDAO, never()).crearAnfitrion(anyInt(), anyString(), anyString(), any());
    }


    @Test
    @DisplayName("QUITAR ANFITRION - Usuario es anfitrión lo quita correctamente")
    void quitarAnfitrion_UsuarioEsAnfitrion_LoQuitaCorrectamente() {
        // ARRANGE
        when(usuarioDAO.eliminarAnfitrion(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT
        usuarioService.quitarAnfitrion(USUARIO_ID_VALIDO);

        // ASSERT
        verify(usuarioDAO, times(1)).eliminarAnfitrion(USUARIO_ID_VALIDO);
    }

    // ==================== LISTAR TESTS ====================

    @Test
    @DisplayName("LISTAR - Retorna lista de usuarios")
    void listar_RetornaListaUsuarios() {
        // ARRANGE
        List<UsuarioDTO> usuariosMock = List.of(usuarioDTOMock);
        when(usuarioDAO.findAll()).thenReturn(usuariosMock);

        // ACT
        List<UsuarioDTO> resultado = usuarioService.listar();

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(usuarioDAO, times(1)).findAll();
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Llama a desactivar (soft delete)")
    void delete_LlamaADesactivar() {
        // ARRANGE
        when(usuarioDAO.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioDTOMock));
        when(usuarioDAO.desactivar(USUARIO_ID_VALIDO)).thenReturn(true);

        // ACT
        usuarioService.delete(USUARIO_ID_VALIDO);

        // ASSERT
        verify(usuarioDAO, times(1)).findById(USUARIO_ID_VALIDO);
        verify(usuarioDAO, times(1)).desactivar(USUARIO_ID_VALIDO);
    }

    // ==================== VALIDACIONES EDAD TESTS ====================

    @Test
    @DisplayName("VALIDACION EDAD - 18 años exactos es válido")
    void validacionEdad_18AniosExactos_EsValido() {
        // ARRANGE
        LocalDate fechaNacimiento = LocalDate.now().minusYears(18);
        registroRequestValido.setFechaNacimiento(fechaNacimiento);
        when(usuarioDAO.existsByEmail(EMAIL_VALIDO)).thenReturn(false);
        when(passwordEncoder.encode(PASSWORD_VALIDA)).thenReturn(PASSWORD_HASH);
        when(usuarioDAO.save(any(RegistroUsuarioRequest.class), eq(PASSWORD_HASH)))
                .thenReturn(usuarioDTOMock);

        // ACT & ASSERT - No debe lanzar excepción
        assertThatCode(() -> usuarioService.registrar(registroRequestValido))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("VALIDACION EDAD - 17 años y 364 días es inválido")
    void validacionEdad_17Anios364Dias_EsInvalido() {
        // ARRANGE
        LocalDate fechaNacimiento = LocalDate.now().minusYears(17).minusDays(364);
        registroRequestValido.setFechaNacimiento(fechaNacimiento);

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.registrar(registroRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario debe ser mayor de 18 años");
    }

    @Test
    @DisplayName("ACTUALIZAR FOTO PERFIL - Datos válidos actualiza foto")
    void actualizarFotoPerfil_DatosValidos_ActualizaFoto() {
        // ARRANGE
        String nuevaUrl = "https://example.com/foto.jpg";
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        // ACT
        String resultado = usuarioService.actualizarFotoPerfil(USUARIO_ID_VALIDO, nuevaUrl);

        // ASSERT
        assertThat(resultado).isEqualTo(nuevaUrl);
        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(usuarioRepository, times(1)).save(usuarioMock);
    }

    @Test
    @DisplayName("ACTUALIZAR FOTO PERFIL - Usuario no existe lanza excepción")
    void actualizarFotoPerfil_UsuarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        String nuevaUrl = "https://example.com/foto.jpg";
        when(usuarioRepository.findById(USUARIO_ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() ->
                usuarioService.actualizarFotoPerfil(USUARIO_ID_INEXISTENTE, nuevaUrl))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Usuario no encontrado");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("ACTUALIZAR FOTO PERFIL - URL vacía lanza IllegalArgumentException")
    void actualizarFotoPerfil_UrlVacia_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() ->
                usuarioService.actualizarFotoPerfil(USUARIO_ID_VALIDO, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La URL de la foto es obligatoria");
    }

    @Test
    @DisplayName("REGISTRAR - Email con formato inválido lanza excepción")
    void registrar_EmailFormatoInvalido_LanzaExcepcion() {
        // ARRANGE
        registroRequestValido.setEmail("email-invalido");

        // ACT & ASSERT
        assertThatThrownBy(() -> usuarioService.registrar(registroRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El email no tiene un formato válido");
    }
}