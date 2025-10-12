package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarPerfilRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CambiarPasswordRequest;
import co.uniquindio.alojapp.negocio.Service.FotoPerfilService;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.seguridad.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para UsuarioController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioController - Unit Tests")
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FotoPerfilService fotoPerfilService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDTO usuarioDTO;
    private Usuario usuarioEntity;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1);
        usuarioDTO.setEmail("usuario@test.com");
        usuarioDTO.setNombre("Juan Pérez");
        usuarioDTO.setTelefono("123456789");

        usuarioEntity = new Usuario();
        usuarioEntity.setId(1);
        usuarioEntity.setEmail("usuario@test.com");
        usuarioEntity.setNombre("Juan Pérez");
    }

    // =========================================================================
    // TESTS PARA OBTENER MI PERFIL
    // =========================================================================

    @Test
    @DisplayName("Obtener mi perfil - Debería retornar 200 OK con datos del usuario")
    void miPerfil_DeberiaRetornar200OkConDatosUsuario() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioService.obtenerPorEmail("usuario@test.com")).thenReturn(Optional.of(usuarioDTO));

            // Act
            ResponseEntity<UsuarioDTO> response = usuarioController.miPerfil();

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(usuarioDTO);
            assertThat(response.getBody().getEmail()).isEqualTo("usuario@test.com");
            assertThat(response.getBody().getNombre()).isEqualTo("Juan Pérez");

            verify(usuarioService, times(1)).obtenerPorEmail("usuario@test.com");
        }
    }

    @Test
    @DisplayName("Obtener mi perfil - Usuario no encontrado debería lanzar excepción")
    void miPerfil_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("noexiste@test.com"));
            when(usuarioService.obtenerPorEmail("noexiste@test.com")).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.miPerfil())
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado");

            verify(usuarioService, times(1)).obtenerPorEmail("noexiste@test.com");
        }
    }

    // =========================================================================
    // TESTS PARA ACTUALIZAR PERFIL
    // =========================================================================

    @Test
    @DisplayName("Actualizar perfil - Debería retornar 200 OK con usuario actualizado")
    void actualizarPerfil_DeberiaRetornar200OkConUsuarioActualizado() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));

            ActualizarPerfilRequest request = new ActualizarPerfilRequest();
            request.setNombre("Juan Carlos Pérez");
            request.setTelefono("987654321");

            UsuarioDTO usuarioActualizado = new UsuarioDTO();
            usuarioActualizado.setId(1);
            usuarioActualizado.setNombre("Juan Carlos Pérez");
            usuarioActualizado.setTelefono("987654321");

            when(usuarioService.actualizarPerfil(1, request)).thenReturn(usuarioActualizado);

            // Act
            ResponseEntity<UsuarioDTO> response = usuarioController.actualizarPerfil(request);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(usuarioActualizado);
            assertThat(response.getBody().getNombre()).isEqualTo("Juan Carlos Pérez");
            assertThat(response.getBody().getTelefono()).isEqualTo("987654321");

            verify(usuarioService, times(1)).actualizarPerfil(1, request);
        }
    }

    @Test
    @DisplayName("Actualizar perfil - Usuario no encontrado debería lanzar excepción")
    void actualizarPerfil_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("noexiste@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("noexiste@test.com")).thenReturn(Optional.empty());

            ActualizarPerfilRequest request = new ActualizarPerfilRequest();
            request.setNombre("Nuevo Nombre");

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.actualizarPerfil(request))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado por email");

            verify(usuarioService, never()).actualizarPerfil(anyInt(), any());
        }
    }

    // =========================================================================
    // TESTS PARA CAMBIAR PASSWORD
    // =========================================================================

    @Test
    @DisplayName("Cambiar password - Debería retornar 204 NO_CONTENT")
    void cambiarPassword_DeberiaRetornar204NoContent() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));

            CambiarPasswordRequest request = new CambiarPasswordRequest();
            request.setPasswordActual("oldPassword");
            request.setNuevaPassword("newPassword");

            doNothing().when(usuarioService).cambiarPassword(1, "oldPassword", "newPassword", true);

            // Act
            ResponseEntity<Void> response = usuarioController.cambiarPassword(request);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
            assertThat(response.getBody()).isNull();

            verify(usuarioService, times(1)).cambiarPassword(1, "oldPassword", "newPassword", true);
        }
    }

    // =========================================================================
    // TESTS PARA OBTENER PERFIL POR ID
    // =========================================================================

    @Test
    @DisplayName("Obtener perfil por ID - Mismo usuario debería retornar 200 OK")
    void obtenerPorId_MismoUsuario_DeberiaRetornar200Ok() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));
            when(usuarioService.obtenerPorId(1)).thenReturn(usuarioDTO);

            // Act
            ResponseEntity<UsuarioDTO> response = usuarioController.obtenerPorId(1);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(usuarioDTO);

            verify(usuarioService, times(1)).obtenerPorId(1);
        }
    }

    @Test
    @DisplayName("Obtener perfil por ID - Usuario diferente debería lanzar excepción")
    void obtenerPorId_UsuarioDiferente_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));

            Usuario otroUsuario = new Usuario();
            otroUsuario.setId(2); // ID diferente al solicitado
            otroUsuario.setEmail("usuario@test.com");

            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(otroUsuario));

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.obtenerPorId(1))
                    .isInstanceOf(AccesoNoAutorizadoException.class)
                    .hasMessage("No puedes acceder al perfil de otro usuario");

            verify(usuarioService, never()).obtenerPorId(anyInt());
        }
    }

    @Test
    @DisplayName("Obtener perfil por ID - Usuario no encontrado debería lanzar excepción")
    void obtenerPorId_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.obtenerPorId(1))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado por email");

            verify(usuarioService, never()).obtenerPorId(anyInt());
        }
    }

    // =========================================================================
    // TESTS PARA ACTUALIZAR FOTO DE PERFIL
    // =========================================================================

    @Test
    @DisplayName("Actualizar foto perfil - Debería retornar 200 OK con URL")
    void actualizarFotoPerfil_DeberiaRetornar200OkConUrl() throws IOException {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));

            MultipartFile file = mock(MultipartFile.class);
            when(file.isEmpty()).thenReturn(false);

            String secureUrl = "https://storage.ejemplo.com/fotos/perfil-1.jpg";
            when(fotoPerfilService.subirFotoPerfil(1, file)).thenReturn(secureUrl);

            // Act
            ResponseEntity<String> response = usuarioController.actualizarFotoPerfil(file);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(secureUrl);

            verify(fotoPerfilService, times(1)).subirFotoPerfil(1, file);
            verify(usuarioService, times(1)).actualizarFotoPerfil(1, secureUrl);
        }
    }

    @Test
    @DisplayName("Actualizar foto perfil - Archivo vacío debería lanzar excepción")
    void actualizarFotoPerfil_ArchivoVacio_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));

            MultipartFile file = mock(MultipartFile.class);
            when(file.isEmpty()).thenReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.actualizarFotoPerfil(file))
                    .isInstanceOf(org.springframework.web.server.ResponseStatusException.class)
                    .hasMessageContaining("Debes adjuntar un archivo en 'file'.");

            // Verificar que no se llamó a los servicios
            verify(fotoPerfilService, never()).subirFotoPerfil(anyInt(), any());
            verify(usuarioService, never()).actualizarFotoPerfil(anyInt(), anyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Actualizar foto perfil - Usuario no encontrado debería lanzar excepción")
    void actualizarFotoPerfil_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("noexiste@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("noexiste@test.com")).thenReturn(Optional.empty());

            MultipartFile file = mock(MultipartFile.class);
            // No configuramos when(file.isEmpty()) porque la excepción se lanza antes de verificar el archivo

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.actualizarFotoPerfil(file))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado por email");

            verify(fotoPerfilService, never()).subirFotoPerfil(anyInt(), any());
            verify(usuarioService, never()).actualizarFotoPerfil(anyInt(), anyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // =========================================================================
    // TESTS PARA MANEJO DE EXCEPCIONES
    // =========================================================================

    @Test
    @DisplayName("Handle MissingServletRequestPartException - Debería retornar 400 Bad Request")
    void handleMissingPart_DeberiaRetornar400BadRequest() {
        // Arrange
        MissingServletRequestPartException ex = new MissingServletRequestPartException("file");

        // Act
        ResponseEntity<Map<String, Object>> response = usuarioController.handleMissingPart(ex);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsKeys("status", "error", "message");
        assertThat(response.getBody().get("status")).isEqualTo(400);
        assertThat(response.getBody().get("error")).isEqualTo("Bad Request");
        assertThat(response.getBody().get("message")).isEqualTo("Falta la parte 'file'. Envia un form-data con la clave 'file'.");
    }

    // =========================================================================
    // TESTS PARA SCENARIOS DE AUTENTICACIÓN
    // =========================================================================

    @Test
    @DisplayName("Mi perfil - Sin autenticación debería lanzar excepción")
    void miPerfil_SinAutenticacion_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.empty());

            // Configurar SecurityContextHolder para que devuelva null
            SecurityContextHolder.setContext(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);

            // Act & Assert
            assertThatThrownBy(() -> usuarioController.miPerfil())
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado");
        }
    }

    @Test
    @DisplayName("Mi perfil - Con Authentication pero sin SecurityUtils debería funcionar")
    void miPerfil_ConAuthentication_DeberiaFuncionar() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.empty());

            SecurityContextHolder.setContext(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("usuario@test.com");

            when(usuarioService.obtenerPorEmail("usuario@test.com")).thenReturn(Optional.of(usuarioDTO));

            // Act
            ResponseEntity<UsuarioDTO> response = usuarioController.miPerfil();

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(usuarioDTO);
        }
    }
}