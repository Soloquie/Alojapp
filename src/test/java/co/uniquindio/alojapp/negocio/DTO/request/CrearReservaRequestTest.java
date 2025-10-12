package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para CrearReservaRequest
 */
@DisplayName("CrearReservaRequest - Unit Tests")
public class CrearReservaRequestTest {

    private final Integer ALOJAMIENTO_ID_VALIDO = 10;
    private final LocalDate FECHA_CHECKIN_VALIDA = LocalDate.now().plusDays(7);
    private final LocalDate FECHA_CHECKOUT_VALIDA = LocalDate.now().plusDays(14);
    private final Integer NUMERO_HUESPEDES_VALIDO = 4;
    private final String METODO_PAGO_VALIDO = "TARJETA_CREDITO";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        CrearReservaRequest request = new CrearReservaRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getAlojamientoId()).isNull();
        assertThat(request.getFechaCheckin()).isNull();
        assertThat(request.getFechaCheckout()).isNull();
        assertThat(request.getNumeroHuespedes()).isNull();
        assertThat(request.getMetodoPago()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        CrearReservaRequest request = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                FECHA_CHECKIN_VALIDA,
                FECHA_CHECKOUT_VALIDA,
                NUMERO_HUESPEDES_VALIDO,
                METODO_PAGO_VALIDO
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(request.getMetodoPago()).isEqualTo(METODO_PAGO_VALIDO);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        CrearReservaRequest request = CrearReservaRequest.builder()
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .numeroHuespedes(NUMERO_HUESPEDES_VALIDO)
                .metodoPago(METODO_PAGO_VALIDO)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(request.getMetodoPago()).isEqualTo(METODO_PAGO_VALIDO);
    }

    @Test
    @DisplayName("Builder - Permite construcción parcial")
    void builder_PermiteConstruccionParcial() {
        // Act
        CrearReservaRequest request = CrearReservaRequest.builder()
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .fechaCheckin(FECHA_CHECKIN_VALIDA)
                .fechaCheckout(FECHA_CHECKOUT_VALIDA)
                .numeroHuespedes(NUMERO_HUESPEDES_VALIDO)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(request.getMetodoPago()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act
        request.setAlojamientoId(ALOJAMIENTO_ID_VALIDO);
        request.setFechaCheckin(FECHA_CHECKIN_VALIDA);
        request.setFechaCheckout(FECHA_CHECKOUT_VALIDA);
        request.setNumeroHuespedes(NUMERO_HUESPEDES_VALIDO);
        request.setMetodoPago(METODO_PAGO_VALIDO);

        // Assert
        assertThat(request.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(request.getFechaCheckin()).isEqualTo(FECHA_CHECKIN_VALIDA);
        assertThat(request.getFechaCheckout()).isEqualTo(FECHA_CHECKOUT_VALIDA);
        assertThat(request.getNumeroHuespedes()).isEqualTo(NUMERO_HUESPEDES_VALIDO);
        assertThat(request.getMetodoPago()).isEqualTo(METODO_PAGO_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        CrearReservaRequest request1 = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                FECHA_CHECKIN_VALIDA,
                FECHA_CHECKOUT_VALIDA,
                NUMERO_HUESPEDES_VALIDO,
                METODO_PAGO_VALIDO
        );
        CrearReservaRequest request2 = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                FECHA_CHECKIN_VALIDA,
                FECHA_CHECKOUT_VALIDA,
                NUMERO_HUESPEDES_VALIDO,
                METODO_PAGO_VALIDO
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getAlojamientoId()).isEqualTo(request2.getAlojamientoId());
        assertThat(request1.getFechaCheckin()).isEqualTo(request2.getFechaCheckin());
        assertThat(request1.getFechaCheckout()).isEqualTo(request2.getFechaCheckout());
        assertThat(request1.getNumeroHuespedes()).isEqualTo(request2.getNumeroHuespedes());
        assertThat(request1.getMetodoPago()).isEqualTo(request2.getMetodoPago());
    }

    @Test
    @DisplayName("Diferente alojamientoId - Campos no son iguales")
    void diferenteAlojamientoId_CamposNoSonIguales() {
        // Arrange
        CrearReservaRequest request1 = new CrearReservaRequest(1, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA, NUMERO_HUESPEDES_VALIDO, METODO_PAGO_VALIDO);
        CrearReservaRequest request2 = new CrearReservaRequest(2, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA, NUMERO_HUESPEDES_VALIDO, METODO_PAGO_VALIDO);

        // Act & Assert
        assertThat(request1.getAlojamientoId()).isNotEqualTo(request2.getAlojamientoId());
    }

    @Test
    @DisplayName("Diferente fechaCheckin - Campos no son iguales")
    void diferenteFechaCheckin_CamposNoSonIguales() {
        // Arrange
        CrearReservaRequest request1 = new CrearReservaRequest(ALOJAMIENTO_ID_VALIDO, LocalDate.now().plusDays(1), FECHA_CHECKOUT_VALIDA, NUMERO_HUESPEDES_VALIDO, METODO_PAGO_VALIDO);
        CrearReservaRequest request2 = new CrearReservaRequest(ALOJAMIENTO_ID_VALIDO, LocalDate.now().plusDays(2), FECHA_CHECKOUT_VALIDA, NUMERO_HUESPEDES_VALIDO, METODO_PAGO_VALIDO);

        // Act & Assert
        assertThat(request1.getFechaCheckin()).isNotEqualTo(request2.getFechaCheckin());
    }

    @Test
    @DisplayName("Diferente numeroHuespedes - Campos no son iguales")
    void diferenteNumeroHuespedes_CamposNoSonIguales() {
        // Arrange
        CrearReservaRequest request1 = new CrearReservaRequest(ALOJAMIENTO_ID_VALIDO, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA, 2, METODO_PAGO_VALIDO);
        CrearReservaRequest request2 = new CrearReservaRequest(ALOJAMIENTO_ID_VALIDO, FECHA_CHECKIN_VALIDA, FECHA_CHECKOUT_VALIDA, 4, METODO_PAGO_VALIDO);

        // Act & Assert
        assertThat(request1.getNumeroHuespedes()).isNotEqualTo(request2.getNumeroHuespedes());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                FECHA_CHECKIN_VALIDA,
                FECHA_CHECKOUT_VALIDA,
                NUMERO_HUESPEDES_VALIDO,
                METODO_PAGO_VALIDO
        );

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("AlojamientoId - Acepta diferentes valores válidos")
    void alojamientoId_AceptaDiferentesValoresValidos() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act & Assert
        request.setAlojamientoId(1);
        assertThat(request.getAlojamientoId()).isEqualTo(1);

        request.setAlojamientoId(1000);
        assertThat(request.getAlojamientoId()).isEqualTo(1000);

        request.setAlojamientoId(null);
        assertThat(request.getAlojamientoId()).isNull();
    }

    @Test
    @DisplayName("FechaCheckin - Acepta fechas presentes y futuras")
    void fechaCheckin_AceptaFechasPresentesYFuturas() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act & Assert - Fecha presente
        LocalDate fechaPresente = LocalDate.now();
        request.setFechaCheckin(fechaPresente);
        assertThat(request.getFechaCheckin()).isEqualTo(fechaPresente);

        // Fecha futura
        LocalDate fechaFutura = LocalDate.now().plusDays(30);
        request.setFechaCheckin(fechaFutura);
        assertThat(request.getFechaCheckin()).isEqualTo(fechaFutura);

        request.setFechaCheckin(null);
        assertThat(request.getFechaCheckin()).isNull();
    }

    @Test
    @DisplayName("FechaCheckout - Acepta fechas futuras")
    void fechaCheckout_AceptaFechasFuturas() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act & Assert - Fecha futura
        LocalDate fechaFutura = LocalDate.now().plusDays(1);
        request.setFechaCheckout(fechaFutura);
        assertThat(request.getFechaCheckout()).isEqualTo(fechaFutura);

        // Fecha más lejana
        LocalDate fechaLejana = LocalDate.now().plusMonths(6);
        request.setFechaCheckout(fechaLejana);
        assertThat(request.getFechaCheckout()).isEqualTo(fechaLejana);

        request.setFechaCheckout(null);
        assertThat(request.getFechaCheckout()).isNull();
    }

    @Test
    @DisplayName("NumeroHuespedes - Acepta valores mayores o iguales a 1")
    void numeroHuespedes_AceptaValoresMayoresOIgualesAUno() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act & Assert - Valor mínimo
        request.setNumeroHuespedes(1);
        assertThat(request.getNumeroHuespedes()).isEqualTo(1);

        // Valor mayor
        request.setNumeroHuespedes(10);
        assertThat(request.getNumeroHuespedes()).isEqualTo(10);

        request.setNumeroHuespedes(null);
        assertThat(request.getNumeroHuespedes()).isNull();
    }

    @Test
    @DisplayName("MetodoPago - Acepta diferentes valores")
    void metodoPago_AceptaDiferentesValores() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act & Assert
        request.setMetodoPago("TARJETA_CREDITO");
        assertThat(request.getMetodoPago()).isEqualTo("TARJETA_CREDITO");

        request.setMetodoPago("TARJETA_DEBITO");
        assertThat(request.getMetodoPago()).isEqualTo("TARJETA_DEBITO");

        request.setMetodoPago("EFECTIVO");
        assertThat(request.getMetodoPago()).isEqualTo("EFECTIVO");

        request.setMetodoPago("");
        assertThat(request.getMetodoPago()).isEmpty();

        request.setMetodoPago(null);
        assertThat(request.getMetodoPago()).isNull();
    }

    @Test
    @DisplayName("Escenario - Reserva normal para fin de semana")
    void escenario_ReservaNormalParaFinDeSemana() {
        // Act
        CrearReservaRequest request = CrearReservaRequest.builder()
                .alojamientoId(25)
                .fechaCheckin(LocalDate.now().plusDays(5))
                .fechaCheckout(LocalDate.now().plusDays(7))
                .numeroHuespedes(2)
                .metodoPago("TARJETA_CREDITO")
                .build();

        // Assert
        assertThat(request.getAlojamientoId()).isEqualTo(25);
        assertThat(request.getNumeroHuespedes()).isEqualTo(2);
        assertThat(request.getMetodoPago()).isEqualTo("TARJETA_CREDITO");
        assertThat(request.getFechaCheckout()).isAfter(request.getFechaCheckin());
    }

    @Test
    @DisplayName("Escenario - Reserva familiar larga")
    void escenario_ReservaFamiliarLarga() {
        // Act
        CrearReservaRequest request = new CrearReservaRequest();
        request.setAlojamientoId(50);
        request.setFechaCheckin(LocalDate.now().plusDays(30));
        request.setFechaCheckout(LocalDate.now().plusDays(45));
        request.setNumeroHuespedes(6);
        request.setMetodoPago("EFECTIVO");

        // Assert
        assertThat(request.getAlojamientoId()).isEqualTo(50);
        assertThat(request.getNumeroHuespedes()).isEqualTo(6);
        assertThat(request.getMetodoPago()).isEqualTo("EFECTIVO");
        assertThat(request.getFechaCheckout()).isAfter(request.getFechaCheckin());
        long diasReserva = request.getFechaCheckout().toEpochDay() - request.getFechaCheckin().toEpochDay();
        assertThat(diasReserva).isEqualTo(15);
    }

    @Test
    @DisplayName("Escenario - Reserva sin método de pago especificado")
    void escenario_ReservaSinMetodoPagoEspecificado() {
        // Act
        CrearReservaRequest request = CrearReservaRequest.builder()
                .alojamientoId(15)
                .fechaCheckin(LocalDate.now().plusDays(3))
                .fechaCheckout(LocalDate.now().plusDays(5))
                .numeroHuespedes(1)
                .build();

        // Assert
        assertThat(request.getAlojamientoId()).isEqualTo(15);
        assertThat(request.getNumeroHuespedes()).isEqualTo(1);
        assertThat(request.getMetodoPago()).isNull();
        assertThat(request.getFechaCheckin()).isAfter(LocalDate.now());
    }

    @Test
    @DisplayName("Casos borde - Número mínimo de huéspedes")
    void casosBorde_NumeroMinimoDeHuespedes() {
        // Arrange & Act
        CrearReservaRequest request = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                FECHA_CHECKIN_VALIDA,
                FECHA_CHECKOUT_VALIDA,
                1, // Mínimo permitido
                METODO_PAGO_VALIDO
        );

        // Assert
        assertThat(request.getNumeroHuespedes()).isEqualTo(1);
    }

    @Test
    @DisplayName("Casos borde - Fecha checkin presente")
    void casosBorde_FechaCheckinPresente() {
        // Arrange & Act
        LocalDate fechaPresente = LocalDate.now();
        CrearReservaRequest request = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                fechaPresente,
                FECHA_CHECKOUT_VALIDA,
                NUMERO_HUESPEDES_VALIDO,
                METODO_PAGO_VALIDO
        );

        // Assert
        assertThat(request.getFechaCheckin()).isEqualTo(fechaPresente);
    }

    @Test
    @DisplayName("Casos borde - Fecha checkout un día después de checkin")
    void casosBorde_FechaCheckoutUnDiaDespuesDeCheckin() {
        // Arrange & Act
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = checkin.plusDays(1);
        CrearReservaRequest request = new CrearReservaRequest(
                ALOJAMIENTO_ID_VALIDO,
                checkin,
                checkout,
                NUMERO_HUESPEDES_VALIDO,
                METODO_PAGO_VALIDO
        );

        // Assert
        assertThat(request.getFechaCheckout()).isEqualTo(checkout);
        assertThat(request.getFechaCheckout()).isAfter(request.getFechaCheckin());
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        CrearReservaRequest request = new CrearReservaRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        CrearReservaRequest request = new CrearReservaRequest(
                100,
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4),
                3,
                "TARJETA_DEBITO"
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getAlojamientoId()).isEqualTo(100);
        assertThat(request.getNumeroHuespedes()).isEqualTo(3);
        assertThat(request.getMetodoPago()).isEqualTo("TARJETA_DEBITO");
    }

    @Test
    @DisplayName("Lombok - Builder funciona")
    void lombok_BuilderFunciona() {
        // Act
        CrearReservaRequest request = CrearReservaRequest.builder()
                .alojamientoId(75)
                .fechaCheckin(LocalDate.now().plusDays(10))
                .fechaCheckout(LocalDate.now().plusDays(12))
                .numeroHuespedes(2)
                .metodoPago("TRANSFERENCIA")
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getAlojamientoId()).isEqualTo(75);
        assertThat(request.getNumeroHuespedes()).isEqualTo(2);
        assertThat(request.getMetodoPago()).isEqualTo("TRANSFERENCIA");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        CrearReservaRequest request = new CrearReservaRequest();

        // Act
        request.setAlojamientoId(200);
        request.setFechaCheckin(LocalDate.now().plusDays(1));
        request.setFechaCheckout(LocalDate.now().plusDays(3));
        request.setNumeroHuespedes(5);
        request.setMetodoPago("EFECTIVO");

        // Assert
        assertThat(request.getAlojamientoId()).isEqualTo(200);
        assertThat(request.getNumeroHuespedes()).isEqualTo(5);
        assertThat(request.getMetodoPago()).isEqualTo("EFECTIVO");
        assertThat(request.getFechaCheckout()).isAfter(request.getFechaCheckin());
    }
}