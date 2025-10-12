package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para PagoDTO
 */
@DisplayName("PagoDTO - Unit Tests")
public class PagoDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 501;
    private final String RESERVA_ID_VALIDO = "25";
    private final String USUARIO_EMAIL_VALIDO = "usuario@correo.com";
    private final double MONTO_VALIDO = 1750000.0;
    private final String METODO_VALIDO = "Tarjeta de crédito";
    private final LocalDateTime FECHA_PAGO_VALIDA = LocalDateTime.of(2025, 9, 18, 15, 30);
    private final String ESTADO_VALIDO = "Completado";
    private final String REFERENCIA_TRANSACCION_VALIDA = "TXN-98456231";
    private final String METODO_PAGO_VALIDO = "Tarjeta";

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioEmail(USUARIO_EMAIL_VALIDO)
                .monto(MONTO_VALIDO)
                .metodo(METODO_VALIDO)
                .fechaPago(FECHA_PAGO_VALIDA)
                .estado(ESTADO_VALIDO)
                .referenciaTransaccion(REFERENCIA_TRANSACCION_VALIDA)
                .metodoPago(METODO_PAGO_VALIDO)
                .build();

        // ASSERT
        assertThat(pago).isNotNull();
        assertThat(pago.getId()).isEqualTo(ID_VALIDO);
        assertThat(pago.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(pago.getUsuarioEmail()).isEqualTo(USUARIO_EMAIL_VALIDO);
        assertThat(pago.getMonto()).isEqualTo(MONTO_VALIDO);
        assertThat(pago.getMetodo()).isEqualTo(METODO_VALIDO);
        assertThat(pago.getFechaPago()).isEqualTo(FECHA_PAGO_VALIDA);
        assertThat(pago.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(pago.getReferenciaTransaccion()).isEqualTo(REFERENCIA_TRANSACCION_VALIDA);
        assertThat(pago.getMetodoPago()).isEqualTo(METODO_PAGO_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT
        assertThat(pago).isNotNull();
        assertThat(pago.getId()).isEqualTo(ID_VALIDO);
        assertThat(pago.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(pago.getMonto()).isEqualTo(MONTO_VALIDO);
        assertThat(pago.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(pago.getUsuarioEmail()).isNull();
        assertThat(pago.getMetodo()).isNull();
        assertThat(pago.getFechaPago()).isNull();
        assertThat(pago.getReferenciaTransaccion()).isNull();
        assertThat(pago.getMetodoPago()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        PagoDTO pago = new PagoDTO();

        // ASSERT
        assertThat(pago).isNotNull();
        assertThat(pago.getId()).isNull();
        assertThat(pago.getReservaId()).isNull();
        assertThat(pago.getUsuarioEmail()).isNull();
        assertThat(pago.getMonto()).isEqualTo(0.0); // Valor por defecto para double
        assertThat(pago.getMetodo()).isNull();
        assertThat(pago.getFechaPago()).isNull();
        assertThat(pago.getEstado()).isNull();
        assertThat(pago.getReferenciaTransaccion()).isNull();
        assertThat(pago.getMetodoPago()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        PagoDTO pago = new PagoDTO(
                ID_VALIDO, RESERVA_ID_VALIDO, USUARIO_EMAIL_VALIDO, MONTO_VALIDO,
                METODO_VALIDO, FECHA_PAGO_VALIDA, ESTADO_VALIDO,
                REFERENCIA_TRANSACCION_VALIDA, METODO_PAGO_VALIDO
        );

        // ASSERT
        assertThat(pago).isNotNull();
        assertThat(pago.getId()).isEqualTo(ID_VALIDO);
        assertThat(pago.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(pago.getUsuarioEmail()).isEqualTo(USUARIO_EMAIL_VALIDO);
        assertThat(pago.getMonto()).isEqualTo(MONTO_VALIDO);
        assertThat(pago.getMetodo()).isEqualTo(METODO_VALIDO);
        assertThat(pago.getFechaPago()).isEqualTo(FECHA_PAGO_VALIDA);
        assertThat(pago.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(pago.getReferenciaTransaccion()).isEqualTo(REFERENCIA_TRANSACCION_VALIDA);
        assertThat(pago.getMetodoPago()).isEqualTo(METODO_PAGO_VALIDO);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT
        pago.setId(ID_VALIDO);
        pago.setReservaId(RESERVA_ID_VALIDO);
        pago.setUsuarioEmail(USUARIO_EMAIL_VALIDO);
        pago.setMonto(MONTO_VALIDO);
        pago.setMetodo(METODO_VALIDO);
        pago.setFechaPago(FECHA_PAGO_VALIDA);
        pago.setEstado(ESTADO_VALIDO);
        pago.setReferenciaTransaccion(REFERENCIA_TRANSACCION_VALIDA);
        pago.setMetodoPago(METODO_PAGO_VALIDO);

        // ASSERT
        assertThat(pago.getId()).isEqualTo(ID_VALIDO);
        assertThat(pago.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(pago.getUsuarioEmail()).isEqualTo(USUARIO_EMAIL_VALIDO);
        assertThat(pago.getMonto()).isEqualTo(MONTO_VALIDO);
        assertThat(pago.getMetodo()).isEqualTo(METODO_VALIDO);
        assertThat(pago.getFechaPago()).isEqualTo(FECHA_PAGO_VALIDA);
        assertThat(pago.getEstado()).isEqualTo(ESTADO_VALIDO);
        assertThat(pago.getReferenciaTransaccion()).isEqualTo(REFERENCIA_TRANSACCION_VALIDA);
        assertThat(pago.getMetodoPago()).isEqualTo(METODO_PAGO_VALIDO);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        PagoDTO pago1 = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        PagoDTO pago2 = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(pago1).isEqualTo(pago2);
        assertThat(pago1.hashCode()).isEqualTo(pago2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        PagoDTO pago1 = PagoDTO.builder()
                .id(1)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .build();

        PagoDTO pago2 = PagoDTO.builder()
                .id(2)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(pago1).isNotEqualTo(pago2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del pago")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        PagoDTO pago = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .referenciaTransaccion(REFERENCIA_TRANSACCION_VALIDA)
                .build();

        // ACT
        String resultado = pago.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(RESERVA_ID_VALIDO);
        assertThat(resultado).contains(String.valueOf(MONTO_VALIDO));
        assertThat(resultado).contains(ESTADO_VALIDO);
        assertThat(resultado).contains(REFERENCIA_TRANSACCION_VALIDA);
    }

    // ==================== MONTO TESTS ====================

    @Test
    @DisplayName("MONTO - Diferentes valores de monto se manejan correctamente")
    void monto_DiferentesValoresDeMonto_SeManejanCorrectamente() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT & ASSERT
        pago.setMonto(0.0);
        assertThat(pago.getMonto()).isZero();

        pago.setMonto(1000.50);
        assertThat(pago.getMonto()).isEqualTo(1000.50);

        pago.setMonto(1000000.0);
        assertThat(pago.getMonto()).isEqualTo(1000000.0);

        pago.setMonto(0.01);
        assertThat(pago.getMonto()).isEqualTo(0.01);
    }

    @Test
    @DisplayName("MONTO - Montos negativos se manejan correctamente")
    void monto_MontosNegativos_SeManejanCorrectamente() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT
        pago.setMonto(-1000.0);

        // ASSERT
        assertThat(pago.getMonto()).isEqualTo(-1000.0);
        assertThat(pago.getMonto()).isNegative();
    }

    // ==================== EMAIL TESTS ====================

    @Test
    @DisplayName("EMAIL - Diferentes formatos de email son aceptados")
    void email_DiferentesFormatosDeEmail_SonAceptados() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT & ASSERT
        pago.setUsuarioEmail("usuario@correo.com");
        assertThat(pago.getUsuarioEmail()).isEqualTo("usuario@correo.com");

        pago.setUsuarioEmail("usuario.nombre@correo.co.uk");
        assertThat(pago.getUsuarioEmail()).isEqualTo("usuario.nombre@correo.co.uk");

        pago.setUsuarioEmail("u+alias@correo.com");
        assertThat(pago.getUsuarioEmail()).isEqualTo("u+alias@correo.com");

        pago.setUsuarioEmail("USUARIO@CORREO.COM");
        assertThat(pago.getUsuarioEmail()).isEqualTo("USUARIO@CORREO.COM");
    }

    // ==================== ESTADO TESTS ====================

    @Test
    @DisplayName("ESTADO - Diferentes estados de pago son aceptados")
    void estado_DiferentesEstadosDePago_SonAceptados() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT & ASSERT
        pago.setEstado("Pendiente");
        assertThat(pago.getEstado()).isEqualTo("Pendiente");

        pago.setEstado("Completado");
        assertThat(pago.getEstado()).isEqualTo("Completado");

        pago.setEstado("Fallido");
        assertThat(pago.getEstado()).isEqualTo("Fallido");

        pago.setEstado("Reembolsado");
        assertThat(pago.getEstado()).isEqualTo("Reembolsado");

        pago.setEstado("En proceso");
        assertThat(pago.getEstado()).isEqualTo("En proceso");

        pago.setEstado("Cancelado");
        assertThat(pago.getEstado()).isEqualTo("Cancelado");
    }

    // ==================== MÉTODO PAGO TESTS ====================

    @Test
    @DisplayName("MÉTODO PAGO - Diferentes métodos de pago son aceptados")
    void metodoPago_DiferentesMetodosDePago_SonAceptados() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT & ASSERT
        pago.setMetodoPago("Tarjeta");
        assertThat(pago.getMetodoPago()).isEqualTo("Tarjeta");

        pago.setMetodoPago("Efectivo");
        assertThat(pago.getMetodoPago()).isEqualTo("Efectivo");

        pago.setMetodoPago("Transferencia");
        assertThat(pago.getMetodoPago()).isEqualTo("Transferencia");

        pago.setMetodoPago("PayPal");
        assertThat(pago.getMetodoPago()).isEqualTo("PayPal");

        pago.setMetodoPago("PSE");
        assertThat(pago.getMetodoPago()).isEqualTo("PSE");
    }

    @Test
    @DisplayName("MÉTODO PAGO - Método y metodoPago pueden tener valores diferentes")
    void metodoPago_MetodoYMetodoPago_PuedenTenerValoresDiferentes() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .metodo("Tarjeta de crédito Visa terminada en 1234")
                .metodoPago("Tarjeta")
                .build();

        // ASSERT
        assertThat(pago.getMetodo()).isEqualTo("Tarjeta de crédito Visa terminada en 1234");
        assertThat(pago.getMetodoPago()).isEqualTo("Tarjeta");
        assertThat(pago.getMetodo()).isNotEqualTo(pago.getMetodoPago());
    }

    // ==================== REFERENCIA TRANSACCIÓN TESTS ====================

    @Test
    @DisplayName("REFERENCIA TRANSACCIÓN - Diferentes formatos de referencia son aceptados")
    void referenciaTransaccion_DiferentesFormatosDeReferencia_SonAceptados() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT & ASSERT
        pago.setReferenciaTransaccion("TXN-98456231");
        assertThat(pago.getReferenciaTransaccion()).isEqualTo("TXN-98456231");

        pago.setReferenciaTransaccion("REF-2024-001-ABC");
        assertThat(pago.getReferenciaTransaccion()).isEqualTo("REF-2024-001-ABC");

        pago.setReferenciaTransaccion("PAY-7X8Y9Z0");
        assertThat(pago.getReferenciaTransaccion()).isEqualTo("PAY-7X8Y9Z0");

        pago.setReferenciaTransaccion("1234567890ABCDEF");
        assertThat(pago.getReferenciaTransaccion()).isEqualTo("1234567890ABCDEF");
    }

    // ==================== FECHAS TESTS ====================

    @Test
    @DisplayName("FECHAS - Fechas en diferentes momentos funcionan correctamente")
    void fechas_FechasEnDiferentesMomentos_FuncionanCorrectamente() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        LocalDateTime fechaPasado = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime fechaFuturo = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaReciente = LocalDateTime.now().minusHours(1);

        // ACT & ASSERT
        pago.setFechaPago(fechaPasado);
        assertThat(pago.getFechaPago()).isEqualTo(fechaPasado);

        pago.setFechaPago(fechaFuturo);
        assertThat(pago.getFechaPago()).isEqualTo(fechaFuturo);

        pago.setFechaPago(fechaReciente);
        assertThat(pago.getFechaPago()).isEqualTo(fechaReciente);
    }

    // ==================== RESERVA ID TESTS ====================

    @Test
    @DisplayName("RESERVA ID - Diferentes formatos de ID de reserva son aceptados")
    void reservaId_DiferentesFormatosDeIdDeReserva_SonAceptados() {
        // ARRANGE
        PagoDTO pago = new PagoDTO();

        // ACT & ASSERT
        pago.setReservaId("25");
        assertThat(pago.getReservaId()).isEqualTo("25");

        pago.setReservaId("RES-2024-001");
        assertThat(pago.getReservaId()).isEqualTo("RES-2024-001");

        pago.setReservaId("ABC123XYZ");
        assertThat(pago.getReservaId()).isEqualTo("ABC123XYZ");

        pago.setReservaId("1001");
        assertThat(pago.getReservaId()).isEqualTo("1001");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Todos los campos null excepto ID")
    void casoBorde_TodosLosCamposNullExceptoId() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(ID_VALIDO)
                .build();

        // ASSERT
        assertThat(pago).isNotNull();
        assertThat(pago.getId()).isEqualTo(ID_VALIDO);
        assertThat(pago.getReservaId()).isNull();
        assertThat(pago.getUsuarioEmail()).isNull();
        assertThat(pago.getMonto()).isEqualTo(0.0);
        assertThat(pago.getMetodo()).isNull();
        assertThat(pago.getFechaPago()).isNull();
        assertThat(pago.getEstado()).isNull();
        assertThat(pago.getReferenciaTransaccion()).isNull();
        assertThat(pago.getMetodoPago()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Pago con monto cero")
    void casoBorde_PagoConMontoCero() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(0.0)
                .estado("Pendiente")
                .build();

        // ASSERT
        assertThat(pago.getMonto()).isZero();
        assertThat(pago.getEstado()).isEqualTo("Pendiente");
    }

    @Test
    @DisplayName("CASO BORDE - Pago sin referencia de transacción")
    void casoBorde_PagoSinReferenciaDeTransaccion() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .referenciaTransaccion(null)
                .build();

        // ASSERT
        assertThat(pago.getReferenciaTransaccion()).isNull();
        assertThat(pago.getEstado()).isEqualTo(ESTADO_VALIDO);
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Pago con tarjeta de crédito exitoso")
    void scenarioUsoReal_PagoConTarjetaDeCreditoExitoso() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(501)
                .reservaId("RES-2024-015")
                .usuarioEmail("cliente@correo.com")
                .monto(1750000.0)
                .metodo("Tarjeta de crédito Visa terminada en 1234")
                .fechaPago(LocalDateTime.of(2025, 9, 18, 15, 30))
                .estado("Completado")
                .referenciaTransaccion("TXN-98456231")
                .metodoPago("Tarjeta")
                .build();

        // ASSERT
        assertThat(pago.getEstado()).isEqualTo("Completado");
        assertThat(pago.getMetodoPago()).isEqualTo("Tarjeta");
        assertThat(pago.getMonto()).isEqualTo(1750000.0);
        assertThat(pago.getReferenciaTransaccion()).startsWith("TXN-");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Pago pendiente de transferencia")
    void scenarioUsoReal_PagoPendienteDeTransferencia() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(502)
                .reservaId("RES-2024-016")
                .usuarioEmail("usuario@empresa.com")
                .monto(1200000.0)
                .metodo("Transferencia bancaria - Banco BBVA")
                .fechaPago(LocalDateTime.now())
                .estado("Pendiente")
                .referenciaTransaccion("TRF-78451296")
                .metodoPago("Transferencia")
                .build();

        // ASSERT
        assertThat(pago.getEstado()).isEqualTo("Pendiente");
        assertThat(pago.getMetodoPago()).isEqualTo("Transferencia");
        assertThat(pago.getMonto()).isEqualTo(1200000.0);
        assertThat(pago.getReferenciaTransaccion()).startsWith("TRF-");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Pago en efectivo al llegar")
    void scenarioUsoReal_PagoEnEfectivoAlLlegar() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(503)
                .reservaId("RES-2024-017")
                .usuarioEmail("visitante@correo.com")
                .monto(850000.0)
                .metodo("Efectivo en moneda local")
                .fechaPago(LocalDateTime.now().plusDays(2))
                .estado("Pendiente")
                .referenciaTransaccion("EFC-00124578")
                .metodoPago("Efectivo")
                .build();

        // ASSERT
        assertThat(pago.getEstado()).isEqualTo("Pendiente");
        assertThat(pago.getMetodoPago()).isEqualTo("Efectivo");
        assertThat(pago.getMonto()).isEqualTo(850000.0);
        assertThat(pago.getFechaPago()).isAfter(LocalDateTime.now());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Pago fallido por fondos insuficientes")
    void scenarioUsoReal_PagoFallidoPorFondosInsuficientes() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(504)
                .reservaId("RES-2024-018")
                .usuarioEmail("cliente@correo.com")
                .monto(2000000.0)
                .metodo("Tarjeta de crédito Mastercard terminada en 5678")
                .fechaPago(LocalDateTime.now().minusHours(1))
                .estado("Fallido")
                .referenciaTransaccion("TXN-ERROR-456")
                .metodoPago("Tarjeta")
                .build();

        // ASSERT
        assertThat(pago.getEstado()).isEqualTo("Fallido");
        assertThat(pago.getMetodoPago()).isEqualTo("Tarjeta");
        assertThat(pago.getMonto()).isEqualTo(2000000.0);
        assertThat(pago.getReferenciaTransaccion()).contains("ERROR");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Reembolso de pago")
    void scenarioUsoReal_ReembolsoDePago() {
        // ARRANGE & ACT
        PagoDTO pago = PagoDTO.builder()
                .id(505)
                .reservaId("RES-2024-019")
                .usuarioEmail("usuario@correo.com")
                .monto(950000.0)
                .metodo("Reembolso a tarjeta original")
                .fechaPago(LocalDateTime.now().minusDays(5))
                .estado("Reembolsado")
                .referenciaTransaccion("REFUND-784512")
                .metodoPago("Tarjeta")
                .build();

        // ASSERT
        assertThat(pago.getEstado()).isEqualTo("Reembolsado");
        assertThat(pago.getMetodo()).contains("Reembolso");
        assertThat(pago.getReferenciaTransaccion()).startsWith("REFUND-");
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        PagoDTO pago1 = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        PagoDTO pago2 = PagoDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .monto(MONTO_VALIDO)
                .estado(ESTADO_VALIDO)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(pago1).isEqualTo(pago2);
        assertThat(pago1.toString()).isNotNull();
        assertThat(pago1.hashCode()).isEqualTo(pago2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        PagoDTO pagoVacio = new PagoDTO();
        assertThat(pagoVacio).isNotNull();

        // Verificar que el builder funciona
        assertThat(pago1).isInstanceOf(PagoDTO.class);
    }
}