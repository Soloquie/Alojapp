package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.negocio.Service.FavoritoService;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para FavoritoController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FavoritoController - Unit Tests")
public class FavoritoControllerTest {

    @Mock
    private FavoritoService favoritoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private FavoritoController favoritoController;

    private FavoritoDTO favoritoDTO;
    private Usuario usuarioEntity;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes - FavoritoDTO no tiene @Builder
        favoritoDTO = new FavoritoDTO();
        favoritoDTO.setId(1);
        favoritoDTO.setUsuarioId(100L); // Long según el DTO real
        favoritoDTO.setAlojamientoId(200L); // Long según el DTO real
        favoritoDTO.setNombreAlojamiento("Cabaña en el bosque");

        usuarioEntity = new Usuario();
        usuarioEntity.setId(100);
        usuarioEntity.setEmail("usuario@test.com");
        usuarioEntity.setNombre("Juan Pérez");
    }

    // =========================================================================
    // TESTS PARA AGREGAR FAVORITO
    // =========================================================================

    @Test
    @DisplayName("Agregar favorito - Debería retornar 201 CREATED")
    void agregar_DeberiaRetornar201Created() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));
            when(favoritoService.agregar(100, 200)).thenReturn(favoritoDTO);

            // Act
            ResponseEntity<FavoritoDTO> response = favoritoController.agregar(authentication, 200);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isEqualTo(favoritoDTO);
            assertThat(response.getBody().getId()).isEqualTo(1);
            assertThat(response.getBody().getAlojamientoId()).isEqualTo(200L); // Long

            verify(favoritoService, times(1)).agregar(100, 200);
        }
    }

    @Test
    @DisplayName("Agregar favorito - Usuario no encontrado debería lanzar excepción")
    void agregar_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("noexiste@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("noexiste@test.com")).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> favoritoController.agregar(authentication, 200))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado por email");

            verify(favoritoService, never()).agregar(anyInt(), anyInt());
        }
    }

    // =========================================================================
    // TESTS PARA ELIMINAR FAVORITO
    // =========================================================================

    @Test
    @DisplayName("Eliminar favorito - Debería retornar 204 NO_CONTENT")
    void eliminar_DeberiaRetornar204NoContent() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));
            doNothing().when(favoritoService).eliminar(100, 200);

            // Act
            ResponseEntity<Void> response = favoritoController.eliminar(authentication, 200);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
            assertThat(response.getBody()).isNull();

            verify(favoritoService, times(1)).eliminar(100, 200);
        }
    }

    // =========================================================================
    // TESTS PARA MIS FAVORITOS
    // =========================================================================

    @Test
    @DisplayName("Mis favoritos - Debería retornar 200 OK con lista")
    void misFavoritos_DeberiaRetornar200OkConLista() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));

            List<FavoritoDTO> favoritos = Arrays.asList(favoritoDTO);
            when(favoritoService.listarPorUsuario(100)).thenReturn(favoritos);

            // Act
            ResponseEntity<List<FavoritoDTO>> response = favoritoController.misFavoritos(authentication);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(favoritos);
            assertThat(response.getBody()).hasSize(1);
            assertThat(response.getBody().get(0).getUsuarioId()).isEqualTo(100L); // Long

            verify(favoritoService, times(1)).listarPorUsuario(100);
        }
    }

    // =========================================================================
    // TESTS PARA ES FAVORITO
    // =========================================================================

    @Test
    @DisplayName("Es favorito - Debería retornar 200 OK con true")
    void esFavorito_DeberiaRetornar200OkConTrue() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));
            when(favoritoService.esFavorito(100, 200)).thenReturn(true);

            // Act
            ResponseEntity<Map<String, Object>> response = favoritoController.esFavorito(authentication, 200);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).containsKeys("alojamientoId", "favorito");
            assertThat(response.getBody().get("alojamientoId")).isEqualTo(200);
            assertThat(response.getBody().get("favorito")).isEqualTo(true);

            verify(favoritoService, times(1)).esFavorito(100, 200);
        }
    }

    @Test
    @DisplayName("Es favorito - Debería retornar 200 OK con false")
    void esFavorito_DeberiaRetornar200OkConFalse() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));
            when(favoritoService.esFavorito(100, 200)).thenReturn(false);

            // Act
            ResponseEntity<Map<String, Object>> response = favoritoController.esFavorito(authentication, 200);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().get("favorito")).isEqualTo(false);

            verify(favoritoService, times(1)).esFavorito(100, 200);
        }
    }

    // =========================================================================
    // TESTS PARA CONTAR FAVORITOS
    // =========================================================================

    @Test
    @DisplayName("Contar favoritos - Debería retornar 200 OK con count")
    void contar_DeberiaRetornar200OkConCount() {
        // Arrange
        Long total = 15L;
        when(favoritoService.contarFavoritosDeAlojamiento(200)).thenReturn(total);

        // Act
        ResponseEntity<Map<String, Object>> response = favoritoController.contar(200);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKeys("alojamientoId", "total");
        assertThat(response.getBody().get("alojamientoId")).isEqualTo(200);
        assertThat(response.getBody().get("total")).isEqualTo(15L);

        verify(favoritoService, times(1)).contarFavoritosDeAlojamiento(200);
    }

    // =========================================================================
    // TESTS PARA TOP FAVORITOS
    // =========================================================================

    @Test
    @DisplayName("Top favoritos - Con límite debería retornar 200 OK")
    void top_ConLimite_DeberiaRetornar200Ok() {
        // Arrange
        List<FavoritoService.MasFavoritosItem> topItems = Arrays.asList(
                new FavoritoService.MasFavoritosItem(1, 25L),
                new FavoritoService.MasFavoritosItem(2, 20L)
        );
        when(favoritoService.topMasFavoritos(5)).thenReturn(topItems);

        // Act
        ResponseEntity<List<FavoritoService.MasFavoritosItem>> response = favoritoController.top(5);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(topItems);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).alojamientoId()).isEqualTo(1);
        assertThat(response.getBody().get(0).total()).isEqualTo(25L); // Cambiado a total()

        verify(favoritoService, times(1)).topMasFavoritos(5);
    }

    @Test
    @DisplayName("Top favoritos - Sin límite debería usar valor por defecto")
    void top_SinLimite_DeberiaUsarValorPorDefecto() {
        // Arrange
        List<FavoritoService.MasFavoritosItem> topItems = Arrays.asList(
                new FavoritoService.MasFavoritosItem(1, 25L)
        );
        when(favoritoService.topMasFavoritos(10)).thenReturn(topItems);

        // Act
        ResponseEntity<List<FavoritoService.MasFavoritosItem>> response = favoritoController.top(null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(topItems);

        verify(favoritoService, times(1)).topMasFavoritos(10);
    }

    // =========================================================================
    // TESTS PARA PING
    // =========================================================================

    @Test
    @DisplayName("Ping - Debería retornar pong")
    void ping_DeberiaRetornarPong() {
        // Act
        String result = favoritoController.ping();

        // Assert
        assertThat(result).isEqualTo("pong");
    }

    // =========================================================================
    // TESTS PARA MANEJO DE EXCEPCIONES
    // =========================================================================

    @Test
    @DisplayName("Handle NoResourceFoundException - Debería retornar 404")
    void handleNoResource_DeberiaRetornar404() {
        // Arrange
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/api/resource");

        // Act
        ResponseEntity<Map<String, Object>> response = favoritoController.handleNoResource(ex);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).containsKeys("status", "error", "message");
        assertThat(response.getBody().get("status")).isEqualTo(404);
        assertThat(response.getBody().get("error")).isEqualTo("Not Found");
        assertThat(response.getBody().get("message")).isNotNull();
    }

    @Test
    @DisplayName("Handle NoHandlerFoundException - Debería retornar 404")
    void handleNoHandler_DeberiaRetornar404() {
        // Arrange
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/api/invalid", null);

        // Act
        ResponseEntity<Map<String, Object>> response = favoritoController.handleNoHandler(ex);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).containsKeys("status", "error", "message");
        assertThat(response.getBody().get("status")).isEqualTo(404);
        assertThat(response.getBody().get("error")).isEqualTo("Not Found");
        assertThat(response.getBody().get("message")).isEqualTo("/api/invalid not mapped");
    }

    // =========================================================================
    // TESTS PARA SCENARIOS DE AUTENTICACIÓN
    // =========================================================================

    @Test
    @DisplayName("CurrentUserId - Sin autenticación debería lanzar excepción")
    void currentUserId_SinAutenticacion_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> favoritoController.agregar(authentication, 200))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario autenticado no encontrado");

            verify(favoritoService, never()).agregar(anyInt(), anyInt());
        }
    }

    @Test
    @DisplayName("CurrentUserId - Email vacío debería lanzar excepción")
    void currentUserId_EmailVacio_DeberiaLanzarExcepcion() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of(""));

            // Act & Assert
            assertThatThrownBy(() -> favoritoController.agregar(authentication, 200))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario autenticado no encontrado");

            verify(favoritoService, never()).agregar(anyInt(), anyInt());
        }
    }

    // =========================================================================
    // TESTS PARA MÚLTIPLES OPERACIONES
    // =========================================================================

    @Test
    @DisplayName("Múltiples operaciones - Deberían procesarse correctamente")
    void multiplesOperaciones_DeberianProcesarseCorrectamente() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Arrange
            securityUtilsMock.when(SecurityUtils::getEmailActual).thenReturn(Optional.of("usuario@test.com"));
            when(usuarioRepository.findByEmailIgnoreCase("usuario@test.com")).thenReturn(Optional.of(usuarioEntity));

            List<FavoritoDTO> favoritos = Arrays.asList(favoritoDTO);
            when(favoritoService.listarPorUsuario(100)).thenReturn(favoritos);
            when(favoritoService.esFavorito(100, 200)).thenReturn(true);
            when(favoritoService.contarFavoritosDeAlojamiento(200)).thenReturn(10L);

            // Act
            ResponseEntity<List<FavoritoDTO>> misFavoritosResponse = favoritoController.misFavoritos(authentication);
            ResponseEntity<Map<String, Object>> esFavoritoResponse = favoritoController.esFavorito(authentication, 200);
            ResponseEntity<Map<String, Object>> contarResponse = favoritoController.contar(200);

            // Assert
            assertThat(misFavoritosResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(esFavoritoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(contarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(esFavoritoResponse.getBody().get("favorito")).isEqualTo(true);
            assertThat(contarResponse.getBody().get("total")).isEqualTo(10L);

            verify(favoritoService, times(1)).listarPorUsuario(100);
            verify(favoritoService, times(1)).esFavorito(100, 200);
            verify(favoritoService, times(1)).contarFavoritosDeAlojamiento(200);
        }
    }
}