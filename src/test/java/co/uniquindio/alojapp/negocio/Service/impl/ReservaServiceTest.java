package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CancelarReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.impl.ReservaServiceIMPL;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.persistencia.DAO.ReservaDAO;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ReservaServiceIMPL
 *
 * OBJETIVO: Probar la lógica de negocio del servicio de reservas de forma aislada
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ReservaService - Unit Tests")
public class ReservaServiceTest {

    @Mock
    private ReservaDAO reservaDAO;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AlojamientoRepository alojamientoRepository;

    @InjectMocks
    private ReservaServiceIMPL reservaService;

    // DATOS DE PRUEBA
    private Usuario usuarioMock;
    private Usuario otroUsuarioMock;
    private Alojamiento alojamientoMock;
    private Reserva reservaMock;
    private ReservaDTO reservaDTOMock;
    private CrearReservaRequest crearRequestValido;
    private CancelarReservaRequest cancelarRequestValido;

    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer OTRO_USUARIO_ID = 2;
    private final Integer ALOJAMIENTO_ID_VALIDO = 1;
    private final Integer RESERVA_ID_VALIDO = 1;
    private final Integer ANFITRION_ID_VALIDO = 3;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario();
        usuarioMock.setId(USUARIO_ID_VALIDO);
        usuarioMock.setNombre("Juan Pérez");
        usuarioMock.setEmail("juan@example.com");

        otroUsuarioMock = new Usuario();
        otroUsuarioMock.setId(OTRO_USUARIO_ID);
        otroUsuarioMock.setNombre("María García");
        otroUsuarioMock.setEmail("maria@example.com");

        alojamientoMock = new Alojamiento();
        alojamientoMock.setId(ALOJAMIENTO_ID_VALIDO);
        alojamientoMock.setTitulo("Casa de playa");
        alojamientoMock.setPrecioNoche(BigDecimal.valueOf(150000));

        reservaMock = new Reserva();
        reservaMock.setId(RESERVA_ID_VALIDO);
        reservaMock.setHuesped(usuarioMock);
        reservaMock.setAlojamiento(alojamientoMock);
        reservaMock.setEstado(EstadoReserva.CONFIRMADA);
        reservaMock.setPrecioTotal(BigDecimal.valueOf(750000));
        reservaMock.setFechaCheckin(LocalDate.now().plusDays(10));
        reservaMock.setFechaCheckout(LocalDate.now().plusDays(15));

        reservaDTOMock = new ReservaDTO();
        reservaDTOMock.setId(RESERVA_ID_VALIDO);
        reservaDTOMock.setHuespedId((long) USUARIO_ID_VALIDO);
        reservaDTOMock.setAlojamientoId((long) ALOJAMIENTO_ID_VALIDO);
        reservaDTOMock.setEstado("CONFIRMADA");
        reservaDTOMock.setPrecioTotal(BigDecimal.valueOf(750000));
        reservaDTOMock.setFechaCheckin(LocalDate.now().plusDays(10));
        reservaDTOMock.setFechaCheckout(LocalDate.now().plusDays(15));

        crearRequestValido = new CrearReservaRequest();
        crearRequestValido.setAlojamientoId(ALOJAMIENTO_ID_VALIDO);
        crearRequestValido.setFechaCheckin(LocalDate.now().plusDays(10));
        crearRequestValido.setFechaCheckout(LocalDate.now().plusDays(15));
        crearRequestValido.setNumeroHuespedes(2);
        cancelarRequestValido = new CancelarReservaRequest();
        cancelarRequestValido.setMotivoCancelacion("Cambio de planes");
    }



    // ==================== CREAR RESERVA TESTS ====================

    @Test
    @DisplayName("CREAR - Datos válidos retorna reserva creada")
    void crear_DatosValidos_RetornaReservaDTO() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(alojamientoRepository.findById(ALOJAMIENTO_ID_VALIDO)).thenReturn(Optional.of(alojamientoMock));
        when(reservaDAO.save(crearRequestValido, USUARIO_ID_VALIDO)).thenReturn(reservaDTOMock);

        // ACT
        ReservaDTO resultado = reservaService.crear(USUARIO_ID_VALIDO, crearRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(resultado.getEstado()).isEqualTo("CONFIRMADA");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(alojamientoRepository, times(1)).findById(ALOJAMIENTO_ID_VALIDO);
        verify(reservaDAO, times(1)).save(crearRequestValido, USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("CREAR - Usuario no existe lanza ReglaNegocioException")
    void crear_UsuarioNoExiste_LanzaReglaNegocioException() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("Usuario no encontrado");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(alojamientoRepository, never()).findById(anyInt());
        verify(reservaDAO, never()).save(any(), anyInt());
    }

    @Test
    @DisplayName("CREAR - Alojamiento no existe lanza ReglaNegocioException")
    void crear_AlojamientoNoExiste_LanzaReglaNegocioException() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(alojamientoRepository.findById(ALOJAMIENTO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("Alojamiento no encontrado");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(alojamientoRepository, times(1)).findById(ALOJAMIENTO_ID_VALIDO);
        verify(reservaDAO, never()).save(any(), anyInt());
    }


    @Test
    @DisplayName("CREAR - DAO lanza RuntimeException mapea a ReglaNegocioException")
    void crear_DAOLanzaRuntimeException_MapeaAReglaNegocioException() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(alojamientoRepository.findById(ALOJAMIENTO_ID_VALIDO)).thenReturn(Optional.of(alojamientoMock));
        when(reservaDAO.save(crearRequestValido, USUARIO_ID_VALIDO))
                .thenThrow(new RuntimeException("No disponible en esas fechas"));

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("No disponible en esas fechas");

        verify(usuarioRepository, times(1)).findById(USUARIO_ID_VALIDO);
        verify(alojamientoRepository, times(1)).findById(ALOJAMIENTO_ID_VALIDO);
        verify(reservaDAO, times(1)).save(crearRequestValido, USUARIO_ID_VALIDO);
    }

    // ==================== CANCELAR RESERVA TESTS ====================

    @Test
    @DisplayName("CANCELAR - Reserva existe y usuario es propietario retorna reserva cancelada")
    void cancelar_ReservaExisteYUsuarioEsPropietario_RetornaReservaCancelada() {
        // ARRANGE
        when(reservaDAO.findEntityById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(reservaDAO.cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido))
                .thenReturn(Optional.of(reservaDTOMock));

        // ACT
        ReservaDTO resultado = reservaService.cancelar(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO, cancelarRequestValido);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(reservaDAO, times(1)).findEntityById(RESERVA_ID_VALIDO);
        verify(reservaDAO, times(1)).cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido);
    }

    @Test
    @DisplayName("CANCELAR - Reserva no existe lanza RecursoNoEncontradoException")
    void cancelar_ReservaNoExiste_LanzaRecursoNoEncontradoException() {
        // ARRANGE
        when(reservaDAO.findEntityById(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.cancelar(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO, cancelarRequestValido))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("No se encontro el recurso");

        verify(reservaDAO, times(1)).findEntityById(RESERVA_ID_VALIDO);
        verify(reservaDAO, never()).cancelar(anyInt(), anyInt(), any());
    }

    @Test
    @DisplayName("CANCELAR - Usuario no es propietario lanza AccesoNoAutorizadoException")
    void cancelar_UsuarioNoEsPropietario_LanzaAccesoNoAutorizadoException() {
        // ARRANGE
        reservaMock.setHuesped(otroUsuarioMock); // La reserva es de otro usuario
        when(reservaDAO.findEntityById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.cancelar(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO, cancelarRequestValido))
                .isInstanceOf(AccesoNoAutorizadoException.class)
                .hasMessageContaining("No puede cancelar reservas de otro usuario");

        verify(reservaDAO, times(1)).findEntityById(RESERVA_ID_VALIDO);
        verify(reservaDAO, never()).cancelar(anyInt(), anyInt(), any());
    }

    @Test
    @DisplayName("CANCELAR - DAO retorna empty lanza ReglaNegocioException")
    void cancelar_DAORetornaEmpty_LanzaReglaNegocioException() {
        // ARRANGE
        when(reservaDAO.findEntityById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(reservaDAO.cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido))
                .thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.cancelar(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO, cancelarRequestValido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("No fue posible cancelar la reserva");

        verify(reservaDAO, times(1)).findEntityById(RESERVA_ID_VALIDO);
        verify(reservaDAO, times(1)).cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido);
    }

    @Test
    @DisplayName("CANCELAR - DAO lanza RuntimeException mapea a ReglaNegocioException")
    void cancelar_DAOLanzaRuntimeException_MapeaAReglaNegocioException() {
        // ARRANGE
        when(reservaDAO.findEntityById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(reservaDAO.cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido))
                .thenThrow(new RuntimeException("No se puede cancelar menos de 48 horas antes"));

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.cancelar(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO, cancelarRequestValido))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("No se puede cancelar menos de 48 horas antes");

        verify(reservaDAO, times(1)).findEntityById(RESERVA_ID_VALIDO);
        verify(reservaDAO, times(1)).cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido);
    }

    // ==================== OBTENER POR ID TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Reserva existe retorna DTO")
    void obtenerPorId_ReservaExiste_RetornaDTO() {
        // ARRANGE
        when(reservaDAO.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaDTOMock));

        // ACT
        ReservaDTO resultado = reservaService.obtenerPorId(RESERVA_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(RESERVA_ID_VALIDO);
        verify(reservaDAO, times(1)).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR ID - Reserva no existe lanza RecursoNoEncontradoException")
    void obtenerPorId_ReservaNoExiste_LanzaRecursoNoEncontradoException() {
        // ARRANGE
        when(reservaDAO.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> reservaService.obtenerPorId(RESERVA_ID_VALIDO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Reserva no encontrada");

        verify(reservaDAO, times(1)).findById(RESERVA_ID_VALIDO);
    }

    // ==================== LISTAR TESTS ====================

    @Test
    @DisplayName("LISTAR POR HUESPED - Retorna paginación correcta")
    void listarPorHuesped_RetornaPaginacionCorrecta() {
        // ARRANGE
        PaginacionResponse<ReservaDTO> paginacionMock = PaginacionResponse.<ReservaDTO>builder()
                .contenido(List.of(reservaDTOMock))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(1L)
                .totalPaginas(1)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        when(reservaDAO.findByHuesped(USUARIO_ID_VALIDO, 0, 10)).thenReturn(paginacionMock);

        // ACT
        PaginacionResponse<ReservaDTO> resultado = reservaService.listarPorHuesped(USUARIO_ID_VALIDO, 0, 10);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).isNotEmpty();
        assertThat(resultado.getContenido().get(0).getId()).isEqualTo(RESERVA_ID_VALIDO);
        verify(reservaDAO, times(1)).findByHuesped(USUARIO_ID_VALIDO, 0, 10);
    }

    @Test
    @DisplayName("LISTAR POR ANFITRION - Retorna paginación correcta")
    void listarPorAnfitrion_RetornaPaginacionCorrecta() {
        // ARRANGE
        PaginacionResponse<ReservaDTO> paginacionMock = PaginacionResponse.<ReservaDTO>builder()
                .contenido(List.of(reservaDTOMock))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(1L)
                .totalPaginas(1)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        when(reservaDAO.findByAnfitrion(ANFITRION_ID_VALIDO, 0, 10)).thenReturn(paginacionMock);

        // ACT
        PaginacionResponse<ReservaDTO> resultado = reservaService.listarPorAnfitrion(ANFITRION_ID_VALIDO, 0, 10);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).isNotEmpty();
        verify(reservaDAO, times(1)).findByAnfitrion(ANFITRION_ID_VALIDO, 0, 10);
    }

    @Test
    @DisplayName("LISTAR COMPLETADAS SIN COMENTARIO - Retorna lista correcta")
    void listarCompletadasSinComentario_RetornaListaCorrecta() {
        // ARRANGE
        List<ReservaDTO> reservasMock = List.of(reservaDTOMock);
        when(reservaDAO.findCompletadasSinComentario(USUARIO_ID_VALIDO)).thenReturn(reservasMock);

        // ACT
        List<ReservaDTO> resultado = reservaService.listarCompletadasSinComentario(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(reservaDAO, times(1)).findCompletadasSinComentario(USUARIO_ID_VALIDO);
    }

    // ==================== CONTAR TESTS ====================

    @Test
    @DisplayName("CONTAR POR ESTADO - Retorna cantidad correcta")
    void contarPorEstado_RetornaCantidadCorrecta() {
        // ARRANGE
        Long cantidadEsperada = 5L;
        when(reservaDAO.countByEstado(EstadoReserva.CONFIRMADA)).thenReturn(cantidadEsperada);

        // ACT
        Long resultado = reservaService.contarPorEstado(EstadoReserva.CONFIRMADA);

        // ASSERT
        assertThat(resultado).isEqualTo(cantidadEsperada);
        verify(reservaDAO, times(1)).countByEstado(EstadoReserva.CONFIRMADA);
    }

    // ==================== CALCULAR INGRESOS TESTS ====================

    @Test
    @DisplayName("CALCULAR INGRESOS TOTALES - Con ingresos retorna valor correcto")
    void calcularIngresosTotales_ConIngresos_RetornaValorCorrecto() {
        // ARRANGE
        BigDecimal ingresosEsperados = BigDecimal.valueOf(2500000);
        when(reservaDAO.calcularIngresosTotales(ANFITRION_ID_VALIDO)).thenReturn(ingresosEsperados);

        // ACT
        BigDecimal resultado = reservaService.calcularIngresosTotales(ANFITRION_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualByComparingTo(ingresosEsperados);
        verify(reservaDAO, times(1)).calcularIngresosTotales(ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("CALCULAR INGRESOS TOTALES - Sin ingresos retorna cero")
    void calcularIngresosTotales_SinIngresos_RetornaCero() {
        // ARRANGE
        when(reservaDAO.calcularIngresosTotales(ANFITRION_ID_VALIDO)).thenReturn(null);

        // ACT
        BigDecimal resultado = reservaService.calcularIngresosTotales(ANFITRION_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualByComparingTo(BigDecimal.ZERO);
        verify(reservaDAO, times(1)).calcularIngresosTotales(ANFITRION_ID_VALIDO);
    }

    @Test
    @DisplayName("CALCULAR INGRESOS POR PERIODO - Con ingresos retorna valor correcto")
    void calcularIngresosPorPeriodo_ConIngresos_RetornaValorCorrecto() {
        // ARRANGE
        LocalDate inicio = LocalDate.now().minusMonths(1);
        LocalDate fin = LocalDate.now();
        BigDecimal ingresosEsperados = BigDecimal.valueOf(1500000);

        when(reservaDAO.calcularIngresosPorPeriodo(ANFITRION_ID_VALIDO, inicio, fin))
                .thenReturn(ingresosEsperados);

        // ACT
        BigDecimal resultado = reservaService.calcularIngresosPorPeriodo(ANFITRION_ID_VALIDO, inicio, fin);

        // ASSERT
        assertThat(resultado).isEqualByComparingTo(ingresosEsperados);
        verify(reservaDAO, times(1)).calcularIngresosPorPeriodo(ANFITRION_ID_VALIDO, inicio, fin);
    }

    @Test
    @DisplayName("CALCULAR INGRESOS POR PERIODO - Sin ingresos retorna cero")
    void calcularIngresosPorPeriodo_SinIngresos_RetornaCero() {
        // ARRANGE
        LocalDate inicio = LocalDate.now().minusMonths(1);
        LocalDate fin = LocalDate.now();

        when(reservaDAO.calcularIngresosPorPeriodo(ANFITRION_ID_VALIDO, inicio, fin))
                .thenReturn(null);

        // ACT
        BigDecimal resultado = reservaService.calcularIngresosPorPeriodo(ANFITRION_ID_VALIDO, inicio, fin);

        // ASSERT
        assertThat(resultado).isEqualByComparingTo(BigDecimal.ZERO);
        verify(reservaDAO, times(1)).calcularIngresosPorPeriodo(ANFITRION_ID_VALIDO, inicio, fin);
    }

    // ==================== COMPLETAR RESERVAS VENCIDAS TESTS ====================

    @Test
    @DisplayName("COMPLETAR RESERVAS VENCIDAS - Ejecuta correctamente")
    void completarReservasVencidas_EjecutaCorrectamente() {
        // ARRANGE
        doNothing().when(reservaDAO).completarReservasVencidas();

        // ACT & ASSERT - No debe lanzar excepción
        assertThatCode(() -> reservaService.completarReservasVencidas())
                .doesNotThrowAnyException();

        verify(reservaDAO, times(1)).completarReservasVencidas();
    }

    // ==================== MAPEO DE EXCEPCIONES TESTS ====================

    @Test
    @DisplayName("MAPEO EXCEPCIONES - RuntimeException del DAO se mapea a ReglaNegocioException")
    void mapeoExcepciones_RuntimeExceptionDAOSeMapeaAReglaNegocioException() {
        // ARRANGE
        lenient().when(usuarioRepository.findById(USUARIO_ID_VALIDO))
                .thenReturn(Optional.of(usuarioMock));
        lenient().when(alojamientoRepository.findById(ALOJAMIENTO_ID_VALIDO))
                .thenReturn(Optional.of(alojamientoMock));

        // Simular diferentes RuntimeException del DAO
        String[] mensajesError = {
                "No disponible en esas fechas",
                "Capacidad excedida",
                "Fecha check-in debe ser futura",
                "No se puede cancelar menos de 48 horas antes"
        };

        for (String mensaje : mensajesError) {
            doThrow(new RuntimeException(mensaje))
                    .when(reservaDAO)
                    .save(crearRequestValido, USUARIO_ID_VALIDO);

            assertThatThrownBy(() -> reservaService.crear(USUARIO_ID_VALIDO, crearRequestValido))
                    .isInstanceOf(ReglaNegocioException.class)
                    .hasMessageContaining(mensaje);

            // Limpieza para la siguiente iteración
            reset(reservaDAO);
        }
    }


    // ==================== VERIFICACIÓN DE PROPIEDAD TESTS ====================

    @Test
    @DisplayName("VERIFICACION PROPIEDAD - Usuario es propietario permite cancelar")
    void verificacionPropiedad_UsuarioEsPropietario_PermiteCancelar() {
        // ARRANGE
        reservaMock.setHuesped(usuarioMock); // El usuario es el propietario
        when(reservaDAO.findEntityById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(reservaDAO.cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido))
                .thenReturn(Optional.of(reservaDTOMock));

        // ACT & ASSERT - No debe lanzar excepción
        assertThatCode(() ->
                reservaService.cancelar(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO, cancelarRequestValido)
        ).doesNotThrowAnyException();

        verify(reservaDAO, times(1)).findEntityById(RESERVA_ID_VALIDO);
        verify(reservaDAO, times(1)).cancelar(RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, cancelarRequestValido);
    }
}