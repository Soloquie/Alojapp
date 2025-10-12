package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.negocio.Service.PagoService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para PagoController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PagoController - Unit Tests")
public class PagoControllerTest {

    @Mock
    private PagoService pagoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PagoController pagoController;

    private PagoDTO pagoDTO;
    private Usuario usuario;
    private PagoController.PagoRequest pagoRequest;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        pagoDTO = PagoDTO.builder()
                .id(501)
                .reservaId("25")
                .usuarioEmail("usuario@correo.com")
                .monto(1750000.0)
                .metodo("Tarjeta de crédito")
                .metodoPago("Tarjeta")
                .fechaPago(LocalDateTime.of(2025, 9, 18, 15, 30))
                .estado("Completado")
                .referenciaTransaccion("TXN-98456231")
                .build();

        usuario = Usuario.builder()
                .id(1)
                .nombre("Juan Pérez")
                .email("juan@correo.com")
                .build();

        pagoRequest = new PagoController.PagoRequest();
        pagoRequest.reservaId = 50;
        pagoRequest.metodoPago = "TARJETA_CREDITO";
        pagoRequest.monto = 700000.0;
    }

    // =========================================================================
    // TESTS PARA REGISTRAR PAGO
    // =========================================================================

    @Test
    @DisplayName("Registrar pago - Debería retornar 201 CREATED")
    void registrarPago_DeberiaRetornar201Created() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(pagoService.registrarPago(eq(1), eq(50), eq("TARJETA_CREDITO"), eq(700000.0), any(LocalDateTime.class)))
                    .thenReturn(pagoDTO);

            // Act
            ResponseEntity<PagoDTO> response = pagoController.registrarPago(pagoRequest);

            // Assert
            assertThat(response.getStatusCodeValue()).isEqualTo(201);
            assertThat(response.getBody()).isEqualTo(pagoDTO);
            assertThat(response.getBody().getId()).isEqualTo(501);
            assertThat(response.getBody().getReservaId()).isEqualTo("25");
            assertThat(response.getBody().getUsuarioEmail()).isEqualTo("usuario@correo.com");
            assertThat(response.getBody().getMonto()).isEqualTo(1750000.0);
            assertThat(response.getBody().getMetodo()).isEqualTo("Tarjeta de crédito");
            assertThat(response.getBody().getEstado()).isEqualTo("Completado");

            verify(pagoService, times(1)).registrarPago(eq(1), eq(50), eq("TARJETA_CREDITO"), eq(700000.0), any(LocalDateTime.class));
        }
    }

    @Test
    @DisplayName("Registrar pago - Usuario no autenticado debería lanzar excepción")
    void registrarPago_UsuarioNoAutenticado_DeberiaLanzarExcepcion() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.empty());

            // Mock SecurityContextHolder como fallback
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(null);
            SecurityContextHolder.setContext(securityContext);

            // Act & Assert
            assertThatThrownBy(() -> pagoController.registrarPago(pagoRequest))
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario autenticado no encontrado");

            verifyNoInteractions(pagoService);
        }
    }

    // =========================================================================
    // TESTS PARA OBTENER PAGO POR ID
    // =========================================================================

    @Test
    @DisplayName("Obtener pago por ID - Debería retornar 200 OK")
    void obtenerPago_DeberiaRetornar200Ok() {
        // Arrange
        Integer pagoId = 501;
        when(pagoService.obtenerPorId(pagoId)).thenReturn(pagoDTO);

        // Act
        ResponseEntity<PagoDTO> response = pagoController.obtenerPago(pagoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagoDTO);
        assertThat(response.getBody().getEstado()).isEqualTo("Completado");
        assertThat(response.getBody().getReferenciaTransaccion()).isEqualTo("TXN-98456231");

        verify(pagoService, times(1)).obtenerPorId(pagoId);
    }

    // =========================================================================
    // TESTS PARA OBTENER PAGO POR RESERVA
    // =========================================================================

    @Test
    @DisplayName("Obtener pago por reserva - Debería retornar 200 OK")
    void obtenerPagoPorReserva_DeberiaRetornar200Ok() {
        // Arrange
        Integer reservaId = 50;
        when(pagoService.obtenerPorReserva(reservaId)).thenReturn(pagoDTO);

        // Act
        ResponseEntity<PagoDTO> response = pagoController.obtenerPagoPorReserva(reservaId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagoDTO);
        assertThat(response.getBody().getReservaId()).isEqualTo("25");

        verify(pagoService, times(1)).obtenerPorReserva(reservaId);
    }

    // =========================================================================
    // TESTS PARA ACTUALIZAR ESTADO DE PAGO
    // =========================================================================

    @Test
    @DisplayName("Actualizar estado de pago - Debería retornar 200 OK")
    void actualizarEstadoPago_DeberiaRetornar200Ok() {
        // Arrange
        Integer pagoId = 501;
        String nuevoEstado = "APROBADO";
        PagoDTO pagoActualizado = PagoDTO.builder()
                .id(501)
                .estado("APROBADO")
                .build();
        when(pagoService.actualizarEstado(pagoId, nuevoEstado)).thenReturn(pagoActualizado);

        // Act
        ResponseEntity<PagoDTO> response = pagoController.actualizarEstadoPago(pagoId, nuevoEstado);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagoActualizado);
        assertThat(response.getBody().getEstado()).isEqualTo("APROBADO");

        verify(pagoService, times(1)).actualizarEstado(pagoId, nuevoEstado);
    }

    @Test
    @DisplayName("Actualizar estado de pago - Diferentes estados deberían procesarse correctamente")
    void actualizarEstadoPago_DiferentesEstados_DeberianProcesarseCorrectamente() {
        // Arrange
        Integer pagoId = 501;
        PagoDTO pagoAprobado = PagoDTO.builder().estado("APROBADO").build();
        PagoDTO pagoRechazado = PagoDTO.builder().estado("RECHAZADO").build();

        when(pagoService.actualizarEstado(pagoId, "APROBADO")).thenReturn(pagoAprobado);
        when(pagoService.actualizarEstado(pagoId, "RECHAZADO")).thenReturn(pagoRechazado);

        // Act
        ResponseEntity<PagoDTO> response1 = pagoController.actualizarEstadoPago(pagoId, "APROBADO");
        ResponseEntity<PagoDTO> response2 = pagoController.actualizarEstadoPago(pagoId, "RECHAZADO");

        // Assert
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody().getEstado()).isEqualTo("APROBADO");
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody().getEstado()).isEqualTo("RECHAZADO");

        verify(pagoService, times(1)).actualizarEstado(pagoId, "APROBADO");
        verify(pagoService, times(1)).actualizarEstado(pagoId, "RECHAZADO");
    }

    // =========================================================================
    // TESTS PARA LISTAR MIS PAGOS
    // =========================================================================

    @Test
    @DisplayName("Listar mis pagos - Debería retornar 200 OK")
    void listarMisPagos_DeberiaRetornar200Ok() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            List<PagoDTO> pagos = Arrays.asList(pagoDTO);
            when(pagoService.listarPorUsuario(1)).thenReturn(pagos);

            // Act
            ResponseEntity<List<PagoDTO>> response = pagoController.listarMisPagos();

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(pagos);
            assertThat(response.getBody()).hasSize(1);
            assertThat(response.getBody().get(0).getId()).isEqualTo(501);

            verify(pagoService, times(1)).listarPorUsuario(1);
        }
    }

    // =========================================================================
    // TESTS PARA MI PAGO POR RESERVA
    // =========================================================================

    @Test
    @DisplayName("Mi pago por reserva - Debería retornar 200 OK")
    void miPagoPorReserva_DeberiaRetornar200Ok() {
        // Arrange
        Integer reservaId = 50;
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(pagoService.obtenerDeUsuarioPorReserva(1, reservaId)).thenReturn(pagoDTO);

            // Act
            ResponseEntity<PagoDTO> response = pagoController.miPagoPorReserva(reservaId);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(pagoDTO);
            assertThat(response.getBody().getReservaId()).isEqualTo("25");

            verify(pagoService, times(1)).obtenerDeUsuarioPorReserva(1, reservaId);
        }
    }

    // =========================================================================
    // TESTS PARA TOTAL PAGADO POR MÍ
    // =========================================================================

    @Test
    @DisplayName("Total pagado por mí - Con datos debería retornar 200 OK")
    void totalPagadoPorMi_ConDatos_DeberiaRetornar200Ok() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            BigDecimal total = new BigDecimal("1500000.00");
            when(pagoService.totalPagadoPorUsuario(1)).thenReturn(total);

            // Act
            ResponseEntity<BigDecimal> response = pagoController.totalPagadoPorMi();

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualByComparingTo("1500000.00");

            verify(pagoService, times(1)).totalPagadoPorUsuario(1);
        }
    }

    @Test
    @DisplayName("Total pagado por mí - Sin datos debería retornar 200 OK con cero")
    void totalPagadoPorMi_SinDatos_DeberiaRetornar200OkConCero() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            when(pagoService.totalPagadoPorUsuario(1)).thenReturn(null);

            // Act
            ResponseEntity<BigDecimal> response = pagoController.totalPagadoPorMi();

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualByComparingTo(BigDecimal.ZERO);

            verify(pagoService, times(1)).totalPagadoPorUsuario(1);
        }
    }

    // =========================================================================
    // TESTS DE CASOS BORDE Y VALIDACIONES
    // =========================================================================

    @Test
    @DisplayName("CurrentUserId - Usuario no encontrado en BD debería lanzar excepción")
    void currentUserId_UsuarioNoEncontradoEnBD_DeberiaLanzarExcepcion() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("noexiste@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("noexiste@correo.com")).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> pagoController.listarMisPagos())
                    .isInstanceOf(RecursoNoEncontradoException.class)
                    .hasMessage("Usuario no encontrado por email");
        }
    }

    @Test
    @DisplayName("PagoDTO - Campos completos tienen valores correctos")
    void pagoDTO_CamposCompletos_TienenValoresCorrectos() {
        // Arrange & Act
        PagoDTO dtoCompleto = PagoDTO.builder()
                .id(502)
                .reservaId("30")
                .usuarioEmail("maria@correo.com")
                .monto(2000000.0)
                .metodo("Transferencia bancaria")
                .metodoPago("Transferencia")
                .fechaPago(LocalDateTime.of(2025, 10, 20, 10, 15))
                .estado("Pendiente")
                .referenciaTransaccion("TXN-78451296")
                .build();

        // Assert
        assertThat(dtoCompleto.getId()).isEqualTo(502);
        assertThat(dtoCompleto.getReservaId()).isEqualTo("30");
        assertThat(dtoCompleto.getUsuarioEmail()).isEqualTo("maria@correo.com");
        assertThat(dtoCompleto.getMonto()).isEqualTo(2000000.0);
        assertThat(dtoCompleto.getMetodo()).isEqualTo("Transferencia bancaria");
        assertThat(dtoCompleto.getMetodoPago()).isEqualTo("Transferencia");
        assertThat(dtoCompleto.getFechaPago()).isEqualTo(LocalDateTime.of(2025, 10, 20, 10, 15));
        assertThat(dtoCompleto.getEstado()).isEqualTo("Pendiente");
        assertThat(dtoCompleto.getReferenciaTransaccion()).isEqualTo("TXN-78451296");
    }

    @Test
    @DisplayName("PagoDTO - Builder funciona correctamente")
    void pagoDTO_Builder_FuncionaCorrectamente() {
        // Arrange & Act
        PagoDTO dto = PagoDTO.builder()
                .id(503)
                .reservaId("35")
                .usuarioEmail("carlos@correo.com")
                .monto(1500000.0)
                .metodo("Efectivo")
                .metodoPago("Efectivo")
                .estado("Completado")
                .build();

        // Assert
        assertThat(dto.getId()).isEqualTo(503);
        assertThat(dto.getReservaId()).isEqualTo("35");
        assertThat(dto.getUsuarioEmail()).isEqualTo("carlos@correo.com");
        assertThat(dto.getMonto()).isEqualTo(1500000.0);
        assertThat(dto.getMetodo()).isEqualTo("Efectivo");
        assertThat(dto.getMetodoPago()).isEqualTo("Efectivo");
        assertThat(dto.getEstado()).isEqualTo("Completado");
    }

    @Test
    @DisplayName("PagoRequest - Campos tienen valores correctos")
    void pagoRequest_Campos_TienenValoresCorrectos() {
        // Arrange & Act
        PagoController.PagoRequest request = new PagoController.PagoRequest();
        request.reservaId = 100;
        request.metodoPago = "EFECTIVO";
        request.monto = 300000.0;

        // Assert
        assertThat(request.reservaId).isEqualTo(100);
        assertThat(request.metodoPago).isEqualTo("EFECTIVO");
        assertThat(request.monto).isEqualTo(300000.0);
    }

    @Test
    @DisplayName("Múltiples endpoints autenticados - Verificar consistencia")
    void multiplesEndpointsAutenticados_DeberianUsarMismoUsuario() {
        // Arrange
        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));

            when(pagoService.listarPorUsuario(1)).thenReturn(Arrays.asList(pagoDTO));
            when(pagoService.totalPagadoPorUsuario(1)).thenReturn(new BigDecimal("1000000.00"));

            // Act
            pagoController.listarMisPagos();
            pagoController.totalPagadoPorMi();

            // Assert
            verify(pagoService, times(1)).listarPorUsuario(1);
            verify(pagoService, times(1)).totalPagadoPorUsuario(1);
            verify(usuarioRepository, times(2)).findByEmailIgnoreCase("juan@correo.com");
        }
    }

    @Test
    @DisplayName("Registrar pago - Diferentes métodos de pago deberían procesarse correctamente")
    void registrarPago_DiferentesMetodosPago_DeberianProcesarseCorrectamente() {
        // Arrange
        PagoController.PagoRequest requestEfectivo = new PagoController.PagoRequest();
        requestEfectivo.reservaId = 60;
        requestEfectivo.metodoPago = "EFECTIVO";
        requestEfectivo.monto = 400000.0;

        PagoController.PagoRequest requestTransferencia = new PagoController.PagoRequest();
        requestTransferencia.reservaId = 70;
        requestTransferencia.metodoPago = "TRANSFERENCIA";
        requestTransferencia.monto = 600000.0;

        try (MockedStatic<SecurityUtils> securityUtils = mockStatic(SecurityUtils.class)) {
            securityUtils.when(() -> SecurityUtils.getEmailActual()).thenReturn(Optional.of("juan@correo.com"));
            when(usuarioRepository.findByEmailIgnoreCase("juan@correo.com")).thenReturn(Optional.of(usuario));
            when(pagoService.registrarPago(anyInt(), anyInt(), anyString(), anyDouble(), any(LocalDateTime.class)))
                    .thenReturn(pagoDTO);

            // Act
            pagoController.registrarPago(requestEfectivo);
            pagoController.registrarPago(requestTransferencia);

            // Assert
            verify(pagoService, times(1)).registrarPago(eq(1), eq(60), eq("EFECTIVO"), eq(400000.0), any(LocalDateTime.class));
            verify(pagoService, times(1)).registrarPago(eq(1), eq(70), eq("TRANSFERENCIA"), eq(600000.0), any(LocalDateTime.class));
        }
    }
}