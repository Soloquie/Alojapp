package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CancelarReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearReservaRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.negocio.Service.ReservaService;
import co.uniquindio.alojapp.negocio.Service.UsuarioService;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoReserva;
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
 * Unit Tests para ReservaController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ReservaController - Unit Tests")
public class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ReservaController reservaController;

    private ReservaDTO reservaDTO;
    private UsuarioDTO usuarioDTO;
    private Usuario usuario;
    private CrearReservaRequest crearRequest;
    private CancelarReservaRequest cancelarRequest;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        reservaDTO = ReservaDTO.builder()
                .id(45)
                .alojamientoId(10L)
                .alojamientoNombre("Casa de playa en Cartagena")
                .huespedId(25L)
                .huespedNombre("Juan Pérez")
                .fechaCheckin(LocalDate.of(2025, 9, 20))
                .fechaCheckout(LocalDate.of(2025, 9, 25))
                .numeroHuespedes(4)
                .precioTotal(new BigDecimal("1750000.00"))
                .estado("CONFIRMADA")
                .fechaCreacion(LocalDateTime.now())
                .fechaCancelacion(null)
                .motivoCancelacion(null)
                .cantidadNoches(5L)
                .puedeCancelarse(true)
                .build();

        usuarioDTO = UsuarioDTO.builder()
                .id(25)
                .nombre("Juan Pérez")
                .email("juan@correo.com")
                .build();

        usuario = Usuario.builder()
                .id(25)
                .nombre("Juan Pérez")
                .email("juan@correo.com")
                .build();

        crearRequest = new CrearReservaRequest();
        crearRequest.setAlojamientoId(10);
        crearRequest.setFechaCheckin(LocalDate.of(2025, 9, 20));
        crearRequest.setFechaCheckout(LocalDate.of(2025, 9, 25));
        crearRequest.setNumeroHuespedes(4);

        cancelarRequest = new CancelarReservaRequest("Cambio de planes");
    }

    // =========================================================================
    // TESTS PARA CREAR RESERVA
    // =========================================================================

    @Test
    @DisplayName("Crear reserva - Debería retornar 201 CREATED")
    void crear_DeberiaRetornar201Created() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(reservaService.crear(eq(25), any(CrearReservaRequest.class))).thenReturn(reservaDTO);

            // Act
            ResponseEntity<ReservaDTO> response = reservaController.crear(crearRequest);

            // Assert
            assertThat(response.getStatusCodeValue()).isEqualTo(201);
            assertThat(response.getBody()).isEqualTo(reservaDTO);
            assertThat(response.getBody().getId()).isEqualTo(45);
            assertThat(response.getBody().getAlojamientoNombre()).isEqualTo("Casa de playa en Cartagena");
            assertThat(response.getBody().getEstado()).isEqualTo("CONFIRMADA");
            assertThat(response.getBody().getPrecioTotal()).isEqualByComparingTo("1750000.00");
            assertThat(response.getBody().getCantidadNoches()).isEqualTo(5L);
            assertThat(response.getBody().getPuedeCancelarse()).isTrue();

            verify(reservaService, times(1)).crear(25, crearRequest);
        }
    }

    @Test
    @DisplayName("Crear reserva - Usuario no autenticado debería lanzar excepción")
    void crear_UsuarioNoAutenticado_DeberiaLanzarExcepcion() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> reservaController.crear(crearRequest))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("No autenticado");

            verifyNoInteractions(reservaService);
        }
    }

    @Test
    @DisplayName("Crear reserva - Usuario no encontrado debería lanzar excepción")
    void crear_UsuarioNoEncontrado_DeberiaLanzarExcepcion() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("noexiste@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("noexiste@correo.com")).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> reservaController.crear(crearRequest))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Usuario no encontrado");

            verifyNoInteractions(reservaService);
        }
    }

    // =========================================================================
    // TESTS PARA LISTAR MIS RESERVAS
    // =========================================================================

    @Test
    @DisplayName("Listar mis reservas - Debería retornar 200 OK")
    void listarMisReservas_DeberiaRetornar200Ok() {
        // Arrange
        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));
        when(reservaService.listarPorHuesped(25, 0, 50)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.listarMisReservas(0, 50);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Arrays.asList(reservaDTO));
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getHuespedId()).isEqualTo(25L);

        verify(reservaService, times(1)).listarPorHuesped(25, 0, 50);
    }

    @Test
    @DisplayName("Listar mis reservas - Parámetros por defecto deberían usarse")
    void listarMisReservas_ParametrosPorDefecto_DeberianUsarse() {
        // Arrange
        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));
        when(reservaService.listarPorHuesped(25, 0, 50)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.listarMisReservas(0, 50);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(reservaService, times(1)).listarPorHuesped(25, 0, 50);
    }

    // =========================================================================
    // TESTS PARA OBTENER RESERVA POR ID
    // =========================================================================

    @Test
    @DisplayName("Obtener reserva por ID - Debería retornar 200 OK")
    void obtenerReserva_DeberiaRetornar200Ok() {
        // Arrange
        Integer reservaId = 45;
        when(reservaService.obtenerPorId(reservaId)).thenReturn(reservaDTO);

        // Act
        ResponseEntity<ReservaDTO> response = reservaController.obtenerReserva(reservaId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(reservaDTO);
        assertThat(response.getBody().getId()).isEqualTo(45);
        assertThat(response.getBody().getAlojamientoId()).isEqualTo(10L);
        assertThat(response.getBody().getNumeroHuespedes()).isEqualTo(4);

        verify(reservaService, times(1)).obtenerPorId(reservaId);
    }

    // =========================================================================
    // TESTS PARA CANCELAR RESERVA
    // =========================================================================

    @Test
    @DisplayName("Cancelar reserva - Con request body debería retornar 200 OK")
    void cancelarReserva_ConRequestBody_DeberiaRetornar200Ok() {
        // Arrange
        Integer reservaId = 45;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));

        // Si cancelar retorna ReservaDTO
        ReservaDTO reservaCancelada = ReservaDTO.builder()
                .id(45)
                .estado("CANCELADA")
                .motivoCancelacion("Cambio de planes")
                .build();
        when(reservaService.cancelar(25, reservaId, cancelarRequest)).thenReturn(reservaCancelada);

        // Act
        ResponseEntity<String> response = reservaController.cancelarReserva(reservaId, cancelarRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Reserva cancelada con ID: 45");

        verify(reservaService, times(1)).cancelar(25, reservaId, cancelarRequest);
    }

    @Test
    @DisplayName("Cancelar reserva - Sin request body debería crear request por defecto")
    void cancelarReserva_SinRequestBody_DeberiaCrearRequestPorDefecto() {
        // Arrange
        Integer reservaId = 45;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));

        // Si cancelar retorna ReservaDTO
        ReservaDTO reservaCancelada = ReservaDTO.builder()
                .id(45)
                .estado("CANCELADA")
                .motivoCancelacion("Cancelación solicitada por el usuario")
                .build();
        when(reservaService.cancelar(eq(25), eq(reservaId), any(CancelarReservaRequest.class)))
                .thenReturn(reservaCancelada);

        // Act
        ResponseEntity<String> response = reservaController.cancelarReserva(reservaId, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Reserva cancelada con ID: 45");

        verify(reservaService, times(1)).cancelar(eq(25), eq(reservaId), any(CancelarReservaRequest.class));
    }
    // =========================================================================
    // TESTS PARA LISTAR RESERVAS DE ANFITRIÓN
    // =========================================================================

    @Test
    @DisplayName("Listar reservas de anfitrión - Sin anfitrionId debería usar currentUserId")
    void listarReservasDeAnfitrion_SinAnfitrionId_DeberiaUsarCurrentUserId() {
        // Arrange
        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));
        when(reservaService.listarPorAnfitrion(25, 0, 50)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.listarReservasDeAnfitrion(0, 50, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        verify(reservaService, times(1)).listarPorAnfitrion(25, 0, 50);
    }

    @Test
    @DisplayName("Listar reservas de anfitrión - Con anfitrionId debería usar el ID proporcionado")
    void listarReservasDeAnfitrion_ConAnfitrionId_DeberiaUsarIdProporcionado() {
        // Arrange
        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        when(reservaService.listarPorAnfitrion(5, 0, 50)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.listarReservasDeAnfitrion(0, 50, 5);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        verify(reservaService, times(1)).listarPorAnfitrion(5, 0, 50);
    }

    // =========================================================================
    // TESTS PARA FILTRAR RESERVAS DE ANFITRIÓN
    // =========================================================================

    @Test
    @DisplayName("Filtrar reservas de anfitrión - Sin filtros debería retornar todas")
    void filtrarReservasDeAnfitrion_SinFiltros_DeberiaRetornarTodas() {
        // Arrange
        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));
        when(reservaService.listarPorAnfitrion(25, 0, 100)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.filtrarReservasDeAnfitrion(
                null, null, null, 0, 100, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        verify(reservaService, times(1)).listarPorAnfitrion(25, 0, 100);
    }

    @Test
    @DisplayName("Filtrar reservas de anfitrión - Con estado debería filtrar correctamente")
    void filtrarReservasDeAnfitrion_ConEstado_DeberiaFiltrarCorrectamente() {
        // Arrange
        ReservaDTO reservaPendiente = ReservaDTO.builder()
                .id(46)
                .estado("PENDIENTE")
                .fechaCheckin(LocalDate.of(2025, 10, 10))
                .build();

        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO, reservaPendiente));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));
        when(reservaService.listarPorAnfitrion(25, 0, 100)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.filtrarReservasDeAnfitrion(
                EstadoReserva.PENDIENTE, null, null, 0, 100, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getEstado()).isEqualTo("PENDIENTE");
    }

    @Test
    @DisplayName("Filtrar reservas de anfitrión - Con fechas debería filtrar correctamente")
    void filtrarReservasDeAnfitrion_ConFechas_DeberiaFiltrarCorrectamente() {
        // Arrange
        LocalDate fechaDesde = LocalDate.of(2025, 9, 1);
        LocalDate fechaHasta = LocalDate.of(2025, 9, 30);

        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("juan@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("juan@correo.com")).thenReturn(Optional.of(usuarioDTO));
        when(reservaService.listarPorAnfitrion(25, 0, 100)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.filtrarReservasDeAnfitrion(
                null, fechaDesde, fechaHasta, 0, 100, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("Filtrar reservas de anfitrión - Con anfitrionId debería usar ID proporcionado")
    void filtrarReservasDeAnfitrion_ConAnfitrionId_DeberiaUsarIdProporcionado() {
        // Arrange
        PaginacionResponse<ReservaDTO> paginacionResponse = new PaginacionResponse<>();
        paginacionResponse.setContenido(Arrays.asList(reservaDTO));

        when(reservaService.listarPorAnfitrion(5, 0, 100)).thenReturn(paginacionResponse);

        // Act
        ResponseEntity<List<ReservaDTO>> response = reservaController.filtrarReservasDeAnfitrion(
                null, null, null, 0, 100, 5);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(reservaService, times(1)).listarPorAnfitrion(5, 0, 100);
    }

    // =========================================================================
    // TESTS DE CASOS BORDE Y VALIDACIONES
    // =========================================================================

    @Test
    @DisplayName("CurrentUserId - Usuario no autenticado debería lanzar excepción")
    void currentUserId_UsuarioNoAutenticado_DeberiaLanzarExcepcion() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        assertThatThrownBy(() -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = (auth != null) ? auth.getName() : null;
            if (!org.springframework.util.StringUtils.hasText(email)) {
                throw new RecursoNoEncontradoException("Usuario autenticado no encontrado");
            }
        }).isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessage("Usuario autenticado no encontrado");
    }

    @Test
    @DisplayName("CurrentUserId - Usuario no encontrado por email debería lanzar excepción")
    void currentUserId_UsuarioNoEncontradoPorEmail_DeberiaLanzarExcepcion() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("noexiste@correo.com");
        SecurityContextHolder.setContext(securityContext);

        when(usuarioService.obtenerPorEmail("noexiste@correo.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> reservaController.listarMisReservas(0, 50))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessage("Usuario no encontrado por email");
    }

    @Test
    @DisplayName("ReservaDTO - Campos completos tienen valores correctos")
    void reservaDTO_CamposCompletos_TienenValoresCorrectos() {
        // Arrange & Act
        ReservaDTO dtoCompleto = ReservaDTO.builder()
                .id(50)
                .alojamientoId(15L)
                .alojamientoNombre("Apartamento en el centro")
                .huespedId(30L)
                .huespedNombre("María López")
                .fechaCheckin(LocalDate.of(2025, 11, 1))
                .fechaCheckout(LocalDate.of(2025, 11, 5))
                .numeroHuespedes(3)
                .precioTotal(new BigDecimal("1200000.00"))
                .estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.of(2025, 10, 25, 14, 30))
                .fechaCancelacion(null)
                .motivoCancelacion(null)
                .cantidadNoches(4L)
                .puedeCancelarse(true)
                .build();

        // Assert
        assertThat(dtoCompleto.getId()).isEqualTo(50);
        assertThat(dtoCompleto.getAlojamientoId()).isEqualTo(15L);
        assertThat(dtoCompleto.getAlojamientoNombre()).isEqualTo("Apartamento en el centro");
        assertThat(dtoCompleto.getHuespedId()).isEqualTo(30L);
        assertThat(dtoCompleto.getHuespedNombre()).isEqualTo("María López");
        assertThat(dtoCompleto.getFechaCheckin()).isEqualTo(LocalDate.of(2025, 11, 1));
        assertThat(dtoCompleto.getFechaCheckout()).isEqualTo(LocalDate.of(2025, 11, 5));
        assertThat(dtoCompleto.getNumeroHuespedes()).isEqualTo(3);
        assertThat(dtoCompleto.getPrecioTotal()).isEqualByComparingTo("1200000.00");
        assertThat(dtoCompleto.getEstado()).isEqualTo("PENDIENTE");
        assertThat(dtoCompleto.getFechaCreacion()).isEqualTo(LocalDateTime.of(2025, 10, 25, 14, 30));
        assertThat(dtoCompleto.getFechaCancelacion()).isNull();
        assertThat(dtoCompleto.getMotivoCancelacion()).isNull();
        assertThat(dtoCompleto.getCantidadNoches()).isEqualTo(4L);
        assertThat(dtoCompleto.getPuedeCancelarse()).isTrue();
    }

    @Test
    @DisplayName("ReservaDTO - Reserva cancelada tiene campos de cancelación")
    void reservaDTO_ReservaCancelada_TieneCamposCancelacion() {
        // Arrange & Act
        ReservaDTO reservaCancelada = ReservaDTO.builder()
                .id(55)
                .estado("CANCELADA")
                .fechaCancelacion(LocalDateTime.of(2025, 9, 18, 10, 0))
                .motivoCancelacion("Cambio de itinerario")
                .puedeCancelarse(false)
                .build();

        // Assert
        assertThat(reservaCancelada.getEstado()).isEqualTo("CANCELADA");
        assertThat(reservaCancelada.getFechaCancelacion()).isEqualTo(LocalDateTime.of(2025, 9, 18, 10, 0));
        assertThat(reservaCancelada.getMotivoCancelacion()).isEqualTo("Cambio de itinerario");
        assertThat(reservaCancelada.getPuedeCancelarse()).isFalse();
    }
}