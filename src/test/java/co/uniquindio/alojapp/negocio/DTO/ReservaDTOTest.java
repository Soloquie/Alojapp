package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ReservaDTO
 */
@DisplayName("ReservaDTO - Unit Tests")
public class ReservaDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 45;
    private final Long ALOJAMIENTO_ID_VALIDO = 10L;
    private final String ALOJAMIENTO_NOMBRE_VALIDO = "Casa de playa en Cartagena";
    private final Long HUESPED_ID_VALIDO = 25L;
    private final String HUESPED_NOMBRE_VALIDO = "Juan Pérez";
    private final LocalDate FECHA_CHECKIN_VALIDA = LocalDate.of(2025, 9, 20);
    private final LocalDate FECHA_CHECKOUT_VALIDA = LocalDate.of(2025, 9, 25);
    private final Integer NUMERO_HUESPEDES_VALIDO = 4;
    private final BigDecimal PRECIO_TOTAL_VALIDO = new BigDecimal("1750000.00");
    private final String ESTADO_VALIDO = "CONFIRMADA";
    private final LocalDateTime FECHA_CREACION_VALIDA = LocalDateTime.of(2025, 9, 15, 10, 30);
    private final LocalDateTime FECHA_CANCELACION_VALIDA = LocalDateTime.of(2025, 9, 16, 14, 15);
    private final String MOTIVO_CANCELACION_VALIDO = "Cambio de planes del huésped";
    private final Long CANTIDAD_NOCHES_VALIDO = 5L;
    private final Boolean PUEDE_CANCELARSE_VALIDO = true;

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .alojamientoNombre(ALOJAMIENTO_NOMBRE_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .huespedNombre(HUESPED_NOMBRE_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .numeroHuespedes(NUMERO_HUESPEDES_VALIDO)
                .precioTotal(PRECIO_TOTAL_VALIDO)
                .estado(ESTADO_VALIDO)
                .fechaCreacion(FECHA_CREACION_VALIDA)
                .fechaCancelacion(FECHA_CANCELACION_VALIDA)
                .motivoCancelacion(MOTIVO_CANCELACION_VALIDO)
                .cantidadNoches(CANTIDAD_NOCHES_VALIDO)
                .puedeCancelarse(PUEDE_CANCELARSE_VALIDO)
                .build();

        // ASSERT
        assertThat(reserva).isNotNull();
        assertThat(reserva.getId()).isEqualTo(ID_VALIDO);
        assertThat(reserva.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(reserva.getAlojamientoNombre()).isEqualTo(ALOJAMIENTO_NOMBRE_VALIDO);
        assertThat(reserva.getHuespedId()).isEqualTo(HUESPED_ID_VALIDO);
        assertThat(reserva.getHuespedNombre()).isEqualTo(HUESPED_NOMBRE_VALIDO);
        assertThat(reserva.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(reserva.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(reserva.getPrecioTotal()).isEqualTo(PRECIO_TOTAL_VALIDO);
        assertThat(reserva.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(reserva.getFechaCreacion()).isEqualTo(FECHA_CREACION_VALIDA);
        assertThat(reserva.getFechaCancelacion()).isEqualTo(FECHA_CANCELACION_VALIDA);
        assertThat(reserva.getMotivoCancelacion()).isEqualTo(MOTIVO_CANCELACION_VALIDO);
        assertThat(reserva.getCantidadNoches()).isEqualTo(CANTIDAD_NOCHES_VALIDO);
        assertThat(reserva.getPuedeCancelarse()).isEqualTo(PUEDE_CANCELARSE_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .numeroHuespedes(NUMERO_HUESPEDES_VALIDO)
                .precioTotal(PRECIO_TOTAL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT
        assertThat(reserva).isNotNull();
        assertThat(reserva.getId()).isEqualTo(ID_VALIDO);
        assertThat(reserva.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(reserva.getHuespedId()).isEqualTo(HUESPED_ID_VALIDO);
        assertThat(reserva.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(reserva.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(reserva.getPrecioTotal()).isEqualTo(PRECIO_TOTAL_VALIDO);
        assertThat(reserva.getEstado()).isEqualTo(ESTADO_VALIDO);

        // Campos opcionales null
        assertThat(reserva.getAlojamientoNombre()).isNull();
        assertThat(reserva.getHuespedNombre()).isNull();
        assertThat(reserva.getFechaCreacion()).isNull();
        assertThat(reserva.getFechaCancelacion()).isNull();
        assertThat(reserva.getMotivoCancelacion()).isNull();
        assertThat(reserva.getCantidadNoches()).isNull();
        assertThat(reserva.getPuedeCancelarse()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ReservaDTO reserva = new ReservaDTO();

        // ASSERT
        assertThat(reserva).isNotNull();
        assertThat(reserva.getId()).isNull();
        assertThat(reserva.getAlojamientoId()).isNull();
        assertThat(reserva.getAlojamientoNombre()).isNull();
        assertThat(reserva.getHuespedId()).isNull();
        assertThat(reserva.getHuespedNombre()).isNull();
        assertThat(reserva.getFechaCheckin()).isNull();
        assertThat(reserva.getFechaCheckout()).isNull();
        assertThat(reserva.getNumeroHuespedes()).isNull();
        assertThat(reserva.getPrecioTotal()).isNull();
        assertThat(reserva.getEstado()).isNull();
        assertThat(reserva.getFechaCreacion()).isNull();
        assertThat(reserva.getFechaCancelacion()).isNull();
        assertThat(reserva.getMotivoCancelacion()).isNull();
        assertThat(reserva.getCantidadNoches()).isNull();
        assertThat(reserva.getPuedeCancelarse()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        ReservaDTO reserva = new ReservaDTO(
                ID_VALIDO, ALOJAMIENTO_ID_VALIDO, ALOJAMIENTO_NOMBRE_VALIDO,
                HUESPED_ID_VALIDO, HUESPED_NOMBRE_VALIDO, FECHA_CHECKIN_VALIDA,
                FECHA_CHECKOUT_VALIDA, NUMERO_HUESPEDES_VALIDO, PRECIO_TOTAL_VALIDO,
                ESTADO_VALIDO, FECHA_CREACION_VALIDA, FECHA_CANCELACION_VALIDA,
                MOTIVO_CANCELACION_VALIDO, CANTIDAD_NOCHES_VALIDO, PUEDE_CANCELARSE_VALIDO
        );

        // ASSERT
        assertThat(reserva).isNotNull();
        assertThat(reserva.getId()).isEqualTo(ID_VALIDO);
        assertThat(reserva.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(reserva.getAlojamientoNombre()).isEqualTo(ALOJAMIENTO_NOMBRE_VALIDO);
        assertThat(reserva.getHuespedId()).isEqualTo(HUESPED_ID_VALIDO);
        assertThat(reserva.getHuespedNombre()).isEqualTo(HUESPED_NOMBRE_VALIDO);
        assertThat(reserva.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(reserva.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(reserva.getPrecioTotal()).isEqualTo(PRECIO_TOTAL_VALIDO);
        assertThat(reserva.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(reserva.getFechaCreacion()).isEqualTo(FECHA_CREACION_VALIDA);
        assertThat(reserva.getFechaCancelacion()).isEqualTo(FECHA_CANCELACION_VALIDA);
        assertThat(reserva.getMotivoCancelacion()).isEqualTo(MOTIVO_CANCELACION_VALIDO);
        assertThat(reserva.getCantidadNoches()).isEqualTo(CANTIDAD_NOCHES_VALIDO);
        assertThat(reserva.getPuedeCancelarse()).isEqualTo(PUEDE_CANCELARSE_VALIDO);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT
        reserva.setId(ID_VALIDO);
        reserva.setAlojamientoId(ALOJAMIENTO_ID_VALIDO);
        reserva.setAlojamientoNombre(ALOJAMIENTO_NOMBRE_VALIDO);
        reserva.setHuespedId(HUESPED_ID_VALIDO);
        reserva.setHuespedNombre(HUESPED_NOMBRE_VALIDO);
        reserva.setFechaCheckin(FECHA_CHECKIN_VALIDA);
        reserva.setFechaCheckout(FECHA_CHECKOUT_VALIDA);
        reserva.setNumeroHuespedes(NUMERO_HUESPEDES_VALIDO);
        reserva.setPrecioTotal(PRECIO_TOTAL_VALIDO);
        reserva.setEstado(ESTADO_VALIDO);
        reserva.setFechaCreacion(FECHA_CREACION_VALIDA);
        reserva.setFechaCancelacion(FECHA_CANCELACION_VALIDA);
        reserva.setMotivoCancelacion(MOTIVO_CANCELACION_VALIDO);
        reserva.setCantidadNoches(CANTIDAD_NOCHES_VALIDO);
        reserva.setPuedeCancelarse(PUEDE_CANCELARSE_VALIDO);

        // ASSERT
        assertThat(reserva.getId()).isEqualTo(ID_VALIDO);
        assertThat(reserva.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(reserva.getAlojamientoNombre()).isEqualTo(ALOJAMIENTO_NOMBRE_VALIDO);
        assertThat(reserva.getHuespedId()).isEqualTo(HUESPED_ID_VALIDO);
        assertThat(reserva.getHuespedNombre()).isEqualTo(HUESPED_NOMBRE_VALIDO);
        assertThat(reserva.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(reserva.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(reserva.getPrecioTotal()).isEqualTo(PRECIO_TOTAL_VALIDO);
        assertThat(reserva.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(reserva.getFechaCreacion()).isEqualTo(FECHA_CREACION_VALIDA);
        assertThat(reserva.getFechaCancelacion()).isEqualTo(FECHA_CANCELACION_VALIDA);
        assertThat(reserva.getMotivoCancelacion()).isEqualTo(MOTIVO_CANCELACION_VALIDO);
        assertThat(reserva.getCantidadNoches()).isEqualTo(CANTIDAD_NOCHES_VALIDO);
        assertThat(reserva.getPuedeCancelarse()).isEqualTo(PUEDE_CANCELARSE_VALIDO);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ReservaDTO reserva1 = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .estado(ESTADO_VALIDO)
                .build();

        ReservaDTO reserva2 = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .estado(ESTADO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(reserva1).isEqualTo(reserva2);
        assertThat(reserva1.hashCode()).isEqualTo(reserva2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        ReservaDTO reserva1 = ReservaDTO.builder()
                .id(1)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .build();

        ReservaDTO reserva2 = ReservaDTO.builder()
                .id(2)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(reserva1).isNotEqualTo(reserva2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante de la reserva")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ReservaDTO reserva = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoNombre(ALOJAMIENTO_NOMBRE_VALIDO)
                .huespedNombre(HUESPED_NOMBRE_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .precioTotal(PRECIO_TOTAL_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ACT
        String resultado = reserva.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(ALOJAMIENTO_NOMBRE_VALIDO);
        assertThat(resultado).contains(HUESPED_NOMBRE_VALIDO);
        assertThat(resultado).contains(ESTADO_VALIDO);
        assertThat(resultado).contains(PRECIO_TOTAL_VALIDO.toString());
    }

    // ==================== FECHAS TESTS ====================

    @Test
    @DisplayName("FECHAS - Validación de fechas checkin y checkout")
    void fechas_ValidacionDeFechasCheckinYCheckout() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .build();

        // ASSERT - Checkout debe ser después de checkin
        assertThat(reserva.getFechaCheckout()).isAfter(reserva.getFechaCheckin());
        assertThat(reserva.getFechaCheckin()).isBefore(reserva.getFechaCheckout());
    }

    @Test
    @DisplayName("FECHAS - Fechas en diferentes momentos funcionan correctamente")
    void fechas_FechasEnDiferentesMomentos_FuncionanCorrectamente() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        LocalDate checkinPasado = LocalDate.of(2024, 1, 1);
        LocalDate checkoutFuturo = LocalDate.of(2026, 12, 31);
        LocalDateTime creacionReciente = LocalDateTime.now().minusHours(1);

        // ACT & ASSERT
        reserva.setFechaCheckin(checkinPasado);
        reserva.setFechaCheckout(checkoutFuturo);
        reserva.setFechaCreacion(creacionReciente);

        assertThat(reserva.getFechaCheckin()).isEqualTo(checkinPasado);
        assertThat(reserva.getFechaCheckout()).isEqualTo(checkoutFuturo);
        assertThat(reserva.getFechaCreacion()).isEqualTo(creacionReciente);
    }

    // ==================== PRECIO TESTS ====================

    @Test
    @DisplayName("PRECIO - Diferentes valores de precio se manejan correctamente")
    void precio_DiferentesValoresDePrecio_SeManejanCorrectamente() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT & ASSERT
        reserva.setPrecioTotal(new BigDecimal("0.00"));
        assertThat(reserva.getPrecioTotal()).isEqualByComparingTo("0.00");

        reserva.setPrecioTotal(new BigDecimal("500000.50"));
        assertThat(reserva.getPrecioTotal()).isEqualByComparingTo("500000.50");

        reserva.setPrecioTotal(new BigDecimal("10000000.00"));
        assertThat(reserva.getPrecioTotal()).isEqualByComparingTo("10000000.00");
    }

    // ==================== ESTADO TESTS ====================

    @Test
    @DisplayName("ESTADO - Diferentes estados de reserva son aceptados")
    void estado_DiferentesEstadosDeReserva_SonAceptados() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT & ASSERT
        reserva.setEstado("PENDIENTE");
        assertThat(reserva.getEstado()).isEqualTo("PENDIENTE");

        reserva.setEstado("CONFIRMADA");
        assertThat(reserva.getEstado()).isEqualTo("CONFIRMADA");

        reserva.setEstado("CANCELADA");
        assertThat(reserva.getEstado()).isEqualTo("CANCELADA");

        reserva.setEstado("COMPLETADA");
        assertThat(reserva.getEstado()).isEqualTo("COMPLETADA");

        reserva.setEstado("EN_CURSO");
        assertThat(reserva.getEstado()).isEqualTo("EN_CURSO");
    }

    // ==================== NÚMERO HUÉSPEDES TESTS ====================

    @Test
    @DisplayName("NÚMERO HUÉSPEDES - Diferentes cantidades de huéspedes son aceptadas")
    void numeroHuespedes_DiferentesCantidadesDeHuespedes_SonAceptadas() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT & ASSERT
        reserva.setNumeroHuespedes(1);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(1);

        reserva.setNumeroHuespedes(2);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(2);

        reserva.setNumeroHuespedes(10);
        assertThat(reserva.getNumeroHuespedes()).isEqualTo(10);

        reserva.setNumeroHuespedes(0);
        assertThat(reserva.getNumeroHuespedes()).isZero();
    }

    // ==================== CANTIDAD NOCHES TESTS ====================

    @Test
    @DisplayName("CANTIDAD NOCHES - Diferentes cantidades de noches son aceptadas")
    void cantidadNoches_DiferentesCantidadesDeNoches_SonAceptadas() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT & ASSERT
        reserva.setCantidadNoches(1L);
        assertThat(reserva.getCantidadNoches()).isEqualTo(1L);

        reserva.setCantidadNoches(7L);
        assertThat(reserva.getCantidadNoches()).isEqualTo(7L);

        reserva.setCantidadNoches(30L);
        assertThat(reserva.getCantidadNoches()).isEqualTo(30L);

        reserva.setCantidadNoches(0L);
        assertThat(reserva.getCantidadNoches()).isZero();
    }

    // ==================== PUEDE CANCELARSE TESTS ====================

    @Test
    @DisplayName("PUEDE CANCELARSE - Valores booleanos se manejan correctamente")
    void puedeCancelarse_ValoresBooleanos_SeManejanCorrectamente() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT & ASSERT
        reserva.setPuedeCancelarse(true);
        assertThat(reserva.getPuedeCancelarse()).isTrue();

        reserva.setPuedeCancelarse(false);
        assertThat(reserva.getPuedeCancelarse()).isFalse();

        reserva.setPuedeCancelarse(null);
        assertThat(reserva.getPuedeCancelarse()).isNull();
    }

    // ==================== CANCELACIÓN TESTS ====================

    @Test
    @DisplayName("CANCELACIÓN - Campos de cancelación se manejan correctamente")
    void cancelacion_CamposDeCancelacion_SeManejanCorrectamente() {
        // ARRANGE
        ReservaDTO reserva = new ReservaDTO();

        // ACT
        reserva.setFechaCancelacion(FECHA_CANCELACION_VALIDA);
        reserva.setMotivoCancelacion(MOTIVO_CANCELACION_VALIDO);

        // ASSERT
        assertThat(reserva.getFechaCancelacion()).isEqualTo(FECHA_CANCELACION_VALIDA);
        assertThat(reserva.getMotivoCancelacion()).isEqualTo(MOTIVO_CANCELACION_VALIDO);
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Reserva con mismo día checkin y checkout")
    void casoBorde_ReservaConMismoDiaCheckinYCheckout() {
        // ARRANGE & ACT
        LocalDate mismoDia = LocalDate.of(2025, 9, 20);
        ReservaDTO reserva = ReservaDTO.builder()
                .fechaCheckin(mismoDia)
                .fechaCheckout(mismoDia)
                .cantidadNoches(0L)
                .build();

        // ASSERT
        assertThat(reserva.getFechaCheckin()).isEqualTo(reserva.getFechaCheckout());
        assertThat(reserva.getCantidadNoches()).isZero();
    }

    @Test
    @DisplayName("CASO BORDE - Reserva con precio cero")
    void casoBorde_ReservaConPrecioCero() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .precioTotal(BigDecimal.ZERO)
                .estado("CONFIRMADA")
                .build();

        // ASSERT
        assertThat(reserva.getPrecioTotal()).isZero();
        assertThat(reserva.getEstado()).isEqualTo("CONFIRMADA");
    }

    @Test
    @DisplayName("CASO BORDE - Reserva cancelada sin motivo")
    void casoBorde_ReservaCanceladaSinMotivo() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .estado("CANCELADA")
                .fechaCancelacion(LocalDateTime.now())
                .motivoCancelacion(null)
                .build();

        // ASSERT
        assertThat(reserva.getEstado()).isEqualTo("CANCELADA");
        assertThat(reserva.getFechaCancelacion()).isNotNull();
        assertThat(reserva.getMotivoCancelacion()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Reserva confirmada para vacaciones")
    void scenarioUsoReal_ReservaConfirmadaParaVacaciones() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
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
                .fechaCreacion(LocalDateTime.of(2025, 9, 15, 10, 30))
                .cantidadNoches(5L)
                .puedeCancelarse(true)
                .build();

        // ASSERT
        assertThat(reserva.getEstado()).isEqualTo("CONFIRMADA");
        assertThat(reserva.getCantidadNoches()).isEqualTo(5L);
        assertThat(reserva.getPuedeCancelarse()).isTrue();
        assertThat(reserva.getPrecioTotal()).isEqualByComparingTo("1750000.00");
        assertThat(reserva.getFechaCheckout()).isAfter(reserva.getFechaCheckin());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Reserva cancelada por huésped")
    void scenarioUsoReal_ReservaCanceladaPorHuesped() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .id(46)
                .alojamientoId(12L)
                .alojamientoNombre("Apartamento en el centro")
                .huespedId(30L)
                .huespedNombre("María García")
                .fechaCheckin(LocalDate.of(2025, 10, 1))
                .fechaCheckout(LocalDate.of(2025, 10, 5))
                .numeroHuespedes(2)
                .precioTotal(new BigDecimal("800000.00"))
                .estado("CANCELADA")
                .fechaCreacion(LocalDateTime.of(2025, 9, 10, 14, 20))
                .fechaCancelacion(LocalDateTime.of(2025, 9, 12, 9, 15))
                .motivoCancelacion("Cambio de planes del huésped")
                .cantidadNoches(4L)
                .puedeCancelarse(false)
                .build();

        // ASSERT
        assertThat(reserva.getEstado()).isEqualTo("CANCELADA");
        assertThat(reserva.getMotivoCancelacion()).isEqualTo("Cambio de planes del huésped");
        assertThat(reserva.getFechaCancelacion()).isAfter(reserva.getFechaCreacion());
        assertThat(reserva.getPuedeCancelarse()).isFalse(); // Ya cancelada, no se puede cancelar again
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Reserva en curso")
    void scenarioUsoReal_ReservaEnCurso() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .id(47)
                .alojamientoId(15L)
                .alojamientoNombre("Cabaña en la montaña")
                .huespedId(28L)
                .huespedNombre("Carlos Rodríguez")
                .fechaCheckin(LocalDate.now().minusDays(2))
                .fechaCheckout(LocalDate.now().plusDays(3))
                .numeroHuespedes(3)
                .precioTotal(new BigDecimal("1200000.00"))
                .estado("EN_CURSO")
                .fechaCreacion(LocalDateTime.now().minusDays(10))
                .cantidadNoches(5L)
                .puedeCancelarse(false) // No se puede cancelar una reserva en curso
                .build();

        // ASSERT
        assertThat(reserva.getEstado()).isEqualTo("EN_CURSO");
        assertThat(reserva.getFechaCheckin()).isBefore(LocalDate.now());
        assertThat(reserva.getFechaCheckout()).isAfter(LocalDate.now());
        assertThat(reserva.getPuedeCancelarse()).isFalse();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Reserva completada exitosamente")
    void scenarioUsoReal_ReservaCompletadaExitosamente() {
        // ARRANGE & ACT
        ReservaDTO reserva = ReservaDTO.builder()
                .id(48)
                .alojamientoId(18L)
                .alojamientoNombre("Loft moderno en zona norte")
                .huespedId(32L)
                .huespedNombre("Ana López")
                .fechaCheckin(LocalDate.now().minusDays(7))
                .fechaCheckout(LocalDate.now().minusDays(1))
                .numeroHuespedes(2)
                .precioTotal(new BigDecimal("950000.00"))
                .estado("COMPLETADA")
                .fechaCreacion(LocalDateTime.now().minusDays(14))
                .cantidadNoches(6L)
                .puedeCancelarse(false)
                .build();

        // ASSERT
        assertThat(reserva.getEstado()).isEqualTo("COMPLETADA");
        assertThat(reserva.getFechaCheckout()).isBefore(LocalDate.now());
        assertThat(reserva.getPuedeCancelarse()).isFalse();
        assertThat(reserva.getCantidadNoches()).isEqualTo(6L);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Nueva reserva pendiente")
    void scenarioUsoReal_NuevaReservaPendiente() {
        // ARRANGE & ACT
        ReservaDTO nuevaReserva = ReservaDTO.builder()
                .alojamientoId(20L)
                .alojamientoNombre("Estudio en la playa")
                .huespedId(35L)
                .huespedNombre("Pedro Martínez")
                .fechaCheckin(LocalDate.now().plusDays(5))
                .fechaCheckout(LocalDate.now().plusDays(10))
                .numeroHuespedes(1)
                .precioTotal(new BigDecimal("650000.00"))
                .estado("PENDIENTE")
                .fechaCreacion(LocalDateTime.now())
                .cantidadNoches(5L)
                .puedeCancelarse(true)
                .build();

        // ASSERT - Nueva reserva sin ID
        assertThat(nuevaReserva.getId()).isNull();
        assertThat(nuevaReserva.getEstado()).isEqualTo("PENDIENTE");
        assertThat(nuevaReserva.getPuedeCancelarse()).isTrue();
        assertThat(nuevaReserva.getFechaCheckin()).isAfter(LocalDate.now());
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        ReservaDTO reserva1 = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .estado(ESTADO_VALIDO)
                .build();

        ReservaDTO reserva2 = ReservaDTO.builder()
                .id(ID_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .huespedId(HUESPED_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(reserva1).isEqualTo(reserva2);
        assertThat(reserva1.toString()).isNotNull();
        assertThat(reserva1.hashCode()).isEqualTo(reserva2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ReservaDTO reservaVacia = new ReservaDTO();
        assertThat(reservaVacia).isNotNull();

        // Verificar que el builder funciona
        assertThat(reserva1).isInstanceOf(ReservaDTO.class);
    }
}