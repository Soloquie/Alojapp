package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.excepciones.AccesoNoAutorizadoException;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.PagoDAO;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
import co.uniquindio.alojapp.persistencia.Entity.Pago;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Mapper.PagoMapper;
import co.uniquindio.alojapp.persistencia.Repository.PagoRepository;
import co.uniquindio.alojapp.persistencia.Repository.ReservaRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para PagoServiceIMPL
 *
 * OBJETIVO: Probar la lógica de gestión de pagos y sus reglas de negocio
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PagoService - Unit Tests")
public class PagoServiceTest {

    @Mock
    private PagoDAO pagoDAO;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoServiceIMPL pagoService;

    // DATOS DE PRUEBA
    private final Integer USUARIO_ID_VALIDO = 1;
    private final Integer USUARIO_ID_NO_AUTOR = 2;
    private final Integer RESERVA_ID_VALIDO = 1;
    private final Integer RESERVA_ID_INEXISTENTE = 999;
    private final Integer PAGO_ID_VALIDO = 1;
    private final Integer ANFITRION_ID_VALIDO = 3;

    private final String METODO_PAGO_VALIDO = "TARJETA_CREDITO";
    private final String METODO_PAGO_INVALIDO = "BITCOIN";
    private final String ESTADO_PAGO_APROBADO = "APROBADO";
    private final String ESTADO_PAGO_PENDIENTE = "PENDIENTE";

    private final BigDecimal MONTO_VALIDO = BigDecimal.valueOf(150000.00);
    private final BigDecimal MONTO_INCORRECTO = BigDecimal.valueOf(100000.00);

    private Reserva reservaMock;
    private Usuario usuarioMock;
    private Pago pagoMock;
    private PagoDTO pagoDTOMock;

    @BeforeEach
    void setUp() {
        // Configurar usuario mock
        usuarioMock = Usuario.builder()
                .id(USUARIO_ID_VALIDO)
                .nombre("Usuario Test")
                .email("usuario@test.com")
                .build();

        // Configurar reserva mock
        reservaMock = Reserva.builder()
                .id(RESERVA_ID_VALIDO)
                .huesped(usuarioMock)
                .estado(EstadoReserva.PENDIENTE)
                .precioTotal(MONTO_VALIDO)
                .fechaCheckin(LocalDate.now().plusDays(2)) // Check-in en 2 días
                .build();

        // Configurar pago mock
        pagoMock = Pago.builder()
                .id(PAGO_ID_VALIDO)
                .monto(MONTO_VALIDO.doubleValue())
                .metodoPago(METODO_PAGO_VALIDO)
                .estado(ESTADO_PAGO_APROBADO)
                .fechaPago(LocalDateTime.now())
                .reserva(reservaMock)
                .usuario(usuarioMock)
                .build();

        // Configurar DTO mock CORREGIDO
        pagoDTOMock = PagoDTO.builder()
                .id(PAGO_ID_VALIDO)
                .monto(MONTO_VALIDO.doubleValue())
                .metodoPago(METODO_PAGO_VALIDO)
                .estado(ESTADO_PAGO_APROBADO)
                .fechaPago(LocalDateTime.now())
                .reservaId(String.valueOf(RESERVA_ID_VALIDO))
                .usuarioEmail("usuario@test.com")
                .referenciaTransaccion("TXN-12345")
                .build();
    }

    // ==================== PAGAR RESERVA TESTS ====================

    @Test
    @DisplayName("PAGAR RESERVA - Datos válidos procesa pago exitosamente")
    void pagarReserva_DatosValidos_ProcesaPagoExitosamente() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(pagoRepository.findByReservaId(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());
        when(pagoDAO.crearParaReserva(eq(RESERVA_ID_VALIDO), eq(USUARIO_ID_VALIDO), any(BigDecimal.class),
                eq(METODO_PAGO_VALIDO), eq("APROBADO"))).thenReturn(pagoDTOMock);

        // ACT
        PagoDTO resultado = pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(PAGO_ID_VALIDO);
        assertThat(resultado.getEstado()).isEqualTo(ESTADO_PAGO_APROBADO);

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
        verify(pagoRepository).findByReservaId(RESERVA_ID_VALIDO);
        verify(pagoDAO).crearParaReserva(eq(RESERVA_ID_VALIDO), eq(USUARIO_ID_VALIDO),
                any(BigDecimal.class), eq(METODO_PAGO_VALIDO), eq("APROBADO"));
    }

    @Test
    @DisplayName("PAGAR RESERVA - Reserva no existe lanza RecursoNoEncontradoException")
    void pagarReserva_ReservaNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_INEXISTENTE)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_INEXISTENTE,
                METODO_PAGO_VALIDO, MONTO_VALIDO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Reserva no encontrada");

        verify(reservaRepository).findById(RESERVA_ID_INEXISTENTE);
        verify(pagoRepository, never()).findByReservaId(anyInt());
    }

    @Test
    @DisplayName("PAGAR RESERVA - Usuario no autor lanza ReglaNegocioException")
    void pagarReserva_UsuarioNoAutor_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_NO_AUTOR, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("La reserva no pertenece al usuario autenticado");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
        verify(pagoRepository, never()).findByReservaId(anyInt());
    }

    @Test
    @DisplayName("PAGAR RESERVA - Reserva cancelada lanza ReglaNegocioException")
    void pagarReserva_ReservaCancelada_LanzaExcepcion() {
        // ARRANGE
        reservaMock.setEstado(EstadoReserva.CANCELADA);
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("No se puede pagar una reserva cancelada o completada");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Check-in pasado lanza ReglaNegocioException")
    void pagarReserva_CheckinPasado_LanzaExcepcion() {
        // ARRANGE
        reservaMock.setFechaCheckin(LocalDate.now().minusDays(1)); // Check-in ayer
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El pago debe realizarse antes del check-in");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Método pago inválido lanza ReglaNegocioException")
    void pagarReserva_MetodoPagoInvalido_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_INVALIDO, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("Método de pago no soportado");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Monto incorrecto lanza ReglaNegocioException")
    void pagarReserva_MontoIncorrecto_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_INCORRECTO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El monto del pago no coincide con el precio total de la reserva");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Pago duplicado lanza ReglaNegocioException")
    void pagarReserva_PagoDuplicado_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(pagoRepository.findByReservaId(RESERVA_ID_VALIDO)).thenReturn(Optional.of(pagoMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("La reserva ya tiene un pago registrado");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
        verify(pagoRepository).findByReservaId(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Actualiza estado reserva de PENDIENTE a CONFIRMADA")
    void pagarReserva_ReservaPendiente_ActualizaAConfirmada() {
        // ARRANGE
        reservaMock.setEstado(EstadoReserva.PENDIENTE);
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(pagoRepository.findByReservaId(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());
        when(pagoDAO.crearParaReserva(anyInt(), anyInt(), any(BigDecimal.class), anyString(), anyString()))
                .thenReturn(pagoDTOMock);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaMock);

        // ACT
        PagoDTO resultado = pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(reservaRepository).save(argThat(reserva ->
                reserva.getEstado() == EstadoReserva.CONFIRMADA
        ));
    }

    // ==================== OBTENER POR ID TESTS ====================

    @Test
    @DisplayName("OBTENER POR ID - Pago existe retorna DTO")
    void obtenerPorId_PagoExiste_RetornaDTO() {
        // ARRANGE
        when(pagoDAO.findById(PAGO_ID_VALIDO)).thenReturn(Optional.of(pagoDTOMock));

        // ACT
        PagoDTO resultado = pagoService.obtenerPorId(PAGO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(PAGO_ID_VALIDO);
        verify(pagoDAO).findById(PAGO_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR ID - Pago no existe lanza RecursoNoEncontradoException")
    void obtenerPorId_PagoNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(pagoDAO.findById(PAGO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.obtenerPorId(PAGO_ID_VALIDO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Pago no encontrado");

        verify(pagoDAO).findById(PAGO_ID_VALIDO);
    }

    // ==================== OBTENER POR RESERVA TESTS ====================

    @Test
    @DisplayName("OBTENER POR RESERVA - Pago existe retorna DTO")
    void obtenerPorReserva_PagoExiste_RetornaDTO() {
        // ARRANGE
        when(pagoDAO.findByReserva(RESERVA_ID_VALIDO)).thenReturn(Optional.of(pagoDTOMock));

        // ACT
        PagoDTO resultado = pagoService.obtenerPorReserva(RESERVA_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getReservaId()).isEqualTo(String.valueOf(RESERVA_ID_VALIDO));
        verify(pagoDAO).findByReserva(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER POR RESERVA - Pago no existe lanza RecursoNoEncontradoException")
    void obtenerPorReserva_PagoNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(pagoDAO.findByReserva(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.obtenerPorReserva(RESERVA_ID_VALIDO))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Pago no encontrado para la reserva");

        verify(pagoDAO).findByReserva(RESERVA_ID_VALIDO);
    }

    // ==================== LISTAR POR USUARIO TESTS ====================

    @Test
    @DisplayName("LISTAR POR USUARIO - Retorna lista de pagos")
    void listarPorUsuario_RetornaListaPagos() {
        // ARRANGE
        List<PagoDTO> pagosMock = List.of(pagoDTOMock);
        when(pagoDAO.listarPorUsuario(USUARIO_ID_VALIDO)).thenReturn(pagosMock);

        // ACT
        List<PagoDTO> resultado = pagoService.listarPorUsuario(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        verify(pagoDAO).listarPorUsuario(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("LISTAR POR USUARIO PAGINADO - Retorna paginación")
    void listarPorUsuarioPaginado_RetornaPaginacion() {
        // ARRANGE
        PaginacionResponse<PagoDTO> paginacionMock = PaginacionResponse.<PagoDTO>builder()
                .contenido(List.of(pagoDTOMock))
                .paginaActual(0)
                .tamanoPagina(10)
                .totalElementos(1L)
                .totalPaginas(1)
                .esPrimera(true)
                .esUltima(true)
                .tieneSiguiente(false)
                .tieneAnterior(false)
                .build();

        when(pagoDAO.listarPorUsuario(USUARIO_ID_VALIDO, 0, 10)).thenReturn(paginacionMock);

        // ACT
        PaginacionResponse<PagoDTO> resultado = pagoService.listarPorUsuario(USUARIO_ID_VALIDO, 0, 10);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getContenido()).hasSize(1);
        verify(pagoDAO).listarPorUsuario(USUARIO_ID_VALIDO, 0, 10);
    }

    // ==================== LISTAR POR ESTADO/METODO TESTS ====================

    @Test
    @DisplayName("LISTAR POR ESTADO - Retorna lista filtrada")
    void listarPorEstado_RetornaListaFiltrada() {
        // ARRANGE
        List<PagoDTO> pagosMock = List.of(pagoDTOMock);
        when(pagoDAO.listarPorEstado(ESTADO_PAGO_APROBADO)).thenReturn(pagosMock);

        // ACT
        List<PagoDTO> resultado = pagoService.listarPorEstado(ESTADO_PAGO_APROBADO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        verify(pagoDAO).listarPorEstado(ESTADO_PAGO_APROBADO);
    }

    @Test
    @DisplayName("LISTAR POR METODO - Retorna lista filtrada")
    void listarPorMetodo_RetornaListaFiltrada() {
        // ARRANGE
        List<PagoDTO> pagosMock = List.of(pagoDTOMock);
        when(pagoDAO.listarPorMetodo(METODO_PAGO_VALIDO)).thenReturn(pagosMock);

        // ACT
        List<PagoDTO> resultado = pagoService.listarPorMetodo(METODO_PAGO_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        verify(pagoDAO).listarPorMetodo(METODO_PAGO_VALIDO);
    }

    // ==================== MÉTRICAS TESTS ====================

    @Test
    @DisplayName("TOTAL PAGADO POR USUARIO - Calcula total correctamente")
    void totalPagadoPorUsuario_CalculaTotalCorrectamente() {
        // ARRANGE
        BigDecimal totalEsperado = BigDecimal.valueOf(300000.00);
        when(pagoDAO.totalPagadoPorUsuario(USUARIO_ID_VALIDO)).thenReturn(totalEsperado);

        // ACT
        BigDecimal resultado = pagoService.totalPagadoPorUsuario(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isEqualTo(totalEsperado);
        verify(pagoDAO).totalPagadoPorUsuario(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("CONTAR POR ESTADO - Retorna conteo correcto")
    void contarPorEstado_RetornaConteoCorrecto() {
        // ARRANGE
        Long conteoEsperado = 5L;
        when(pagoDAO.contarPorEstado(ESTADO_PAGO_APROBADO)).thenReturn(conteoEsperado);

        // ACT
        Long resultado = pagoService.contarPorEstado(ESTADO_PAGO_APROBADO);

        // ASSERT
        assertThat(resultado).isEqualTo(conteoEsperado);
        verify(pagoDAO).contarPorEstado(ESTADO_PAGO_APROBADO);
    }

    // ==================== ACTUALIZAR ESTADO TESTS ====================

    @Test
    @DisplayName("ACTUALIZAR ESTADO - Estado válido actualiza correctamente")
    void actualizarEstado_EstadoValido_ActualizaCorrectamente() {
        // ARRANGE
        String nuevoEstado = "RECHAZADO";
        when(pagoDAO.actualizarEstado(PAGO_ID_VALIDO, nuevoEstado)).thenReturn(pagoDTOMock);

        // ACT
        PagoDTO resultado = pagoService.actualizarEstado(PAGO_ID_VALIDO, nuevoEstado);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(pagoDAO).actualizarEstado(PAGO_ID_VALIDO, nuevoEstado);
    }

    // ==================== REGISTRAR PAGO TESTS ====================

    @Test
    @DisplayName("REGISTRAR PAGO - Datos válidos registra pago exitosamente")
    void registrarPago_DatosValidos_RegistraExitosamente() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(pagoRepository.findByReservaId(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoMock);
        when(pagoMapper.pagoToDTO(any(Pago.class))).thenReturn(pagoDTOMock);

        // ACT
        PagoDTO resultado = pagoService.registrarPago(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO.doubleValue(), LocalDateTime.now());

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(usuarioRepository).findById(USUARIO_ID_VALIDO);
        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
        verify(pagoRepository).findByReservaId(RESERVA_ID_VALIDO);
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("REGISTRAR PAGO - Usuario no existe lanza RecursoNoEncontradoException")
    void registrarPago_UsuarioNoExiste_LanzaExcepcion() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.registrarPago(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO.doubleValue(), LocalDateTime.now()))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("Usuario no encontrado");

        verify(usuarioRepository).findById(USUARIO_ID_VALIDO);
        verify(reservaRepository, never()).findById(anyInt());
    }

    @Test
    @DisplayName("REGISTRAR PAGO - Monto cero lanza ReglaNegocioException")
    void registrarPago_MontoCero_LanzaExcepcion() {
        // ARRANGE
        when(usuarioRepository.findById(USUARIO_ID_VALIDO)).thenReturn(Optional.of(usuarioMock));
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(pagoRepository.findByReservaId(RESERVA_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.registrarPago(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, 0.0, LocalDateTime.now()))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El monto del pago debe ser mayor a cero");

        verify(usuarioRepository).findById(USUARIO_ID_VALIDO);
        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    // ==================== OBTENER DE USUARIO POR RESERVA TESTS ====================

    @Test
    @DisplayName("OBTENER DE USUARIO POR RESERVA - Datos válidos retorna pago")
    void obtenerDeUsuarioPorReserva_DatosValidos_RetornaPago() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));
        when(pagoRepository.findByReservaId(RESERVA_ID_VALIDO)).thenReturn(Optional.of(pagoMock));
        when(pagoMapper.pagoToDTO(pagoMock)).thenReturn(pagoDTOMock);

        // ACT
        PagoDTO resultado = pagoService.obtenerDeUsuarioPorReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getReservaId()).isEqualTo(String.valueOf(RESERVA_ID_VALIDO));
        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
        verify(pagoRepository).findByReservaId(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("OBTENER DE USUARIO POR RESERVA - Usuario no autor lanza AccesoNoAutorizadoException")
    void obtenerDeUsuarioPorReserva_UsuarioNoAutor_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.obtenerDeUsuarioPorReserva(USUARIO_ID_NO_AUTOR, RESERVA_ID_VALIDO))
                .isInstanceOf(AccesoNoAutorizadoException.class)
                .hasMessageContaining("La reserva no pertenece al usuario");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
        verify(pagoRepository, never()).findByReservaId(anyInt());
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("PAGAR RESERVA - Método pago null lanza ReglaNegocioException")
    void pagarReserva_MetodoPagoNull_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                null, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("Método de pago no soportado");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Monto null lanza ReglaNegocioException")
    void pagarReserva_MontoNull_LanzaExcepcion() {
        // ARRANGE
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, null))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El monto del pago no coincide con el precio total de la reserva");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }

    @Test
    @DisplayName("PAGAR RESERVA - Precio total null se compara con cero")
    void pagarReserva_PrecioTotalNull_ComparaConCero() {
        // ARRANGE
        reservaMock.setPrecioTotal(null);
        when(reservaRepository.findById(RESERVA_ID_VALIDO)).thenReturn(Optional.of(reservaMock));

        // ACT & ASSERT
        assertThatThrownBy(() -> pagoService.pagarReserva(USUARIO_ID_VALIDO, RESERVA_ID_VALIDO,
                METODO_PAGO_VALIDO, MONTO_VALIDO))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("El monto del pago no coincide con el precio total de la reserva");

        verify(reservaRepository).findById(RESERVA_ID_VALIDO);
    }
}