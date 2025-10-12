package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.ServicioAlojamientoDAO;
import co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ServicioAlojamientoServiceIMPL
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ServicioAlojamientoService - Unit Tests")
public class ServicioAlojamientoServiceTest {

    @Mock
    private ServicioAlojamientoDAO servicioDAO;

    @InjectMocks
    private ServicioAlojamientoServiceIMPL servicioService;

    // DATOS DE PRUEBA
    private final Integer SERVICIO_ID_VALIDO = 1;
    private final Integer SERVICIO_ID_INEXISTENTE = 999;
    private final String NOMBRE_VALIDO = "Wi-Fi";
    private final String NOMBRE_INEXISTENTE = "ServicioInexistente";
    private final String DESCRIPCION_VALIDO = "Conexión a internet inalámbrica";
    private final String ICONO_URL_VALIDO = "https://example.com/icon.png";

    private ServicioAlojamientoDTO servicioDTOMock;
    private ServicioAlojamiento servicioEntityMock;

    @BeforeEach
    void setUp() {
        // Configurar entity mock
        servicioEntityMock = ServicioAlojamiento.builder()
                .id(SERVICIO_ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .descripcion(DESCRIPCION_VALIDO)
                .iconoUrl(ICONO_URL_VALIDO)
                .build();

        // Configurar DTO mock
        servicioDTOMock = new ServicioAlojamientoDTO(
                SERVICIO_ID_VALIDO,
                NOMBRE_VALIDO,
                DESCRIPCION_VALIDO
        );
    }

    // ==================== CREAR TESTS ====================

    @Test
    @DisplayName("CREAR - Datos válidos crea servicio exitosamente")
    void crear_DatosValidos_CreaServicioExitosamente() {
        // ARRANGE
        when(servicioDAO.save(eq(NOMBRE_VALIDO), eq(DESCRIPCION_VALIDO), eq(ICONO_URL_VALIDO)))
                .thenReturn(servicioDTOMock);

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.crear(NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(SERVICIO_ID_VALIDO);
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE_VALIDO);
        verify(servicioDAO).save(NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);
    }

    @Test
    @DisplayName("CREAR - Nombre duplicado lanza ReglaNegocioException")
    void crear_NombreDuplicado_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.save(eq(NOMBRE_VALIDO), eq(DESCRIPCION_VALIDO), eq(ICONO_URL_VALIDO)))
                .thenThrow(new RuntimeException("Ya existe un servicio con ese nombre"));

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.crear(NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("Ya existe un servicio con ese nombre");

        verify(servicioDAO).save(NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);
    }

    @Test
    @DisplayName("CREAR - Nombre null lanza ReglaNegocioException")
    void crear_NombreNull_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.save(isNull(), anyString(), anyString()))
                .thenThrow(new RuntimeException("El nombre es requerido"));

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.crear(null, DESCRIPCION_VALIDO, ICONO_URL_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El nombre es requerido");

        verify(servicioDAO).save(null, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);
    }

    // ==================== OBTENER POR ID TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Servicio existe retorna DTO")
    void obtenerPorId_ServicioExiste_RetornaDTO() {
        // ARRANGE
        when(servicioDAO.findById(SERVICIO_ID_VALIDO)).thenReturn(Optional.of(servicioDTOMock));

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.obtenerPorId(SERVICIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(SERVICIO_ID_VALIDO);
        verify(servicioDAO).findById(SERVICIO_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR ID - Servicio no existe lanza RecursoNoEncontradoException")
    void obtenerPorId_ServicioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.findById(SERVICIO_ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.obtenerPorId(SERVICIO_ID_INEXISTENTE))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Servicio no encontrado");

        verify(servicioDAO).findById(SERVICIO_ID_INEXISTENTE);
    }

    // ==================== LISTAR TESTS ====================

    @Test
    @DisplayName("LISTAR - Retorna lista de servicios")
    void listar_RetornaListaServicios() {
        // ARRANGE
        List<ServicioAlojamientoDTO> serviciosMock = Arrays.asList(servicioDTOMock);
        when(servicioDAO.findAll()).thenReturn(serviciosMock);

        // ACT
        List<ServicioAlojamientoDTO> resultado = servicioService.listar();

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(servicioDAO).findAll();
    }

    @Test
    @DisplayName("LISTAR - Lista vacía retorna lista vacía")
    void listar_ListaVacia_RetornaListaVacia() {
        // ARRANGE
        when(servicioDAO.findAll()).thenReturn(Arrays.asList());

        // ACT
        List<ServicioAlojamientoDTO> resultado = servicioService.listar();

        // ASSERT
        assertThat(resultado).isEmpty();
        verify(servicioDAO).findAll();
    }

    // ==================== ACTUALIZAR TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR - Datos válidos actualiza servicio exitosamente")
    void actualizar_DatosValidos_ActualizaServicioExitosamente() {
        // ARRANGE
        String nuevoNombre = "Wi-Fi Premium";
        String nuevaDescripcion = "Conexión a internet de alta velocidad";
        String nuevoIcono = "https://example.com/icon-premium.png";

        ServicioAlojamientoDTO servicioActualizadoMock = new ServicioAlojamientoDTO(
                SERVICIO_ID_VALIDO,
                nuevoNombre,
                nuevaDescripcion
        );

        when(servicioDAO.actualizar(eq(SERVICIO_ID_VALIDO), eq(nuevoNombre), eq(nuevaDescripcion), eq(nuevoIcono)))
                .thenReturn(Optional.of(servicioActualizadoMock));

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.actualizar(SERVICIO_ID_VALIDO, nuevoNombre, nuevaDescripcion, nuevoIcono);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo(nuevoNombre);
        assertThat(resultado.getDescripcion()).isEqualTo(nuevaDescripcion);
        verify(servicioDAO).actualizar(SERVICIO_ID_VALIDO, nuevoNombre, nuevaDescripcion, nuevoIcono);
    }

    @Test
    @DisplayName("ACTUALIZAR - Servicio no existe lanza RecursoNoEncontradoException")
    void actualizar_ServicioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.actualizar(eq(SERVICIO_ID_INEXISTENTE), anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.actualizar(SERVICIO_ID_INEXISTENTE, NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Servicio no encontrado para actualizar");

        verify(servicioDAO).actualizar(SERVICIO_ID_INEXISTENTE, NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);
    }

    @Test
    @DisplayName("ACTUALIZAR - Nombre duplicado lanza RuntimeException")
    void actualizar_NombreDuplicado_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.actualizar(eq(SERVICIO_ID_VALIDO), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Ya existe un servicio con ese nombre"));

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.actualizar(SERVICIO_ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO))
                .isInstanceOf(RuntimeException.class) // Cambiado a RuntimeException
                .hasMessageContaining("Ya existe un servicio con ese nombre");

        verify(servicioDAO).actualizar(SERVICIO_ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);
    }
    // ==================== ELIMINAR TESTS ====================

    @Test
    @DisplayName("ELIMINAR - Servicio existe elimina exitosamente")
    void eliminar_ServicioExiste_EliminaExitosamente() {
        // ARRANGE
        when(servicioDAO.delete(SERVICIO_ID_VALIDO)).thenReturn(true);

        // ACT
        servicioService.eliminar(SERVICIO_ID_VALIDO);

        // ASSERT
        verify(servicioDAO).delete(SERVICIO_ID_VALIDO);
    }

    @Test
    @DisplayName("ELIMINAR - Servicio no existe lanza RecursoNoEncontradoException")
    void eliminar_ServicioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.delete(SERVICIO_ID_INEXISTENTE)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.eliminar(SERVICIO_ID_INEXISTENTE))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Servicio no encontrado para eliminar");

        verify(servicioDAO).delete(SERVICIO_ID_INEXISTENTE);
    }

    // ==================== BUSCAR POR NOMBRE TESTS ====================

    @Test
    @DisplayName("BUSCAR POR NOMBRE - Servicio existe retorna DTO")
    void buscarPorNombre_ServicioExiste_RetornaDTO() {
        // ARRANGE
        when(servicioDAO.findByNombre(NOMBRE_VALIDO)).thenReturn(Optional.of(servicioDTOMock));

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.buscarPorNombre(NOMBRE_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE_VALIDO);
        verify(servicioDAO).findByNombre(NOMBRE_VALIDO);
    }

    @Test
    @DisplayName("BUSCAR POR NOMBRE - Servicio no existe lanza RecursoNoEncontradoException")
    void buscarPorNombre_ServicioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(servicioDAO.findByNombre(NOMBRE_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> servicioService.buscarPorNombre(NOMBRE_INEXISTENTE))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Servicio no encontrado con nombre: " + NOMBRE_INEXISTENTE);

        verify(servicioDAO).findByNombre(NOMBRE_INEXISTENTE);
    }

    // ==================== SERVICIOS MÁS UTILIZADOS TESTS ====================

    @Test
    @DisplayName("SERVICIOS MÁS UTILIZADOS - Retorna lista de servicios con conteo")
    void serviciosMasUtilizados_RetornaListaConConteo() {
        // ARRANGE
        Object[] filaMock = new Object[]{servicioEntityMock, 5L};
        // Corrección: Especificar explícitamente el tipo como List<Object[]>
        List<Object[]> filasMock = Arrays.<Object[]>asList(filaMock);

        when(servicioDAO.findServiciosMasUtilizados()).thenReturn(filasMock);

        // ACT
        List<ServicioAlojamientoServiceIMPL.ServicioUsoDTO> resultado = servicioService.serviciosMasUtilizados();

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);

        ServicioAlojamientoServiceIMPL.ServicioUsoDTO servicioUso = resultado.get(0);
        assertThat(servicioUso.getServicio().getId()).isEqualTo(SERVICIO_ID_VALIDO);
        assertThat(servicioUso.getTotalAlojamientos()).isEqualTo(5L);

        verify(servicioDAO).findServiciosMasUtilizados();
    }

    @Test
    @DisplayName("SERVICIOS MÁS UTILIZADOS - Lista vacía retorna lista vacía")
    void serviciosMasUtilizados_ListaVacia_RetornaListaVacia() {
        // ARRANGE
        when(servicioDAO.findServiciosMasUtilizados()).thenReturn(Arrays.asList());

        // ACT
        List<ServicioAlojamientoServiceIMPL.ServicioUsoDTO> resultado = servicioService.serviciosMasUtilizados();

        // ASSERT
        assertThat(resultado).isEmpty();
        verify(servicioDAO).findServiciosMasUtilizados();
    }

    // ==================== CONTAR ALOJAMIENTOS POR SERVICIO TESTS ====================

    @Test
    @DisplayName("CONTAR ALOJAMIENTOS POR SERVICIO - Servicio existe retorna conteo")
    void contarAlojamientosPorServicio_ServicioExiste_RetornaConteo() {
        // ARRANGE
        Long conteoEsperado = 10L;
        when(servicioDAO.countAlojamientosByServicio(SERVICIO_ID_VALIDO)).thenReturn(conteoEsperado);

        // ACT
        Long resultado = servicioService.contarAlojamientosPorServicio(SERVICIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualTo(conteoEsperado);
        verify(servicioDAO).countAlojamientosByServicio(SERVICIO_ID_VALIDO);
    }

    @Test
    @DisplayName("CONTAR ALOJAMIENTOS POR SERVICIO - Servicio sin alojamientos retorna cero")
    void contarAlojamientosPorServicio_ServicioSinAlojamientos_RetornaCero() {
        // ARRANGE
        when(servicioDAO.countAlojamientosByServicio(SERVICIO_ID_VALIDO)).thenReturn(0L);

        // ACT
        Long resultado = servicioService.contarAlojamientosPorServicio(SERVICIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isZero();
        verify(servicioDAO).countAlojamientosByServicio(SERVICIO_ID_VALIDO);
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CREAR - Descripción null se procesa correctamente")
    void crear_DescripcionNull_SeProcesaCorrectamente() {
        // ARRANGE
        ServicioAlojamientoDTO servicioSinDescripcionMock = new ServicioAlojamientoDTO(
                SERVICIO_ID_VALIDO,
                NOMBRE_VALIDO,
                null
        );

        when(servicioDAO.save(eq(NOMBRE_VALIDO), isNull(), eq(ICONO_URL_VALIDO)))
                .thenReturn(servicioSinDescripcionMock);

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.crear(NOMBRE_VALIDO, null, ICONO_URL_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isNull();
        verify(servicioDAO).save(NOMBRE_VALIDO, null, ICONO_URL_VALIDO);
    }

    @Test
    @DisplayName("ACTUALIZAR - Solo actualiza nombre mantiene otros valores")
    void actualizar_SoloNombre_ActualizaNombreMantieneOtrosValores() {
        // ARRANGE
        String nuevoNombre = "Nuevo Nombre";

        ServicioAlojamientoDTO servicioActualizadoMock = new ServicioAlojamientoDTO(
                SERVICIO_ID_VALIDO,
                nuevoNombre,
                DESCRIPCION_VALIDO
        );

        when(servicioDAO.actualizar(eq(SERVICIO_ID_VALIDO), eq(nuevoNombre), eq(DESCRIPCION_VALIDO), eq(ICONO_URL_VALIDO)))
                .thenReturn(Optional.of(servicioActualizadoMock));

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.actualizar(SERVICIO_ID_VALIDO, nuevoNombre, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo(nuevoNombre);
        assertThat(resultado.getDescripcion()).isEqualTo(DESCRIPCION_VALIDO);
        verify(servicioDAO).actualizar(SERVICIO_ID_VALIDO, nuevoNombre, DESCRIPCION_VALIDO, ICONO_URL_VALIDO);
    }

    @Test
    @DisplayName("BUSCAR POR NOMBRE - Nombre case insensitive funciona correctamente")
    void buscarPorNombre_NombreCaseInsensitive_FuncionaCorrectamente() {
        // ARRANGE
        String nombreMayusculas = "WI-FI";
        when(servicioDAO.findByNombre(nombreMayusculas)).thenReturn(Optional.of(servicioDTOMock));

        // ACT
        ServicioAlojamientoDTO resultado = servicioService.buscarPorNombre(nombreMayusculas);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(servicioDAO).findByNombre(nombreMayusculas);
    }
}