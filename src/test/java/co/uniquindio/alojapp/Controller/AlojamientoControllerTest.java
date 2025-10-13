package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.AlojamientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para AlojamientoController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AlojamientoController - Unit Tests")
public class AlojamientoControllerTest {

    @Mock
    private AlojamientoService alojamientoService;

    @InjectMocks
    private AlojamientoController alojamientoController;

    private AlojamientoDTO alojamientoDTO;
    private CrearAlojamientoRequest crearRequest;
    private ActualizarAlojamientoRequest actualizarRequest;
    private BuscarAlojamientosRequest buscarRequest;
    private PaginacionResponse<AlojamientoDTO> paginacionResponse;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes con los campos correctos
        alojamientoDTO = AlojamientoDTO.builder()
                .id(1)
                .titulo("Casa en la playa")
                .descripcion("Hermosa casa frente al mar")
                .ciudad("Cartagena")
                .direccion("Calle 10 #5-20")
                .latitud(new BigDecimal("10.3910485"))
                .longitud(new BigDecimal("-75.4794257"))
                .precioNoche(new BigDecimal("150000.00"))
                .capacidadMaxima(8)
                .imagenPrincipalUrl("https://ejemplo.com/imagen.jpg")
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .anfitrionId(5L)
                .anfitrionNombre("María López")
                .imagenes(Arrays.asList("img1.jpg", "img2.jpg"))
                .calificacionPromedio(4.7)
                .cantidadComentarios(23)
                .build();

        crearRequest = new CrearAlojamientoRequest();
        crearRequest.setTitulo("Nuevo alojamiento");
        crearRequest.setPrecioNoche(BigDecimal.valueOf(200000));

        actualizarRequest = new ActualizarAlojamientoRequest();
        actualizarRequest.setTitulo("Alojamiento actualizado");
        actualizarRequest.setPrecioNoche(BigDecimal.valueOf(180000));

        buscarRequest = BuscarAlojamientosRequest.builder()
                .ciudad("Cartagena")
                .precioMin(BigDecimal.valueOf(100000))
                .precioMax(BigDecimal.valueOf(300000))
                .build();

        paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(alojamientoDTO));
        paginacionResponse.setPaginaActual(0);
        paginacionResponse.setTamanoPagina(10);
        paginacionResponse.setTotalElementos(1L);
        paginacionResponse.setTotalPaginas(1);
    }

    // =========================================================================
    // TESTS PARA RUTAS DE ANFITRIÓN
    // =========================================================================

    @Test
    @DisplayName("Crear alojamiento - Debería retornar 201 CREATED")
    void crearAlojamiento_DeberiaRetornar201Created() {
        // Arrange
        Integer anfitrionId = 2;
        when(alojamientoService.crear(eq(anfitrionId), any(CrearAlojamientoRequest.class)))
                .thenReturn(alojamientoDTO);

        // Act
        ResponseEntity<AlojamientoDTO> response = alojamientoController.crearAlojamiento(anfitrionId, crearRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(alojamientoDTO);
        assertThat(response.getBody().getTitulo()).isEqualTo("Casa en la playa");
        assertThat(response.getBody().getCiudad()).isEqualTo("Cartagena");
        verify(alojamientoService, times(1)).crear(anfitrionId, crearRequest);
    }



    @Test
    @DisplayName("Listar alojamientos del anfitrión - Debería retornar paginación")
    void listarAlojamientosDelAnfitrion_DeberiaRetornarPaginacion() {
        // Arrange
        Integer anfitrionId = 2;
        int pagina = 0;
        int tamano = 10;
        when(alojamientoService.listarPorAnfitrion(anfitrionId, pagina, tamano)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<AlojamientoDTO>> response =
                alojamientoController.listarAlojamientosDelAnfitrion(anfitrionId, pagina, tamano);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        assertThat(response.getBody().getContenido()).hasSize(1);
        assertThat(response.getBody().getContenido().get(0).getTitulo()).isEqualTo("Casa en la playa");
        verify(alojamientoService, times(1)).listarPorAnfitrion(anfitrionId, pagina, tamano);
    }

    // =========================================================================
    // TESTS PARA RUTAS PÚBLICAS
    // =========================================================================

    @Test
    @DisplayName("Obtener alojamiento por ID - Existente debería retornar 200 OK")
    void obtenerAlojamiento_Existente_DeberiaRetornar200Ok() {
        // Arrange
        Integer alojamientoId = 10;
        when(alojamientoService.obtenerPorId(alojamientoId)).thenReturn(alojamientoDTO);

        // Act
        ResponseEntity<AlojamientoDTO> response = alojamientoController.obtenerAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(alojamientoDTO);
        assertThat(response.getBody().getCalificacionPromedio()).isEqualTo(4.7);
        assertThat(response.getBody().getCantidadComentarios()).isEqualTo(23);
        verify(alojamientoService, times(1)).obtenerPorId(alojamientoId);
    }

    @Test
    @DisplayName("Obtener alojamiento por ID - No existente debería retornar 404 NOT_FOUND")
    void obtenerAlojamiento_NoExistente_DeberiaRetornar404NotFound() {
        // Arrange
        Integer alojamientoId = 999;
        when(alojamientoService.obtenerPorId(alojamientoId)).thenReturn(null);

        // Act
        ResponseEntity<AlojamientoDTO> response = alojamientoController.obtenerAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(alojamientoService, times(1)).obtenerPorId(alojamientoId);
    }

    @Test
    @DisplayName("Listar o buscar - Sin filtros debería listar activos")
    void listarOBuscar_SinFiltros_DeberiaListarActivos() {
        // Arrange
        when(alojamientoService.listarActivos(anyInt(), anyInt())).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<AlojamientoDTO>> response = alojamientoController.listarOBuscar(
                null, null, null, null, null, null, null, "fechaCreacion", "DESC", 0, 10);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        verify(alojamientoService, times(1)).listarActivos(0, 10);
        verify(alojamientoService, never()).buscar(any(BuscarAlojamientosRequest.class));
    }

    @Test
    @DisplayName("Listar o buscar - Con filtro ciudad debería buscar")
    void listarOBuscar_ConFiltroCiudad_DeberiaBuscar() {
        // Arrange
        String ciudad = "Cartagena";
        when(alojamientoService.buscar(any(BuscarAlojamientosRequest.class))).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<AlojamientoDTO>> response = alojamientoController.listarOBuscar(
                ciudad, null, null, null, null, null, null, "precioNoche", "ASC", 0, 10);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        verify(alojamientoService, times(1)).buscar(any(BuscarAlojamientosRequest.class));
        verify(alojamientoService, never()).listarActivos(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Listar o buscar - Con filtro precios debería buscar")
    void listarOBuscar_ConFiltroPrecios_DeberiaBuscar() {
        // Arrange
        BigDecimal precioMin = BigDecimal.valueOf(100000);
        BigDecimal precioMax = BigDecimal.valueOf(300000);
        when(alojamientoService.buscar(any(BuscarAlojamientosRequest.class))).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<AlojamientoDTO>> response = alojamientoController.listarOBuscar(
                null, precioMin, precioMax, null, null, null, null, "precioNoche", "ASC", 0, 10);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        verify(alojamientoService, times(1)).buscar(any(BuscarAlojamientosRequest.class));
    }

    @Test
    @DisplayName("Buscar con body - Debería retornar paginación")
    void buscarConBody_DeberiaRetornarPaginacion() {
        // Arrange
        when(alojamientoService.buscar(buscarRequest)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<AlojamientoDTO>> response =
                alojamientoController.buscar(buscarRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paginacionResponse);
        verify(alojamientoService, times(1)).buscar(buscarRequest);
    }

    @Test
    @DisplayName("Verificar disponibilidad - Debería retornar boolean")
    void disponibilidad_DeberiaRetornarBoolean() {
        // Arrange
        Integer alojamientoId = 10;
        LocalDate checkin = LocalDate.of(2025, 11, 10);
        LocalDate checkout = LocalDate.of(2025, 11, 15);
        when(alojamientoService.estaDisponible(alojamientoId, checkin, checkout)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = alojamientoController.disponibilidad(alojamientoId, checkin, checkout);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
        verify(alojamientoService, times(1)).estaDisponible(alojamientoId, checkin, checkout);
    }

    // =========================================================================
    // TESTS DE CAMPOS ESPECÍFICOS DEL NUEVO DTO
    // =========================================================================

    @Test
    @DisplayName("AlojamientoDTO - Campos completos tienen valores correctos")
    void alojamientoDTO_CamposCompletos_TienenValoresCorrectos() {
        // Assert
        assertThat(alojamientoDTO.getId()).isEqualTo(1);
        assertThat(alojamientoDTO.getTitulo()).isEqualTo("Casa en la playa");
        assertThat(alojamientoDTO.getDescripcion()).isEqualTo("Hermosa casa frente al mar");
        assertThat(alojamientoDTO.getCiudad()).isEqualTo("Cartagena");
        assertThat(alojamientoDTO.getDireccion()).isEqualTo("Calle 10 #5-20");
        assertThat(alojamientoDTO.getLatitud()).isEqualByComparingTo("10.3910485");
        assertThat(alojamientoDTO.getLongitud()).isEqualByComparingTo("-75.4794257");
        assertThat(alojamientoDTO.getPrecioNoche()).isEqualByComparingTo("150000.00");
        assertThat(alojamientoDTO.getCapacidadMaxima()).isEqualTo(8);
        assertThat(alojamientoDTO.getImagenPrincipalUrl()).contains("ejemplo.com");
        assertThat(alojamientoDTO.getEstado()).isEqualTo("ACTIVO");
        assertThat(alojamientoDTO.getAnfitrionId()).isEqualTo(5L);
        assertThat(alojamientoDTO.getAnfitrionNombre()).isEqualTo("María López");
        assertThat(alojamientoDTO.getImagenes()).hasSize(2);
        assertThat(alojamientoDTO.getCalificacionPromedio()).isEqualTo(4.7);
        assertThat(alojamientoDTO.getCantidadComentarios()).isEqualTo(23);
    }

    @Test
    @DisplayName("AlojamientoDTO - Builder funciona correctamente")
    void alojamientoDTO_Builder_FuncionaCorrectamente() {
        // Arrange & Act
        AlojamientoDTO dto = AlojamientoDTO.builder()
                .id(2)
                .titulo("Apartamento en el centro")
                .ciudad("Medellín")
                .precioNoche(new BigDecimal("200000.00"))
                .capacidadMaxima(4)
                .anfitrionId(3L)
                .anfitrionNombre("Carlos Rodríguez")
                .calificacionPromedio(4.5)
                .cantidadComentarios(15)
                .build();

        // Assert
        assertThat(dto.getId()).isEqualTo(2);
        assertThat(dto.getTitulo()).isEqualTo("Apartamento en el centro");
        assertThat(dto.getCiudad()).isEqualTo("Medellín");
        assertThat(dto.getPrecioNoche()).isEqualByComparingTo("200000.00");
        assertThat(dto.getCapacidadMaxima()).isEqualTo(4);
        assertThat(dto.getAnfitrionId()).isEqualTo(3L);
        assertThat(dto.getAnfitrionNombre()).isEqualTo("Carlos Rodríguez");
        assertThat(dto.getCalificacionPromedio()).isEqualTo(4.5);
        assertThat(dto.getCantidadComentarios()).isEqualTo(15);
    }

    @Test
    @DisplayName("Respuesta contiene campos de calificación y comentarios")
    void respuesta_ContieneCamposDeCalificacionYComentarios() {
        // Arrange
        Integer alojamientoId = 10;
        when(alojamientoService.obtenerPorId(alojamientoId)).thenReturn(alojamientoDTO);

        // Act
        ResponseEntity<AlojamientoDTO> response = alojamientoController.obtenerAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getBody().getCalificacionPromedio()).isNotNull();
        assertThat(response.getBody().getCantidadComentarios()).isNotNull();
        assertThat(response.getBody().getCalificacionPromedio()).isEqualTo(4.7);
        assertThat(response.getBody().getCantidadComentarios()).isEqualTo(23);
    }

    @Test
    @DisplayName("Respuesta contiene información del anfitrión")
    void respuesta_ContieneInformacionDelAnfitrion() {
        // Arrange
        Integer alojamientoId = 10;
        when(alojamientoService.obtenerPorId(alojamientoId)).thenReturn(alojamientoDTO);

        // Act
        ResponseEntity<AlojamientoDTO> response = alojamientoController.obtenerAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getBody().getAnfitrionId()).isEqualTo(5L);
        assertThat(response.getBody().getAnfitrionNombre()).isEqualTo("María López");
    }

    // =========================================================================
    // TESTS DE CASOS BORDE
    // =========================================================================

    @Test
    @DisplayName("Disponibilidad - Fechas diferentes")
    void disponibilidad_FechasDiferentes_DeberiaLlamarServicioConFechasCorrectas() {
        // Arrange
        Integer alojamientoId = 10;
        LocalDate checkin = LocalDate.of(2025, 12, 1);
        LocalDate checkout = LocalDate.of(2025, 12, 5);
        when(alojamientoService.estaDisponible(alojamientoId, checkin, checkout)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = alojamientoController.disponibilidad(alojamientoId, checkin, checkout);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isFalse();
        verify(alojamientoService, times(1)).estaDisponible(alojamientoId, checkin, checkout);
    }

    @Test
    @DisplayName("Listar alojamientos anfitrión - Paginación personalizada")
    void listarAlojamientosDelAnfitrion_PaginacionPersonalizada_DeberiaPasarParametrosCorrectos() {
        // Arrange
        Integer anfitrionId = 2;
        int pagina = 1;
        int tamano = 5;
        when(alojamientoService.listarPorAnfitrion(anfitrionId, pagina, tamano)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<PaginacionResponse<AlojamientoDTO>> response =
                alojamientoController.listarAlojamientosDelAnfitrion(anfitrionId, pagina, tamano);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(alojamientoService, times(1)).listarPorAnfitrion(anfitrionId, pagina, tamano);
    }
}