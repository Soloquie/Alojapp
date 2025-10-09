package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.impl.AlojamientoServiceIMPL;
import co.uniquindio.alojapp.persistencia.DAO.AlojamientoDAO;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para AlojamientoServiceIMPL
 *
 * OBJETIVO: Probar la lógica de negocio del servicio de alojamientos de forma aislada
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AlojamientoService - Unit Tests")
public class AlojamientoServiceTest {

    @Mock
    private AlojamientoDAO alojamientoDAO;

    @Mock
    private AnfitrionRepository anfitrionRepository;

    @InjectMocks
    private AlojamientoServiceIMPL alojamientoService;

    // DATOS DE PRUEBA
    private Anfitrion anfitrionMock;
    private AlojamientoDTO alojamientoDTOMock;
    private CrearAlojamientoRequest crearRequestValido;
    private ActualizarAlojamientoRequest actualizarRequestValido;
    private BuscarAlojamientosRequest buscarRequestValido;

    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer ANFITRION_ID_VALIDO = 1;
    private final Integer ALOJAMIENTO_ID_VALIDO = 1;
    private final BigDecimal PRECIO_VALIDO = BigDecimal.valueOf(150000);
    private final BigDecimal LATITUD_VALIDA = BigDecimal.valueOf(4.6097);
    private final BigDecimal LONGITUD_VALIDA = BigDecimal.valueOf(-74.0817);

    @BeforeEach
    void setUp() {
        anfitrionMock = Anfitrion.builder()
                .id(ANFITRION_ID_VALIDO)
                .build();

        alojamientoDTOMock = AlojamientoDTO.builder()
                .id(ALOJAMIENTO_ID_VALIDO)
                .titulo("Casa de playa")
                .direccion("Carrera 10 #20-30")
                .ciudad("Cartagena")
                .precioNoche(PRECIO_VALIDO)
                .capacidadMaxima(4)
                .imagenPrincipalUrl("https://example.com/image.jpg")
                .latitud(LATITUD_VALIDA)
                .longitud(LONGITUD_VALIDA)
                .estado(EstadoAlojamiento.ACTIVO.name())
                .build();

        crearRequestValido = CrearAlojamientoRequest.builder()
                .titulo("Casa de playa")
                .direccion("Carrera 10 #20-30")
                .ciudad("Cartagena")
                .precioNoche(PRECIO_VALIDO)
                .capacidadMaxima(4)
                .imagenPrincipalUrl("https://example.com/image.jpg")
                .latitud(LATITUD_VALIDA)
                .longitud(LONGITUD_VALIDA)
                .build();

        actualizarRequestValido = ActualizarAlojamientoRequest.builder()
                .titulo("Casa remodelada")
                .precioNoche(PRECIO_VALIDO)
                .capacidadMaxima(4)
                .build();

        buscarRequestValido = BuscarAlojamientosRequest.builder()
                .ciudad("Cartagena")
                .pagina(0)
                .tamanoPagina(10)
                .build();
    }


    // ==================== CREAR TESTS ====================

    @Test
    @DisplayName("CREAR - Datos válidos retorna alojamiento creado")
    void crear_DatosValidos_RetornaAlojamientoDTO() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.save(crearRequestValido, ANFITRION_ID_VALIDO)).thenReturn(alojamientoDTOMock);

        // ACT
        AlojamientoDTO resultado = alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(resultado.getTitulo()).isEqualTo("Casa de playa");

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).save(crearRequestValido, ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("CREAR - Usuario no es anfitrión lanza ResponseStatusException")
    void crear_UsuarioNoEsAnfitrion_LanzaExcepcion() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("El usuario autenticado no es anfitrión");

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, never()).save(any(), anyInt());
    }

    @Test
    @DisplayName("CREAR - Título vacío lanza IllegalArgumentException")
    void crear_TituloVacio_LanzaExcepcion() {
        // ARRANGE
        crearRequestValido.setTitulo("");

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título es obligatorio");

        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(alojamientoDAO, never()).save(any(), anyInt());
    }

    @Test
    @DisplayName("CREAR - Precio cero lanza IllegalArgumentException")
    void crear_PrecioCero_LanzaExcepcion() {
        // ARRANGE
        crearRequestValido.setPrecioNoche(BigDecimal.ZERO);

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El precio por noche debe ser mayor a 0");

        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(alojamientoDAO, never()).save(any(), anyInt());
    }

    @Test
    @DisplayName("CREAR - Capacidad cero lanza IllegalArgumentException")
    void crear_CapacidadCero_LanzaExcepcion() {
        // ARRANGE
        crearRequestValido.setCapacidadMaxima(0);

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La capacidad debe ser al menos 1");

        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(alojamientoDAO, never()).save(any(), anyInt());
    }

    @Test
    @DisplayName("CREAR - Latitud inválida lanza IllegalArgumentException")
    void crear_LatitudInvalida_LanzaExcepcion() {
        // ARRANGE
        crearRequestValido.setLatitud(BigDecimal.valueOf(-100)); // Latitud fuera de rango

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Latitud inválida");

        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(alojamientoDAO, never()).save(any(), anyInt());
    }

    // ==================== ACTUALIZAR TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR - Datos válidos retorna alojamiento actualizado")
    void actualizar_DatosValidos_RetornaAlojamientoActualizado() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.actualizar(ALOJAMIENTO_ID_VALIDO, actualizarRequestValido, ANFITRION_ID_VALIDO))
                .thenReturn(Optional.of(alojamientoDTOMock));

        // ACT
        AlojamientoDTO resultado = alojamientoService.actualizar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, actualizarRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).actualizar(ALOJAMIENTO_ID_VALIDO, actualizarRequestValido, ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("ACTUALIZAR - Alojamiento no existe lanza IllegalArgumentException")
    void actualizar_AlojamientoNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.actualizar(ALOJAMIENTO_ID_VALIDO, actualizarRequestValido, ANFITRION_ID_VALIDO))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.actualizar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, actualizarRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No se pudo actualizar");

        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).actualizar(ALOJAMIENTO_ID_VALIDO, actualizarRequestValido, ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("ACTUALIZAR - Precio negativo lanza IllegalArgumentException")
    void actualizar_PrecioNegativo_LanzaExcepcion() {
        // ARRANGE
        actualizarRequestValido.setPrecioNoche(BigDecimal.valueOf(-100));

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.actualizar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO, actualizarRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El precio por noche debe ser mayor a 0");

        verify(anfitrionRepository, never()).findByUsuarioId(anyInt());
        verify(alojamientoDAO, never()).actualizar(anyInt(), any(), anyInt());
    }

    // ==================== ACTIVAR/DESACTIVAR TESTS ====================

    @Test
    @DisplayName("ACTIVAR - Alojamiento existe retorna alojamiento activado")
    void activar_AlojamientoExiste_RetornaAlojamientoActivado() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.actualizar(eq(ALOJAMIENTO_ID_VALIDO), any(ActualizarAlojamientoRequest.class), eq(ANFITRION_ID_VALIDO)))
                .thenReturn(Optional.of(alojamientoDTOMock));

        // ACT
        AlojamientoDTO resultado = alojamientoService.activar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).actualizar(eq(ALOJAMIENTO_ID_VALIDO), any(ActualizarAlojamientoRequest.class), eq(ANFITRION_ID_VALIDO));
    }

    @Test
    @DisplayName("DESACTIVAR - Alojamiento existe retorna alojamiento desactivado")
    void desactivar_AlojamientoExiste_RetornaAlojamientoDesactivado() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.actualizar(eq(ALOJAMIENTO_ID_VALIDO), any(ActualizarAlojamientoRequest.class), eq(ANFITRION_ID_VALIDO)))
                .thenReturn(Optional.of(alojamientoDTOMock));

        // ACT
        AlojamientoDTO resultado = alojamientoService.desactivar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).actualizar(eq(ALOJAMIENTO_ID_VALIDO), any(ActualizarAlojamientoRequest.class), eq(ANFITRION_ID_VALIDO));
    }

    // ==================== ELIMINAR TESTS ====================

    @Test
    @DisplayName("ELIMINAR - Alojamiento existe retorna true")
    void eliminar_AlojamientoExiste_RetornaTrue() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.eliminar(ALOJAMIENTO_ID_VALIDO, ANFITRION_ID_VALIDO)).thenReturn(true);

        // ACT
        boolean resultado = alojamientoService.eliminar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isTrue();
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).eliminar(ALOJAMIENTO_ID_VALIDO, ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("ELIMINAR - Alojamiento no existe retorna false")
    void eliminar_AlojamientoNoExiste_RetornaFalse() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.eliminar(ALOJAMIENTO_ID_VALIDO, ANFITRION_ID_VALIDO)).thenReturn(false);

        // ACT
        boolean resultado = alojamientoService.eliminar(USUARIO_ID_VALIDO, ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isFalse();
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).eliminar(ALOJAMIENTO_ID_VALIDO, ANFITRION_ID_VALIDO);
    }

    // ==================== DISPONIBILIDAD TESTS ====================

    @Test
    @DisplayName("ESTA DISPONIBLE - Fechas válidas retorna disponibilidad")
    void estaDisponible_FechasValidas_RetornaDisponibilidad() {
        // ARRANGE
        LocalDate checkin = LocalDate.now().plusDays(10);
        LocalDate checkout = LocalDate.now().plusDays(15);
        when(alojamientoDAO.verificarDisponibilidad(ALOJAMIENTO_ID_VALIDO, checkin, checkout)).thenReturn(true);

        // ACT
        Boolean resultado = alojamientoService.estaDisponible(ALOJAMIENTO_ID_VALIDO, checkin, checkout);

        // ASSERT
        assertThat(resultado).isTrue();
        verify(alojamientoDAO, times(1)).verificarDisponibilidad(ALOJAMIENTO_ID_VALIDO, checkin, checkout);
    }

    @Test
    @DisplayName("ESTA DISPONIBLE - Checkout antes de checkin lanza IllegalArgumentException")
    void estaDisponible_CheckoutAntesCheckin_LanzaExcepcion() {
        // ARRANGE
        LocalDate checkin = LocalDate.now().plusDays(15);
        LocalDate checkout = LocalDate.now().plusDays(10);

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.estaDisponible(ALOJAMIENTO_ID_VALIDO, checkin, checkout))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rango de fechas inválido");

        verify(alojamientoDAO, never()).verificarDisponibilidad(anyInt(), any(), any());
    }

    @Test
    @DisplayName("ESTA DISPONIBLE - Fechas null lanza IllegalArgumentException")
    void estaDisponible_FechasNull_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.estaDisponible(ALOJAMIENTO_ID_VALIDO, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rango de fechas inválido");

        verify(alojamientoDAO, never()).verificarDisponibilidad(anyInt(), any(), any());
    }

    // ==================== OBTENER TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Alojamiento existe retorna DTO")
    void obtenerPorId_AlojamientoExiste_RetornaDTO() {
        // ARRANGE
        when(alojamientoDAO.findById(ALOJAMIENTO_ID_VALIDO)).thenReturn(Optional.of(alojamientoDTOMock));

        // ACT
        AlojamientoDTO resultado = alojamientoService.obtenerPorId(ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).findById(ALOJAMIENTO_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR ID - Alojamiento no existe lanza IllegalArgumentException")
    void obtenerPorId_AlojamientoNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(alojamientoDAO.findById(ALOJAMIENTO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.obtenerPorId(ALOJAMIENTO_ID_VALIDO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Alojamiento no encontrado");

        verify(alojamientoDAO, times(1)).findById(ALOJAMIENTO_ID_VALIDO);
    }

    // ==================== LISTAR TESTS ====================

    @Test
    @DisplayName("LISTAR POR ANFITRION - Usuario es anfitrión retorna lista")
    void listarPorAnfitrion_UsuarioEsAnfitrion_RetornaLista() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        List<AlojamientoDTO> alojamientos = List.of(alojamientoDTOMock);
        when(alojamientoDAO.findByAnfitrion(ANFITRION_ID_VALIDO)).thenReturn(alojamientos);

        // ACT
        List<AlojamientoDTO> resultado = alojamientoService.listarPorAnfitrion(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(anfitrionRepository, times(1)).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO, times(1)).findByAnfitrion(ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("LISTAR ACTIVOS - Retorna paginación")
    void listarActivos_RetornaPaginacion() {
        PaginacionResponse<AlojamientoDTO> paginacionMock =
                PaginacionResponse.<AlojamientoDTO>builder()
                        .contenido(List.of(alojamientoDTOMock))
                        .paginaActual(0)
                        .tamanoPagina(10)
                        .totalElementos(1L)
                        .totalPaginas(1)
                        .esPrimera(true)
                        .esUltima(true)
                        .tieneSiguiente(false)
                        .tieneAnterior(false)
                        .build();

        when(alojamientoDAO.findActivos(0, 10)).thenReturn(paginacionMock);

        PaginacionResponse<AlojamientoDTO> resultado = alojamientoService.listarActivos(0, 10);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).isNotEmpty();
        verify(alojamientoDAO, times(1)).findActivos(0, 10);
    }

    // ==================== BUSCAR TESTS ====================

    @Test
    @DisplayName("BUSCAR - Filtros válidos retorna paginación")
    void buscar_FiltrosValidos_RetornaPaginacion() {
        PaginacionResponse<AlojamientoDTO> paginacionMock =
                PaginacionResponse.<AlojamientoDTO>builder()
                        .contenido(List.of(alojamientoDTOMock))
                        .paginaActual(1)
                        .tamanoPagina(10)
                        .totalElementos(1L)
                        .totalPaginas(1)
                        .esPrimera(false)
                        .esUltima(true)
                        .tieneSiguiente(false)
                        .tieneAnterior(true)
                        .build();

        when(alojamientoDAO.buscarConFiltros(buscarRequestValido)).thenReturn(paginacionMock);

        PaginacionResponse<AlojamientoDTO> resultado = alojamientoService.buscar(buscarRequestValido);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).isNotEmpty();
        verify(alojamientoDAO, times(1)).buscarConFiltros(buscarRequestValido);
    }


    @Test
    @DisplayName("BUSCAR - Solo checkin sin checkout lanza IllegalArgumentException")
    void buscar_SoloCheckinSinCheckout_LanzaExcepcion() {
        // ARRANGE
        buscarRequestValido.setFechaCheckin(LocalDate.now().plusDays(10));
        buscarRequestValido.setFechaCheckout(null);

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.buscar(buscarRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Debe enviar checkin y checkout juntos");

        verify(alojamientoDAO, never()).buscarConFiltros(any());
    }

    @Test
    @DisplayName("BUSCAR - Solo checkout sin checkin lanza IllegalArgumentException")
    void buscar_SoloCheckoutSinCheckin_LanzaExcepcion() {
        // ARRANGE
        buscarRequestValido.setFechaCheckin(null);
        buscarRequestValido.setFechaCheckout(LocalDate.now().plusDays(15));

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.buscar(buscarRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Debe enviar checkin y checkout juntos");

        verify(alojamientoDAO, never()).buscarConFiltros(any());
    }

    // ==================== VALIDACIÓN COORDENADAS TESTS ====================

    @Test
    @DisplayName("VALIDAR LAT LON - Coordenadas válidas no lanza excepción")
    void validarLatLon_CoordenadasValidas_NoLanzaExcepcion() {
        // ARRANGE
        when(anfitrionRepository.findByUsuarioId(USUARIO_ID_VALIDO)).thenReturn(Optional.of(anfitrionMock));
        when(alojamientoDAO.save(any(), anyInt())).thenReturn(alojamientoDTOMock);

        // ACT & ASSERT
        assertThatCode(() -> {
            alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido);
        }).doesNotThrowAnyException();

        // Opcional: verificar llamadas
        verify(anfitrionRepository).findByUsuarioId(USUARIO_ID_VALIDO);
        verify(alojamientoDAO).save(any(), anyInt());
    }


    @Test
    @DisplayName("VALIDAR LAT LON - Latitud fuera de rango lanza excepción")
    void validarLatLon_LatitudFueraDeRango_LanzaExcepcion() {
        // ARRANGE
        crearRequestValido.setLatitud(BigDecimal.valueOf(100)); // Latitud > 90

        // ACT & ASSERT
        assertThatThrownBy(() -> alojamientoService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Latitud inválida");
    }
}