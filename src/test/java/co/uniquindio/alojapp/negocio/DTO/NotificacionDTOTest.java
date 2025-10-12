package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para NotificacionDTO
 */
@DisplayName("NotificacionDTO - Unit Tests")
public class NotificacionDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 101;
    private final String TITULO_VALIDO = "Reserva confirmada";
    private final String MENSAJE_VALIDO = "Tu reserva en Casa de Playa ha sido confirmada";
    private final LocalDateTime FECHA_ENVIO_VALIDA = LocalDateTime.of(2025, 9, 10, 14, 35);
    private final boolean LEIDA_VALIDA = false;
    private final Long USUARIO_ID_VALIDO = 123L;

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        NotificacionDTO notificacion = new NotificacionDTO();

        // ASSERT
        assertThat(notificacion).isNotNull();
        assertThat(notificacion.getId()).isNull();
        assertThat(notificacion.getTitulo()).isNull();
        assertThat(notificacion.getMensaje()).isNull();
        assertThat(notificacion.getFechaEnvio()).isNull();
        assertThat(notificacion.isLeida()).isFalse(); // Valor por defecto para boolean
        assertThat(notificacion.getUsuarioId()).isNull();
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);
        notificacion.setMensaje(MENSAJE_VALIDO);
        notificacion.setFechaEnvio(FECHA_ENVIO_VALIDA);
        notificacion.setLeida(LEIDA_VALIDA);
        notificacion.setUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(notificacion.getId()).isEqualTo(ID_VALIDO);
        assertThat(notificacion.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(notificacion.getMensaje()).isEqualTo(MENSAJE_VALIDO);
        assertThat(notificacion.getFechaEnvio()).isEqualTo(FECHA_ENVIO_VALIDA);
        assertThat(notificacion.isLeida()).isEqualTo(LEIDA_VALIDA);
        assertThat(notificacion.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos booleanos con diferentes valores funcionan correctamente")
    void gettersYSetters_CamposBooleanosConDiferentesValores_FuncionanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT & ASSERT
        notificacion.setLeida(true);
        assertThat(notificacion.isLeida()).isTrue();

        notificacion.setLeida(false);
        assertThat(notificacion.isLeida()).isFalse();
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos vacíos se manejan correctamente")
    void gettersYSetters_CamposVacios_SeManejanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT
        notificacion.setTitulo("");
        notificacion.setMensaje("");

        // ASSERT
        assertThat(notificacion.getTitulo()).isEmpty();
        assertThat(notificacion.getMensaje()).isEmpty();
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos con espacios en blanco se mantienen")
    void gettersYSetters_CamposConEspaciosEnBlanco_SeMantienen() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        String tituloConEspacios = "  Reserva confirmada  ";
        String mensajeConEspacios = "  Tu reserva ha sido confirmada  ";

        // ACT
        notificacion.setTitulo(tituloConEspacios);
        notificacion.setMensaje(mensajeConEspacios);

        // ASSERT
        assertThat(notificacion.getTitulo()).isEqualTo(tituloConEspacios);
        assertThat(notificacion.getMensaje()).isEqualTo(mensajeConEspacios);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        NotificacionDTO notificacion1 = new NotificacionDTO();
        notificacion1.setId(ID_VALIDO);
        notificacion1.setTitulo(TITULO_VALIDO);
        notificacion1.setMensaje(MENSAJE_VALIDO);
        notificacion1.setFechaEnvio(FECHA_ENVIO_VALIDA);
        notificacion1.setLeida(LEIDA_VALIDA);
        notificacion1.setUsuarioId(USUARIO_ID_VALIDO);

        NotificacionDTO notificacion2 = new NotificacionDTO();
        notificacion2.setId(ID_VALIDO);
        notificacion2.setTitulo(TITULO_VALIDO);
        notificacion2.setMensaje(MENSAJE_VALIDO);
        notificacion2.setFechaEnvio(FECHA_ENVIO_VALIDA);
        notificacion2.setLeida(LEIDA_VALIDA);
        notificacion2.setUsuarioId(USUARIO_ID_VALIDO);

        // ACT & ASSERT
        assertThat(notificacion1).isEqualTo(notificacion2);
        assertThat(notificacion1.hashCode()).isEqualTo(notificacion2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        NotificacionDTO notificacion1 = new NotificacionDTO();
        notificacion1.setId(1);
        notificacion1.setTitulo(TITULO_VALIDO);
        notificacion1.setUsuarioId(USUARIO_ID_VALIDO);

        NotificacionDTO notificacion2 = new NotificacionDTO();
        notificacion2.setId(2);
        notificacion2.setTitulo(TITULO_VALIDO);
        notificacion2.setUsuarioId(USUARIO_ID_VALIDO);

        // ACT & ASSERT
        assertThat(notificacion1).isNotEqualTo(notificacion2);
    }

    @Test
    @DisplayName("EQUALS - Misma instancia retorna true")
    void equals_MismaInstancia_RetornaTrue() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);

        // ACT & ASSERT
        assertThat(notificacion).isEqualTo(notificacion);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);

        // ACT & ASSERT
        assertThat(notificacion).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(notificacion).isNotEqualTo(objetoDiferente);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante de la notificación")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);
        notificacion.setMensaje(MENSAJE_VALIDO);
        notificacion.setLeida(LEIDA_VALIDA);
        notificacion.setUsuarioId(USUARIO_ID_VALIDO);

        // ACT
        String resultado = notificacion.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(TITULO_VALIDO);
        assertThat(resultado).contains(MENSAJE_VALIDO);
        assertThat(resultado).contains(String.valueOf(LEIDA_VALIDA));
        assertThat(resultado).contains(String.valueOf(USUARIO_ID_VALIDO));
    }

    @Test
    @DisplayName("TO STRING - Con campos null se maneja correctamente")
    void toString_ConCamposNull_SeManejaCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT
        String resultado = notificacion.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
    }

    // ==================== TÍTULO Y MENSAJE TESTS ====================

    @Test
    @DisplayName("TÍTULO Y MENSAJE - Textos muy largos se manejan correctamente")
    void tituloYMensaje_TextosMuyLargos_SeManejanCorrectamente() {
        // ARRANGE
        String tituloLargo = "T".repeat(500);
        String mensajeLargo = "M".repeat(2000);
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT
        notificacion.setTitulo(tituloLargo);
        notificacion.setMensaje(mensajeLargo);

        // ASSERT
        assertThat(notificacion.getTitulo()).isEqualTo(tituloLargo);
        assertThat(notificacion.getMensaje()).isEqualTo(mensajeLargo);
        assertThat(notificacion.getTitulo()).hasSize(500);
        assertThat(notificacion.getMensaje()).hasSize(2000);
    }

    @Test
    @DisplayName("TÍTULO Y MENSAJE - Textos con caracteres especiales se manejan correctamente")
    void tituloYMensaje_TextosConCaracteresEspeciales_SeManejanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        String tituloEspecial = "¡Reserva confirmada! 🎉 - Estado: ✅";
        String mensajeEspecial = "Tu reserva en 'Casa de Playa' ha sido confirmada. ¡Disfruta tu estadía! 🌴";

        // ACT
        notificacion.setTitulo(tituloEspecial);
        notificacion.setMensaje(mensajeEspecial);

        // ASSERT
        assertThat(notificacion.getTitulo()).isEqualTo(tituloEspecial);
        assertThat(notificacion.getMensaje()).isEqualTo(mensajeEspecial);
        assertThat(notificacion.getTitulo()).contains("🎉");
        assertThat(notificacion.getMensaje()).contains("🌴");
    }

    // ==================== FECHAS TESTS ====================

    @Test
    @DisplayName("FECHAS - Fechas en diferentes momentos funcionan correctamente")
    void fechas_FechasEnDiferentesMomentos_FuncionanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        LocalDateTime fechaPasado = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime fechaFuturo = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaReciente = LocalDateTime.now().minusHours(1);

        // ACT & ASSERT
        notificacion.setFechaEnvio(fechaPasado);
        assertThat(notificacion.getFechaEnvio()).isEqualTo(fechaPasado);

        notificacion.setFechaEnvio(fechaFuturo);
        assertThat(notificacion.getFechaEnvio()).isEqualTo(fechaFuturo);

        notificacion.setFechaEnvio(fechaReciente);
        assertThat(notificacion.getFechaEnvio()).isEqualTo(fechaReciente);
    }

    @Test
    @DisplayName("FECHAS - Fecha null se maneja correctamente")
    void fechas_FechaNull_SeManejaCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT
        notificacion.setFechaEnvio(null);

        // ASSERT
        assertThat(notificacion.getFechaEnvio()).isNull();
    }

    // ==================== ESTADO LECTURA TESTS ====================

    @Test
    @DisplayName("ESTADO LECTURA - Valores booleanos se manejan correctamente")
    void estadoLectura_ValoresBooleanos_SeManejanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT & ASSERT
        notificacion.setLeida(true);
        assertThat(notificacion.isLeida()).isTrue();

        notificacion.setLeida(false);
        assertThat(notificacion.isLeida()).isFalse();
    }

    @Test
    @DisplayName("ESTADO LECTURA - Valor por defecto es false")
    void estadoLectura_ValorPorDefectoEsFalse() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();

        // ASSERT - Por defecto, las notificaciones no están leídas
        assertThat(notificacion.isLeida()).isFalse();
    }

    // ==================== RELACIONES TESTS ====================

    @Test
    @DisplayName("RELACIONES - IDs con valores límite se manejan correctamente")
    void relaciones_IdsConValoresLimite_SeManejanCorrectamente() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();

        // ACT & ASSERT
        notificacion.setId(1);
        notificacion.setUsuarioId(1L);

        assertThat(notificacion.getId()).isEqualTo(1);
        assertThat(notificacion.getUsuarioId()).isEqualTo(1L);

        notificacion.setId(Integer.MAX_VALUE);
        notificacion.setUsuarioId(Long.MAX_VALUE);

        assertThat(notificacion.getId()).isEqualTo(Integer.MAX_VALUE);
        assertThat(notificacion.getUsuarioId()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("RELACIONES - Múltiples notificaciones para un mismo usuario")
    void relaciones_MultiplesNotificacionesParaUnMismoUsuario() {
        // ARRANGE & ACT
        NotificacionDTO notificacion1 = new NotificacionDTO();
        notificacion1.setId(101);
        notificacion1.setTitulo("Reserva confirmada");
        notificacion1.setUsuarioId(123L);

        NotificacionDTO notificacion2 = new NotificacionDTO();
        notificacion2.setId(102);
        notificacion2.setTitulo("Pago procesado");
        notificacion2.setUsuarioId(123L);

        NotificacionDTO notificacion3 = new NotificacionDTO();
        notificacion3.setId(103);
        notificacion3.setTitulo("Recordatorio de check-in");
        notificacion3.setUsuarioId(123L);

        // ASSERT
        assertThat(notificacion1.getUsuarioId()).isEqualTo(123L);
        assertThat(notificacion2.getUsuarioId()).isEqualTo(123L);
        assertThat(notificacion3.getUsuarioId()).isEqualTo(123L);

        assertThat(notificacion1.getId()).isNotEqualTo(notificacion2.getId());
        assertThat(notificacion2.getId()).isNotEqualTo(notificacion3.getId());

        assertThat(notificacion1.getTitulo()).isNotEqualTo(notificacion2.getTitulo());
        assertThat(notificacion2.getTitulo()).isNotEqualTo(notificacion3.getTitulo());
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Solo ID establecido")
    void casoBorde_SoloIdEstablecido() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);

        // ASSERT
        assertThat(notificacion).isNotNull();
        assertThat(notificacion.getId()).isEqualTo(ID_VALIDO);
        assertThat(notificacion.getTitulo()).isNull();
        assertThat(notificacion.getMensaje()).isNull();
        assertThat(notificacion.getFechaEnvio()).isNull();
        assertThat(notificacion.isLeida()).isFalse();
        assertThat(notificacion.getUsuarioId()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Solo usuarioId establecido")
    void casoBorde_SoloUsuarioIdEstablecido() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(notificacion.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(notificacion.getId()).isNull();
        assertThat(notificacion.getTitulo()).isNull();
        assertThat(notificacion.getMensaje()).isNull();
        assertThat(notificacion.getFechaEnvio()).isNull();
        assertThat(notificacion.isLeida()).isFalse();
    }

    @Test
    @DisplayName("CASO BORDE - Notificación sin mensaje pero con título")
    void casoBorde_NotificacionSinMensajePeroConTitulo() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);
        notificacion.setUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(notificacion.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(notificacion.getMensaje()).isNull();
        assertThat(notificacion.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
    }

    @Test
    @DisplayName("CASO BORDE - Notificación sin fecha de envío")
    void casoBorde_NotificacionSinFechaDeEnvio() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(ID_VALIDO);
        notificacion.setTitulo(TITULO_VALIDO);
        notificacion.setMensaje(MENSAJE_VALIDO);
        notificacion.setFechaEnvio(null);
        notificacion.setUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT
        assertThat(notificacion.getTitulo()).isEqualTo(TITULO_VALIDO);
        assertThat(notificacion.getMensaje()).isEqualTo(MENSAJE_VALIDO);
        assertThat(notificacion.getFechaEnvio()).isNull();
        assertThat(notificacion.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Notificación de reserva confirmada")
    void scenarioUsoReal_NotificacionDeReservaConfirmada() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(101);
        notificacion.setTitulo("Reserva confirmada");
        notificacion.setMensaje("Tu reserva en 'Casa de Playa' para las fechas 15-20 de Septiembre ha sido confirmada. ¡Prepárate para disfrutar!");
        notificacion.setFechaEnvio(LocalDateTime.now().minusHours(2));
        notificacion.setLeida(false);
        notificacion.setUsuarioId(123L);

        // ASSERT
        assertThat(notificacion.getTitulo()).contains("Reserva confirmada");
        assertThat(notificacion.getMensaje()).contains("Casa de Playa");
        assertThat(notificacion.getMensaje()).contains("Septiembre");
        assertThat(notificacion.isLeida()).isFalse();
        assertThat(notificacion.getFechaEnvio()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Notificación de pago exitoso")
    void scenarioUsoReal_NotificacionDePagoExitoso() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(102);
        notificacion.setTitulo("Pago procesado exitosamente");
        notificacion.setMensaje("Hemos procesado tu pago por $350,000 COP para la reserva #R-2024-0015. Recibirás el comprobante por correo.");
        notificacion.setFechaEnvio(LocalDateTime.now().minusMinutes(30));
        notificacion.setLeida(true);
        notificacion.setUsuarioId(123L);

        // ASSERT
        assertThat(notificacion.getTitulo()).contains("Pago procesado");
        assertThat(notificacion.getMensaje()).contains("$350,000");
        assertThat(notificacion.getMensaje()).contains("comprobante");
        assertThat(notificacion.isLeida()).isTrue();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Notificación de recordatorio")
    void scenarioUsoReal_NotificacionDeRecordatorio() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(103);
        notificacion.setTitulo("Recordatorio de check-in");
        notificacion.setMensaje("Tu check-in en 'Cabaña del Bosque' es mañana a las 3:00 PM. No olvides llevar tu documento de identidad.");
        notificacion.setFechaEnvio(LocalDateTime.now().minusDays(1));
        notificacion.setLeida(false);
        notificacion.setUsuarioId(123L);

        // ASSERT
        assertThat(notificacion.getTitulo()).contains("Recordatorio");
        assertThat(notificacion.getMensaje()).contains("check-in");
        assertThat(notificacion.getMensaje()).contains("3:00 PM");
        assertThat(notificacion.isLeida()).isFalse();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Notificación del sistema")
    void scenarioUsoReal_NotificacionDelSistema() {
        // ARRANGE & ACT
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(104);
        notificacion.setTitulo("Mantenimiento programado");
        notificacion.setMensaje("El sistema estará en mantenimiento el próximo domingo de 2:00 AM a 4:00 AM. Disculpa las molestias.");
        notificacion.setFechaEnvio(LocalDateTime.now().minusHours(6));
        notificacion.setLeida(false);
        notificacion.setUsuarioId(123L);

        // ASSERT
        assertThat(notificacion.getTitulo()).contains("Mantenimiento");
        assertThat(notificacion.getMensaje()).contains("domingo");
        assertThat(notificacion.getMensaje()).contains("2:00 AM");
        assertThat(notificacion.isLeida()).isFalse();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Nueva notificación generada")
    void scenarioUsoReal_NuevaNotificacionGenerada() {
        // ARRANGE & ACT
        NotificacionDTO nuevaNotificacion = new NotificacionDTO();
        nuevaNotificacion.setTitulo("Bienvenido a AlojApp");
        nuevaNotificacion.setMensaje("¡Gracias por registrarte! Ahora puedes explorar todos nuestros alojamientos y hacer reservas.");
        nuevaNotificacion.setFechaEnvio(LocalDateTime.now());
        nuevaNotificacion.setLeida(false);
        nuevaNotificacion.setUsuarioId(456L);

        // ASSERT - Nueva notificación sin ID
        assertThat(nuevaNotificacion.getId()).isNull();
        assertThat(nuevaNotificacion.getTitulo()).contains("Bienvenido");
        assertThat(nuevaNotificacion.getMensaje()).contains("registrarte");
        assertThat(nuevaNotificacion.isLeida()).isFalse();
        assertThat(nuevaNotificacion.getUsuarioId()).isEqualTo(456L);
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        NotificacionDTO notificacion1 = new NotificacionDTO();
        notificacion1.setId(ID_VALIDO);
        notificacion1.setTitulo(TITULO_VALIDO);
        notificacion1.setMensaje(MENSAJE_VALIDO);
        notificacion1.setFechaEnvio(FECHA_ENVIO_VALIDA);
        notificacion1.setLeida(LEIDA_VALIDA);
        notificacion1.setUsuarioId(USUARIO_ID_VALIDO);

        NotificacionDTO notificacion2 = new NotificacionDTO();
        notificacion2.setId(ID_VALIDO);
        notificacion2.setTitulo(TITULO_VALIDO);
        notificacion2.setMensaje(MENSAJE_VALIDO);
        notificacion2.setFechaEnvio(FECHA_ENVIO_VALIDA);
        notificacion2.setLeida(LEIDA_VALIDA);
        notificacion2.setUsuarioId(USUARIO_ID_VALIDO);

        // ASSERT - Verificar que @Data funciona correctamente
        assertThat(notificacion1).isEqualTo(notificacion2);
        assertThat(notificacion1.toString()).isNotNull();
        assertThat(notificacion1.hashCode()).isEqualTo(notificacion2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        NotificacionDTO notificacionVacia = new NotificacionDTO();
        assertThat(notificacionVacia).isNotNull();
    }

    // ==================== GESTIÓN DE ESTADO TESTS ====================

    @Test
    @DisplayName("GESTIÓN DE ESTADO - Marcar notificación como leída")
    void gestionDeEstado_MarcarNotificacionComoLeida() {
        // ARRANGE
        NotificacionDTO notificacion = new NotificacionDTO();
        notificacion.setId(101);
        notificacion.setTitulo("Nueva notificación");
        notificacion.setLeida(false);

        // ACT - Simular que el usuario marca como leída
        notificacion.setLeida(true);

        // ASSERT
        assertThat(notificacion.isLeida()).isTrue();
    }

    @Test
    @DisplayName("GESTIÓN DE ESTADO - Notificaciones ordenadas por fecha")
    void gestionDeEstado_NotificacionesOrdenadasPorFecha() {
        // ARRANGE & ACT
        NotificacionDTO notificacionReciente = new NotificacionDTO();
        notificacionReciente.setId(101);
        notificacionReciente.setFechaEnvio(LocalDateTime.now().minusHours(1));

        NotificacionDTO notificacionAntigua = new NotificacionDTO();
        notificacionAntigua.setId(102);
        notificacionAntigua.setFechaEnvio(LocalDateTime.now().minusDays(2));

        // ASSERT - Para ordenamiento, la más reciente debe ser mayor
        assertThat(notificacionReciente.getFechaEnvio())
                .isAfter(notificacionAntigua.getFechaEnvio());
    }
}