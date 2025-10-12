package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ComentarioDTO
 */
@DisplayName("ComentarioDTO - Unit Tests")
public class ComentarioDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 100;
    private final Long RESERVA_ID_VALIDO = 25L;
    private final Long USUARIO_ID_VALIDO = 15L;
    private final String USUARIO_NOMBRE_VALIDO = "Juan Pérez";
    private final Long ALOJAMIENTO_ID_VALIDO = 10L;
    private final Integer CALIFICACION_VALIDA = 5;
    private final String COMENTARIO_TEXTO_VALIDO = "Excelente alojamiento, muy recomendado!";
    private final LocalDateTime FECHA_COMENTARIO_VALIDA = LocalDateTime.of(2024, 1, 15, 14, 30);

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .usuarioNombre(USUARIO_NOMBRE_VALIDO)
                .alojamientoId(ALOJAMIENTO_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .fechaComentario(FECHA_COMENTARIO_VALIDA)
                .build();

        // ASSERT
        assertThat(comentario).isNotNull();
        assertThat(comentario.getId()).isEqualTo(ID_VALIDO);
        assertThat(comentario.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(comentario.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(comentario.getUsuarioNombre()).isEqualTo(USUARIO_NOMBRE_VALIDO);
        assertThat(comentario.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(comentario.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(comentario.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
        assertThat(comentario.getFechaComentario()).isEqualTo(FECHA_COMENTARIO_VALIDA);
        assertThat(comentario.getRespuestas()).isNull(); // No se estableció en el builder
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ASSERT
        assertThat(comentario).isNotNull();
        assertThat(comentario.getId()).isEqualTo(ID_VALIDO);
        assertThat(comentario.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(comentario.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(comentario.getUsuarioNombre()).isNull();
        assertThat(comentario.getAlojamientoId()).isNull();
        assertThat(comentario.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(comentario.getComentarioTexto()).isNull();
        assertThat(comentario.getFechaComentario()).isNull();
        assertThat(comentario.getRespuestas()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ComentarioDTO comentario = new ComentarioDTO();

        // ASSERT
        assertThat(comentario).isNotNull();
        assertThat(comentario.getId()).isNull();
        assertThat(comentario.getReservaId()).isNull();
        assertThat(comentario.getUsuarioId()).isNull();
        assertThat(comentario.getUsuarioNombre()).isNull();
        assertThat(comentario.getAlojamientoId()).isNull();
        assertThat(comentario.getCalificacion()).isNull();
        assertThat(comentario.getComentarioTexto()).isNull();
        assertThat(comentario.getFechaComentario()).isNull();
        assertThat(comentario.getRespuestas()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE
        List<RespuestaComentarioDTO> respuestas = Arrays.asList(
                new RespuestaComentarioDTO(1, "Gracias por tu comentario!",
                        LocalDateTime.now(), 105L, "María González"),
                new RespuestaComentarioDTO(2, "Nos alegra que hayas disfrutado tu estancia",
                        LocalDateTime.now(), 105L, "María González")
        );

        // ACT
        ComentarioDTO comentario = new ComentarioDTO(
                ID_VALIDO, RESERVA_ID_VALIDO, USUARIO_ID_VALIDO, USUARIO_NOMBRE_VALIDO,
                ALOJAMIENTO_ID_VALIDO, CALIFICACION_VALIDA, COMENTARIO_TEXTO_VALIDO,
                FECHA_COMENTARIO_VALIDA, respuestas
        );

        // ASSERT
        assertThat(comentario).isNotNull();
        assertThat(comentario.getId()).isEqualTo(ID_VALIDO);
        assertThat(comentario.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(comentario.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(comentario.getUsuarioNombre()).isEqualTo(USUARIO_NOMBRE_VALIDO);
        assertThat(comentario.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(comentario.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(comentario.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
        assertThat(comentario.getFechaComentario()).isEqualTo(FECHA_COMENTARIO_VALIDA);
        assertThat(comentario.getRespuestas()).hasSize(2);
        assertThat(comentario.getRespuestas().get(0).getContenido()).contains("Gracias");
        assertThat(comentario.getRespuestas().get(1).getContenido()).contains("alegra");
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();
        List<RespuestaComentarioDTO> respuestas = Arrays.asList(
                new RespuestaComentarioDTO(1, "Respuesta 1", LocalDateTime.now(), 105L, "Anfitrión")
        );

        // ACT
        comentario.setId(ID_VALIDO);
        comentario.setReservaId(RESERVA_ID_VALIDO);
        comentario.setUsuarioId(USUARIO_ID_VALIDO);
        comentario.setUsuarioNombre(USUARIO_NOMBRE_VALIDO);
        comentario.setAlojamientoId(ALOJAMIENTO_ID_VALIDO);
        comentario.setCalificacion(CALIFICACION_VALIDA);
        comentario.setComentarioTexto(COMENTARIO_TEXTO_VALIDO);
        comentario.setFechaComentario(FECHA_COMENTARIO_VALIDA);
        comentario.setRespuestas(respuestas);

        // ASSERT
        assertThat(comentario.getId()).isEqualTo(ID_VALIDO);
        assertThat(comentario.getReservaId()).isEqualTo(RESERVA_ID_VALIDO);
        assertThat(comentario.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(comentario.getUsuarioNombre()).isEqualTo(USUARIO_NOMBRE_VALIDO);
        assertThat(comentario.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO);
        assertThat(comentario.getCalificacion()).isEqualTo(CALIFICACION_VALIDA);
        assertThat(comentario.getComentarioTexto()).isEqualTo(COMENTARIO_TEXTO_VALIDO);
        assertThat(comentario.getFechaComentario()).isEqualTo(FECHA_COMENTARIO_VALIDA);
        assertThat(comentario.getRespuestas()).isEqualTo(respuestas);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ComentarioDTO comentario1 = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .usuarioNombre(USUARIO_NOMBRE_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        ComentarioDTO comentario2 = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .usuarioNombre(USUARIO_NOMBRE_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ACT & ASSERT
        assertThat(comentario1).isEqualTo(comentario2);
        assertThat(comentario1.hashCode()).isEqualTo(comentario2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        ComentarioDTO comentario1 = ComentarioDTO.builder()
                .id(1)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        ComentarioDTO comentario2 = ComentarioDTO.builder()
                .id(2)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ACT & ASSERT
        assertThat(comentario1).isNotEqualTo(comentario2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .build();

        // ACT & ASSERT
        assertThat(comentario).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .build();

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(comentario).isNotEqualTo(objetoDiferente);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del comentario")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .usuarioNombre(USUARIO_NOMBRE_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .comentarioTexto(COMENTARIO_TEXTO_VALIDO)
                .build();

        // ACT
        String resultado = comentario.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(USUARIO_NOMBRE_VALIDO);
        assertThat(resultado).contains(String.valueOf(CALIFICACION_VALIDA));
        assertThat(resultado).contains(COMENTARIO_TEXTO_VALIDO);
    }

    @Test
    @DisplayName("TO STRING - Con campos null se maneja correctamente")
    void toString_ConCamposNull_SeManejaCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT
        String resultado = comentario.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
    }

    // ==================== CALIFICACIÓN TESTS ====================

    @Test
    @DisplayName("CALIFICACIÓN - Valores dentro del rango 1-5 son aceptados")
    void calificacion_ValoresDentroDelRango1a5_SonAceptados() {
        // ARRANGE & ACT
        ComentarioDTO calificacion1 = ComentarioDTO.builder().id(1).calificacion(1).build();
        ComentarioDTO calificacion2 = ComentarioDTO.builder().id(2).calificacion(2).build();
        ComentarioDTO calificacion3 = ComentarioDTO.builder().id(3).calificacion(3).build();
        ComentarioDTO calificacion4 = ComentarioDTO.builder().id(4).calificacion(4).build();
        ComentarioDTO calificacion5 = ComentarioDTO.builder().id(5).calificacion(5).build();

        // ASSERT
        assertThat(calificacion1.getCalificacion()).isEqualTo(1);
        assertThat(calificacion2.getCalificacion()).isEqualTo(2);
        assertThat(calificacion3.getCalificacion()).isEqualTo(3);
        assertThat(calificacion4.getCalificacion()).isEqualTo(4);
        assertThat(calificacion5.getCalificacion()).isEqualTo(5);
    }

    @Test
    @DisplayName("CALIFICACIÓN - Valores fuera del rango se manejan correctamente")
    void calificacion_ValoresFueraDelRango_SeManejanCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT & ASSERT
        comentario.setCalificacion(0);  // Por debajo del mínimo
        assertThat(comentario.getCalificacion()).isEqualTo(0);

        comentario.setCalificacion(6);  // Por encima del máximo
        assertThat(comentario.getCalificacion()).isEqualTo(6);

        comentario.setCalificacion(-1); // Valor negativo
        assertThat(comentario.getCalificacion()).isEqualTo(-1);
    }

    @Test
    @DisplayName("CALIFICACIÓN - Calificación null se maneja correctamente")
    void calificacion_CalificacionNull_SeManejaCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT
        comentario.setCalificacion(null);

        // ASSERT
        assertThat(comentario.getCalificacion()).isNull();
    }

    // ==================== TEXTO COMENTARIO TESTS ====================

    @Test
    @DisplayName("TEXTO COMENTARIO - Texto muy largo se maneja correctamente")
    void textoComentario_TextoMuyLargo_SeManejaCorrectamente() {
        // ARRANGE
        String textoLargo = "Muy ".repeat(500) + "bueno!";
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT
        comentario.setComentarioTexto(textoLargo);

        // ASSERT
        assertThat(comentario.getComentarioTexto()).isEqualTo(textoLargo);
        assertThat(comentario.getComentarioTexto()).hasSizeGreaterThan(1000);
    }

    @Test
    @DisplayName("TEXTO COMENTARIO - Texto vacío se maneja correctamente")
    void textoComentario_TextoVacio_SeManejaCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT
        comentario.setComentarioTexto("");

        // ASSERT
        assertThat(comentario.getComentarioTexto()).isEmpty();
    }

    @Test
    @DisplayName("TEXTO COMENTARIO - Texto con solo espacios se maneja correctamente")
    void textoComentario_TextoConSoloEspacios_SeManejaCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT
        comentario.setComentarioTexto("   ");

        // ASSERT
        assertThat(comentario.getComentarioTexto()).isEqualTo("   ");
    }

    // ==================== FECHAS TESTS ====================

    @Test
    @DisplayName("FECHAS - Fechas en diferentes momentos funcionan correctamente")
    void fechas_FechasEnDiferentesMomentos_FuncionanCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        LocalDateTime fechaPasado = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime fechaFuturo = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaReciente = LocalDateTime.now().minusHours(1);

        // ACT & ASSERT
        comentario.setFechaComentario(fechaPasado);
        assertThat(comentario.getFechaComentario()).isEqualTo(fechaPasado);

        comentario.setFechaComentario(fechaFuturo);
        assertThat(comentario.getFechaComentario()).isEqualTo(fechaFuturo);

        comentario.setFechaComentario(fechaReciente);
        assertThat(comentario.getFechaComentario()).isEqualTo(fechaReciente);
    }

    @Test
    @DisplayName("FECHAS - Fecha null se maneja correctamente")
    void fechas_FechaNull_SeManejaCorrectamente() {
        // ARRANGE
        ComentarioDTO comentario = new ComentarioDTO();

        // ACT
        comentario.setFechaComentario(null);

        // ASSERT
        assertThat(comentario.getFechaComentario()).isNull();
    }

    // ==================== RESPUESTAS TESTS ====================

    @Test
    @DisplayName("RESPUESTAS - Lista de respuestas se maneja correctamente")
    void respuestas_ListaDeRespuestas_SeManejaCorrectamente() {
        // ARRANGE
        List<RespuestaComentarioDTO> respuestas = Arrays.asList(
                new RespuestaComentarioDTO(1, "Gracias por tu feedback!", LocalDateTime.now(), 105L, "Anfitrión"),
                new RespuestaComentarioDTO(2, "Esperamos verte pronto de nuevo", LocalDateTime.now().plusHours(1), 105L, "Anfitrión"),
                new RespuestaComentarioDTO(3, "Nos alegra que hayas disfrutado", LocalDateTime.now().plusHours(2), 105L, "Anfitrión")
        );

        // ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .respuestas(respuestas)
                .build();

        // ASSERT
        assertThat(comentario.getRespuestas()).isNotNull();
        assertThat(comentario.getRespuestas()).hasSize(3);
        assertThat(comentario.getRespuestas().get(0).getContenido()).contains("Gracias");
        assertThat(comentario.getRespuestas().get(1).getContenido()).contains("Esperamos");
        assertThat(comentario.getRespuestas().get(2).getContenido()).contains("Nos alegra");
    }

    @Test
    @DisplayName("RESPUESTAS - Lista vacía de respuestas se maneja correctamente")
    void respuestas_ListaVaciaDeRespuestas_SeManejaCorrectamente() {
        // ARRANGE
        List<RespuestaComentarioDTO> respuestasVacias = Collections.emptyList();

        // ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .respuestas(respuestasVacias)
                .build();

        // ASSERT
        assertThat(comentario.getRespuestas()).isNotNull();
        assertThat(comentario.getRespuestas()).isEmpty();
    }

    @Test
    @DisplayName("RESPUESTAS - Respuestas null se maneja correctamente")
    void respuestas_RespuestasNull_SeManejaCorrectamente() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .respuestas(null)
                .build();

        // ASSERT
        assertThat(comentario.getRespuestas()).isNull();
    }

    // ==================== RELACIONES TESTS ====================

    @Test
    @DisplayName("RELACIONES - IDs de relaciones con valores límite se manejan correctamente")
    void relaciones_IdsConValoresLimite_SeManejanCorrectamente() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(1)
                .reservaId(1L)
                .usuarioId(1L)
                .alojamientoId(1L)
                .build();

        // ASSERT
        assertThat(comentario.getReservaId()).isEqualTo(1L);
        assertThat(comentario.getUsuarioId()).isEqualTo(1L);
        assertThat(comentario.getAlojamientoId()).isEqualTo(1L);
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Todos los campos null excepto ID")
    void casoBorde_TodosLosCamposNullExceptoId() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .build();

        // ASSERT
        assertThat(comentario).isNotNull();
        assertThat(comentario.getId()).isEqualTo(ID_VALIDO);
        assertThat(comentario.getReservaId()).isNull();
        assertThat(comentario.getUsuarioId()).isNull();
        assertThat(comentario.getUsuarioNombre()).isNull();
        assertThat(comentario.getAlojamientoId()).isNull();
        assertThat(comentario.getCalificacion()).isNull();
        assertThat(comentario.getComentarioTexto()).isNull();
        assertThat(comentario.getFechaComentario()).isNull();
        assertThat(comentario.getRespuestas()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Comentario sin texto pero con calificación")
    void casoBorde_ComentarioSinTextoPeroConCalificacion() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .calificacion(5)
                .build();

        // ASSERT
        assertThat(comentario.getCalificacion()).isEqualTo(5);
        assertThat(comentario.getComentarioTexto()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Comentario con texto pero sin calificación")
    void casoBorde_ComentarioConTextoSinCalificacion() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .comentarioTexto("Buen lugar pero olvidé calificar")
                .build();

        // ASSERT
        assertThat(comentario.getComentarioTexto()).isEqualTo("Buen lugar pero olvidé calificar");
        assertThat(comentario.getCalificacion()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Comentario con calificación positiva")
    void scenarioUsoReal_ComentarioConCalificacionPositiva() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .usuarioNombre("Carlos Gómez")
                .calificacion(5)
                .comentarioTexto("Increíble experiencia, todo perfecto!")
                .fechaComentario(LocalDateTime.now().minusDays(1))
                .build();

        // ASSERT
        assertThat(comentario.getCalificacion()).isEqualTo(5);
        assertThat(comentario.getComentarioTexto()).contains("Increíble");
        assertThat(comentario.getFechaComentario()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Comentario con calificación neutral")
    void scenarioUsoReal_ComentarioConCalificacionNeutral() {
        // ARRANGE & ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .usuarioNombre("Ana Martínez")
                .calificacion(3)
                .comentarioTexto("Bueno en general, hay aspectos a mejorar")
                .fechaComentario(LocalDateTime.now().minusDays(2))
                .build();

        // ASSERT
        assertThat(comentario.getCalificacion()).isEqualTo(3);
        assertThat(comentario.getComentarioTexto()).contains("aspectos a mejorar");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Comentario con respuestas del anfitrión")
    void scenarioUsoReal_ComentarioConRespuestasDelAnfitrion() {
        // ARRANGE
        List<RespuestaComentarioDTO> respuestas = Arrays.asList(
                new RespuestaComentarioDTO(1, "Gracias por tu comentario Carlos!",
                        LocalDateTime.now().minusHours(12), 105L, "María González"),
                new RespuestaComentarioDTO(2, "Nos alegra que hayas disfrutado tu estadía",
                        LocalDateTime.now().minusHours(10), 105L, "María González")
        );

        // ACT
        ComentarioDTO comentario = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .usuarioNombre("Carlos Gómez")
                .calificacion(5)
                .comentarioTexto("Excelente servicio y atención")
                .fechaComentario(LocalDateTime.now().minusDays(1))
                .respuestas(respuestas)
                .build();

        // ASSERT
        assertThat(comentario.getRespuestas()).hasSize(2);
        assertThat(comentario.getRespuestas().get(0).getContenido()).contains("Gracias");
        assertThat(comentario.getRespuestas().get(1).getContenido()).contains("estadía");
        assertThat(comentario.getRespuestas().get(0).getAutorNombre()).isEqualTo("María González");
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        ComentarioDTO comentario1 = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        ComentarioDTO comentario2 = ComentarioDTO.builder()
                .id(ID_VALIDO)
                .reservaId(RESERVA_ID_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .calificacion(CALIFICACION_VALIDA)
                .build();

        // ASSERT - Verificar que @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(comentario1).isEqualTo(comentario2);
        assertThat(comentario1.toString()).isNotNull();
        assertThat(comentario1.hashCode()).isEqualTo(comentario2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ComentarioDTO comentarioVacio = new ComentarioDTO();
        assertThat(comentarioVacio).isNotNull();

        // Verificar que el builder funciona
        assertThat(comentario1).isInstanceOf(ComentarioDTO.class);
    }
}