package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.negocio.Service.FavoritoService;
import co.uniquindio.alojapp.persistencia.DAO.FavoritoDAO;
import co.uniquindio.alojapp.negocio.excepciones.BadRequestException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para FavoritoServiceIMPL
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FavoritoService - Unit Tests")
public class FavoritoServiceTest {

    @Mock
    private FavoritoDAO favoritoDAO;

    @InjectMocks
    private FavoritoServiceIMPL favoritoService;

    private FavoritoDTO favoritoDTO;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes - usando Long para IDs según el DTO real
        favoritoDTO = new FavoritoDTO();
        favoritoDTO.setId(1);
        favoritoDTO.setUsuarioId(100L);    // Long según el DTO real
        favoritoDTO.setAlojamientoId(200L); // Long según el DTO real
        favoritoDTO.setNombreAlojamiento("Cabaña en el bosque");
    }

    // =========================================================================
    // TESTS PARA AGREGAR FAVORITO
    // =========================================================================

    @Test
    @DisplayName("Agregar favorito - Debería retornar FavoritoDTO")
    void agregar_DeberiaRetornarFavoritoDTO() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = 200;

        when(favoritoDAO.save(userId, alojamientoId)).thenReturn(favoritoDTO);

        // Act
        FavoritoDTO result = favoritoService.agregar(userId, alojamientoId);

        // Assert
        assertThat(result).isEqualTo(favoritoDTO);
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUsuarioId()).isEqualTo(100L); // Long
        assertThat(result.getAlojamientoId()).isEqualTo(200L); // Long
        assertThat(result.getNombreAlojamiento()).isEqualTo("Cabaña en el bosque");

        verify(favoritoDAO, times(1)).save(userId, alojamientoId);
    }

    @Test
    @DisplayName("Agregar favorito - ID de usuario nulo debería lanzar excepción")
    void agregar_UserIdNulo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = null;
        Integer alojamientoId = 200;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.agregar(userId, alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("userId es obligatorio");

        verify(favoritoDAO, never()).save(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Agregar favorito - ID de usuario negativo debería lanzar excepción")
    void agregar_UserIdNegativo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = -1;
        Integer alojamientoId = 200;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.agregar(userId, alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("userId debe ser positivo");

        verify(favoritoDAO, never()).save(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Agregar favorito - ID de alojamiento nulo debería lanzar excepción")
    void agregar_AlojamientoIdNulo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = null;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.agregar(userId, alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("alojamientoId es obligatorio");

        verify(favoritoDAO, never()).save(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Agregar favorito - Excepción del DAO debería convertir a BadRequestException")
    void agregar_ExcepcionDAO_DeberiaConvertirABadRequestException() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = 200;

        when(favoritoDAO.save(userId, alojamientoId))
                .thenThrow(new RuntimeException("El alojamiento ya está en favoritos"));

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.agregar(userId, alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("El alojamiento ya está en favoritos");

        verify(favoritoDAO, times(1)).save(userId, alojamientoId);
    }

    // =========================================================================
    // TESTS PARA ELIMINAR FAVORITO
    // =========================================================================

    @Test
    @DisplayName("Eliminar favorito - Debería eliminar correctamente")
    void eliminar_DeberiaEliminarCorrectamente() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = 200;

        when(favoritoDAO.eliminar(userId, alojamientoId)).thenReturn(true);

        // Act
        favoritoService.eliminar(userId, alojamientoId);

        // Assert
        verify(favoritoDAO, times(1)).eliminar(userId, alojamientoId);
    }

    @Test
    @DisplayName("Eliminar favorito - No encontrado debería lanzar excepción")
    void eliminar_NoEncontrado_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = 200;

        when(favoritoDAO.eliminar(userId, alojamientoId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.eliminar(userId, alojamientoId))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessage("El alojamiento no estaba en tus favoritos");

        verify(favoritoDAO, times(1)).eliminar(userId, alojamientoId);
    }

    @Test
    @DisplayName("Eliminar favorito - ID de usuario nulo debería lanzar excepción")
    void eliminar_UserIdNulo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = null;
        Integer alojamientoId = 200;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.eliminar(userId, alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("userId es obligatorio");

        verify(favoritoDAO, never()).eliminar(anyInt(), anyInt());
    }

    // =========================================================================
    // TESTS PARA LISTAR POR USUARIO
    // =========================================================================

    @Test
    @DisplayName("Listar por usuario - Debería retornar lista de favoritos")
    void listarPorUsuario_DeberiaRetornarLista() {
        // Arrange
        Integer userId = 100;
        List<FavoritoDTO> favoritos = Arrays.asList(favoritoDTO);

        when(favoritoDAO.findByUsuario(userId)).thenReturn(favoritos);

        // Act
        List<FavoritoDTO> result = favoritoService.listarPorUsuario(userId);

        // Assert
        assertThat(result).isEqualTo(favoritos);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsuarioId()).isEqualTo(100L); // Long

        verify(favoritoDAO, times(1)).findByUsuario(userId);
    }

    @Test
    @DisplayName("Listar por usuario - ID nulo debería lanzar excepción")
    void listarPorUsuario_UserIdNulo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = null;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.listarPorUsuario(userId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("userId es obligatorio");

        verify(favoritoDAO, never()).findByUsuario(anyInt());
    }

    // =========================================================================
    // TESTS PARA ES FAVORITO
    // =========================================================================

    @Test
    @DisplayName("Es favorito - Debería retornar true cuando es favorito")
    void esFavorito_DeberiaRetornarTrue() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = 200;

        when(favoritoDAO.esFavorito(userId, alojamientoId)).thenReturn(true);

        // Act
        boolean result = favoritoService.esFavorito(userId, alojamientoId);

        // Assert
        assertThat(result).isTrue();

        verify(favoritoDAO, times(1)).esFavorito(userId, alojamientoId);
    }

    @Test
    @DisplayName("Es favorito - Debería retornar false cuando no es favorito")
    void esFavorito_DeberiaRetornarFalse() {
        // Arrange
        Integer userId = 100;
        Integer alojamientoId = 200;

        when(favoritoDAO.esFavorito(userId, alojamientoId)).thenReturn(false);

        // Act
        boolean result = favoritoService.esFavorito(userId, alojamientoId);

        // Assert
        assertThat(result).isFalse();

        verify(favoritoDAO, times(1)).esFavorito(userId, alojamientoId);
    }

    @Test
    @DisplayName("Es favorito - ID de usuario nulo debería lanzar excepción")
    void esFavorito_UserIdNulo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer userId = null;
        Integer alojamientoId = 200;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.esFavorito(userId, alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("userId es obligatorio");

        verify(favoritoDAO, never()).esFavorito(anyInt(), anyInt());
    }

    // =========================================================================
    // TESTS PARA CONTAR FAVORITOS DE ALOJAMIENTO
    // =========================================================================

    @Test
    @DisplayName("Contar favoritos de alojamiento - Debería retornar count")
    void contarFavoritosDeAlojamiento_DeberiaRetornarCount() {
        // Arrange
        Integer alojamientoId = 200;
        Long count = 5L;

        when(favoritoDAO.countByAlojamiento(alojamientoId)).thenReturn(count);

        // Act
        Long result = favoritoService.contarFavoritosDeAlojamiento(alojamientoId);

        // Assert
        assertThat(result).isEqualTo(5L);

        verify(favoritoDAO, times(1)).countByAlojamiento(alojamientoId);
    }

    @Test
    @DisplayName("Contar favoritos de alojamiento - ID nulo debería lanzar excepción")
    void contarFavoritosDeAlojamiento_IdNulo_DeberiaLanzarExcepcion() {
        // Arrange
        Integer alojamientoId = null;

        // Act & Assert
        assertThatThrownBy(() -> favoritoService.contarFavoritosDeAlojamiento(alojamientoId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("alojamientoId es obligatorio");

        verify(favoritoDAO, never()).countByAlojamiento(anyInt());
    }

    // =========================================================================
    // TESTS PARA TOP MÁS FAVORITOS
    // =========================================================================

    @Test
    @DisplayName("Top más favoritos - Debería retornar lista limitada")
    void topMasFavoritos_DeberiaRetornarListaLimitada() {
        // Arrange
        List<Object[]> filasDAO = Arrays.asList(
                new Object[]{1, 10L},
                new Object[]{2, 8L},
                new Object[]{3, 5L},
                new Object[]{4, 3L}
        );

        when(favoritoDAO.findAlojamientosMasFavoritos()).thenReturn(filasDAO);

        // Act
        List<FavoritoService.MasFavoritosItem> result = favoritoService.topMasFavoritos(2);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).alojamientoId()).isEqualTo(1);
        assertThat(result.get(0).total()).isEqualTo(10L);
        assertThat(result.get(1).alojamientoId()).isEqualTo(2);
        assertThat(result.get(1).total()).isEqualTo(8L);

        verify(favoritoDAO, times(1)).findAlojamientosMasFavoritos();
    }

    @Test
    @DisplayName("Top más favoritos - Límite negativo debería normalizar a 10")
    void topMasFavoritos_LimiteNegativo_DeberiaNormalizarA10() {
        // Arrange
        List<Object[]> filasDAO = Arrays.asList(
                new Object[]{1, 10L},
                new Object[]{2, 8L}
        );

        when(favoritoDAO.findAlojamientosMasFavoritos()).thenReturn(filasDAO);

        // Act
        List<FavoritoService.MasFavoritosItem> result = favoritoService.topMasFavoritos(-5);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).alojamientoId()).isEqualTo(1);
        assertThat(result.get(0).total()).isEqualTo(10L);
        assertThat(result.get(1).alojamientoId()).isEqualTo(2);
        assertThat(result.get(1).total()).isEqualTo(8L);

        verify(favoritoDAO, times(1)).findAlojamientosMasFavoritos();
    }

    @Test
    @DisplayName("Top más favoritos - Límite mayor a 50 debería normalizar a 50")
    void topMasFavoritos_LimiteMayorA50_DeberiaNormalizarA50() {
        // Arrange
        List<Object[]> filasDAO = Arrays.asList(
                new Object[]{1, 10L},
                new Object[]{2, 8L}
        );

        when(favoritoDAO.findAlojamientosMasFavoritos()).thenReturn(filasDAO);

        // Act
        List<FavoritoService.MasFavoritosItem> result = favoritoService.topMasFavoritos(100);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).alojamientoId()).isEqualTo(1);
        assertThat(result.get(0).total()).isEqualTo(10L);
        assertThat(result.get(1).alojamientoId()).isEqualTo(2);
        assertThat(result.get(1).total()).isEqualTo(8L);

        verify(favoritoDAO, times(1)).findAlojamientosMasFavoritos();
    }

    @Test
    @DisplayName("Top más favoritos - Lista vacía debería retornar lista vacía")
    void topMasFavoritos_ListaVacia_DeberiaRetornarListaVacia() {
        // Arrange
        List<Object[]> filasDAO = Arrays.asList();

        when(favoritoDAO.findAlojamientosMasFavoritos()).thenReturn(filasDAO);

        // Act
        List<FavoritoService.MasFavoritosItem> result = favoritoService.topMasFavoritos(10);

        // Assert
        assertThat(result).isEmpty();

        verify(favoritoDAO, times(1)).findAlojamientosMasFavoritos();
    }

    // =========================================================================
    // TESTS PARA NORMALIZAR LÍMITE (pruebas indirectas)
    // =========================================================================

    @Test
    @DisplayName("Normalizar límite - Límite positivo debería mantener el valor")
    void normalizarLimite_Positivo_DeberiaMantenerValor() {
        // Arrange
        List<Object[]> filasDAO = Arrays.asList(
                new Object[]{1, 10L},
                new Object[]{2, 8L},
                new Object[]{3, 5L}
        );

        when(favoritoDAO.findAlojamientosMasFavoritos()).thenReturn(filasDAO);

        // Act
        List<FavoritoService.MasFavoritosItem> result = favoritoService.topMasFavoritos(3);

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result.get(0).alojamientoId()).isEqualTo(1);
        assertThat(result.get(0).total()).isEqualTo(10L);
        assertThat(result.get(1).alojamientoId()).isEqualTo(2);
        assertThat(result.get(1).total()).isEqualTo(8L);
        assertThat(result.get(2).alojamientoId()).isEqualTo(3);
        assertThat(result.get(2).total()).isEqualTo(5L);

        verify(favoritoDAO, times(1)).findAlojamientosMasFavoritos();
    }

    @Test
    @DisplayName("Normalizar límite - Límite cero debería usar 10")
    void normalizarLimite_Cero_DeberiaUsar10() {
        // Arrange
        List<Object[]> filasDAO = Arrays.asList(
                new Object[]{1, 10L},
                new Object[]{2, 8L},
                new Object[]{3, 5L}
        );

        when(favoritoDAO.findAlojamientosMasFavoritos()).thenReturn(filasDAO);

        // Act
        List<FavoritoService.MasFavoritosItem> result = favoritoService.topMasFavoritos(0);

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result.get(0).alojamientoId()).isEqualTo(1);
        assertThat(result.get(0).total()).isEqualTo(10L);
        assertThat(result.get(1).alojamientoId()).isEqualTo(2);
        assertThat(result.get(1).total()).isEqualTo(8L);
        assertThat(result.get(2).alojamientoId()).isEqualTo(3);
        assertThat(result.get(2).total()).isEqualTo(5L);

        verify(favoritoDAO, times(1)).findAlojamientosMasFavoritos();
    }
}