package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para RespuestaComentarioDTO
 *
 * OBJETIVO: Probar el DTO de respuesta a comentarios
 * - Validar constructor, builder, getters/setters
 * - Verificar equals/hashCode
 * - Probar diferentes escenarios de uso
 */
@DisplayName("RespuestaComentarioDTO - Unit Tests")
public class RespuestaComentarioDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 200;
    private final String CONTENIDO_VALIDO = "Gracias por tu comentario, te esperamos nuevamente.";
    private final LocalDateTime FECHA_RESPUESTA_VALIDA = LocalDateTime.of(2025, 10, 5, 18, 45);
    private final Long COMENTARIO_ID_VALIDO = 105L;
    private final String AUTOR_NOMBRE_VALIDO = "Carlos Ramírez";

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO();

        // ASSERT
        assertThat(respuesta).isNotNull();
        assertThat(respuesta.getId()).isNull();
        assertThat(respuesta.getContenido()).isNull();
        assertThat(respuesta.getFechaRespuesta()).isNull();
        assertThat(respuesta.getComentarioId()).isNull();
        assertThat(respuesta.getAutorNombre()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA,
                COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ASSERT
        assertThat(respuesta).isNotNull();
        assertThat(respuesta.getId()).isEqualTo(ID_VALIDO);
        assertThat(respuesta.getContenido()).isEqualTo(CONTENIDO_VALIDO);
        assertThat(respuesta.getFechaRespuesta()).isEqualTo(FECHA_RESPUESTA_VALIDA);
        assertThat(respuesta.getComentarioId()).isEqualTo(COMENTARIO_ID_VALIDO);
        assertThat(respuesta.getAutorNombre()).isEqualTo(AUTOR_NOMBRE_VALIDO);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO();

        // ACT
        respuesta.setId(ID_VALIDO);
        respuesta.setContenido(CONTENIDO_VALIDO);
        respuesta.setFechaRespuesta(FECHA_RESPUESTA_VALIDA);
        respuesta.setComentarioId(COMENTARIO_ID_VALIDO);
        respuesta.setAutorNombre(AUTOR_NOMBRE_VALIDO);

        // ASSERT
        assertThat(respuesta.getId()).isEqualTo(ID_VALIDO);
        assertThat(respuesta.getContenido()).isEqualTo(CONTENIDO_VALIDO);
        assertThat(respuesta.getFechaRespuesta()).isEqualTo(FECHA_RESPUESTA_VALIDA);
        assertThat(respuesta.getComentarioId()).isEqualTo(COMENTARIO_ID_VALIDO);
        assertThat(respuesta.getAutorNombre()).isEqualTo(AUTOR_NOMBRE_VALIDO);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE - Usar la MISMA fecha para ambos objetos
        LocalDateTime mismaFecha = LocalDateTime.now();

        RespuestaComentarioDTO respuesta1 = new RespuestaComentarioDTO(
                ID_VALIDO, "Contenido idéntico", mismaFecha, 1L, "Mismo autor"
        );

        RespuestaComentarioDTO respuesta2 = new RespuestaComentarioDTO(
                ID_VALIDO, "Contenido idéntico", mismaFecha, 1L, "Mismo autor"
        );

        // ACT & ASSERT
        assertThat(respuesta1).isEqualTo(respuesta2);
        assertThat(respuesta1.hashCode()).isEqualTo(respuesta2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE - Mismos valores excepto ID
        RespuestaComentarioDTO respuesta1 = new RespuestaComentarioDTO(
                1, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        RespuestaComentarioDTO respuesta2 = new RespuestaComentarioDTO(
                2, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT & ASSERT
        assertThat(respuesta1).isNotEqualTo(respuesta2);
    }

    @Test
    @DisplayName("EQUALS - Diferente contenido retorna false")
    void equals_DiferenteContenido_RetornaFalse() {
        // ARRANGE - Mismos valores excepto contenido
        RespuestaComentarioDTO respuesta1 = new RespuestaComentarioDTO(
                ID_VALIDO, "Contenido 1", FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        RespuestaComentarioDTO respuesta2 = new RespuestaComentarioDTO(
                ID_VALIDO, "Contenido 2", FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT & ASSERT
        assertThat(respuesta1).isNotEqualTo(respuesta2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT & ASSERT
        assertThat(respuesta).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT & ASSERT
        assertThat(respuesta).isNotEqualTo("No soy una respuesta");
    }

    @Test
    @DisplayName("EQUALS - Ambos objetos null en ID son iguales")
    void equals_AmbosObjetosNullEnId_SonIguales() {
        // ARRANGE
        RespuestaComentarioDTO respuesta1 = new RespuestaComentarioDTO(
                null, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        RespuestaComentarioDTO respuesta2 = new RespuestaComentarioDTO(
                null, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT & ASSERT
        assertThat(respuesta1).isEqualTo(respuesta2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante de la respuesta")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT
        String resultado = respuesta.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(CONTENIDO_VALIDO);
        assertThat(resultado).contains(AUTOR_NOMBRE_VALIDO);
        assertThat(resultado).contains(String.valueOf(COMENTARIO_ID_VALIDO));
    }

    // ==================== CONTENIDO TESTS ====================

    @Test
    @DisplayName("CONTENIDO - Diferentes longitudes de contenido son aceptadas")
    void contenido_DiferentesLongitudesDeContenido_SonAceptadas() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO();

        // ACT & ASSERT
        respuesta.setContenido("OK");
        assertThat(respuesta.getContenido()).isEqualTo("OK");

        respuesta.setContenido("Muchas gracias por su comentario. Valoramos mucho su opinión y estamos trabajando para mejorar nuestros servicios continuamente.");
        assertThat(respuesta.getContenido()).hasSizeGreaterThan(50);

        respuesta.setContenido("");
        assertThat(respuesta.getContenido()).isEmpty();

        respuesta.setContenido(null);
        assertThat(respuesta.getContenido()).isNull();
    }

    // ==================== FECHA TESTS ====================

    @Test
    @DisplayName("FECHA - Diferentes fechas y horas son aceptadas")
    void fecha_DiferentesFechasYHoras_SonAceptadas() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO();

        // ACT & ASSERT
        LocalDateTime fechaPasado = LocalDateTime.of(2020, 1, 1, 0, 0);
        respuesta.setFechaRespuesta(fechaPasado);
        assertThat(respuesta.getFechaRespuesta()).isEqualTo(fechaPasado);

        LocalDateTime fechaFuturo = LocalDateTime.of(2030, 12, 31, 23, 59);
        respuesta.setFechaRespuesta(fechaFuturo);
        assertThat(respuesta.getFechaRespuesta()).isEqualTo(fechaFuturo);

        respuesta.setFechaRespuesta(null);
        assertThat(respuesta.getFechaRespuesta()).isNull();
    }

    // ==================== AUTOR TESTS ====================

    @Test
    @DisplayName("AUTOR - Diferentes nombres de autor son aceptados")
    void autor_DiferentesNombresDeAutor_SonAceptados() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO();

        // ACT & ASSERT
        respuesta.setAutorNombre("Ana García");
        assertThat(respuesta.getAutorNombre()).isEqualTo("Ana García");

        respuesta.setAutorNombre("Juan Carlos Pérez Rodríguez");
        assertThat(respuesta.getAutorNombre()).hasSizeGreaterThan(20);

        respuesta.setAutorNombre("A");
        assertThat(respuesta.getAutorNombre()).isEqualTo("A");

        respuesta.setAutorNombre("");
        assertThat(respuesta.getAutorNombre()).isEmpty();

        respuesta.setAutorNombre(null);
        assertThat(respuesta.getAutorNombre()).isNull();
    }

    // ==================== COMENTARIO ID TESTS ====================

    @Test
    @DisplayName("COMENTARIO ID - Diferentes IDs de comentario son aceptados")
    void comentarioId_DiferentesIdsDeComentario_SonAceptados() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO();

        // ACT & ASSERT
        respuesta.setComentarioId(1L);
        assertThat(respuesta.getComentarioId()).isEqualTo(1L);

        respuesta.setComentarioId(999L);
        assertThat(respuesta.getComentarioId()).isEqualTo(999L);

        respuesta.setComentarioId(0L);
        assertThat(respuesta.getComentarioId()).isZero();

        respuesta.setComentarioId(null);
        assertThat(respuesta.getComentarioId()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Respuesta rápida del anfitrión")
    void scenarioUsoReal_RespuestaRapidaDelAnfitrion() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                201,
                "Gracias por tu comentario. Nos alegra que hayas disfrutado tu estancia.",
                LocalDateTime.of(2025, 10, 6, 9, 30),
                150L,
                "María González"
        );

        // ASSERT
        assertThat(respuesta.getId()).isEqualTo(201);
        assertThat(respuesta.getContenido()).contains("Gracias");
        assertThat(respuesta.getFechaRespuesta()).isAfter(LocalDateTime.of(2025, 10, 5, 0, 0));
        assertThat(respuesta.getComentarioId()).isEqualTo(150L);
        assertThat(respuesta.getAutorNombre()).isEqualTo("María González");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Respuesta formal de administración")
    void scenarioUsoReal_RespuestaFormalDeAdministracion() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                202,
                "Apreciado huésped, agradecemos sus comentarios. Hemos tomado nota de sus observaciones y estamos trabajando en las mejoras correspondientes. Esperamos poder brindarle una mejor experiencia en su próxima visita.",
                LocalDateTime.of(2025, 10, 7, 14, 15),
                155L,
                "Administración AlojApp"
        );

        // ASSERT
        assertThat(respuesta.getContenido()).hasSizeGreaterThan(100);
        assertThat(respuesta.getAutorNombre()).isEqualTo("Administración AlojApp");
        assertThat(respuesta.getContenido()).contains("agradecemos", "mejoras");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Respuesta a comentario negativo")
    void scenarioUsoReal_RespuestaAComentarioNegativo() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                203,
                "Lamentamos mucho que su experiencia no haya sido la esperada. Nos pondremos en contacto con usted directamente para resolver esta situación.",
                LocalDateTime.of(2025, 10, 8, 16, 45),
                160L,
                "Carlos Ramírez - Gerente"
        );

        // ASSERT
        assertThat(respuesta.getContenido()).contains("Lamentamos", "resolver");
        assertThat(respuesta.getAutorNombre()).contains("Gerente");
        assertThat(respuesta.getFechaRespuesta().getHour()).isBetween(16, 17);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Nueva respuesta sin ID")
    void scenarioUsoReal_NuevaRespuestaSinId() {
        // ARRANGE & ACT
        RespuestaComentarioDTO nuevaRespuesta = new RespuestaComentarioDTO(
                null,
                "Estamos revisando su comentario y le responderemos pronto.",
                LocalDateTime.now(),
                165L,
                "Soporte Técnico"
        );

        // ASSERT - Nueva respuesta sin ID asignado
        assertThat(nuevaRespuesta.getId()).isNull();
        assertThat(nuevaRespuesta.getContenido()).isNotNull();
        assertThat(nuevaRespuesta.getComentarioId()).isEqualTo(165L);
        assertThat(nuevaRespuesta.getAutorNombre()).isEqualTo("Soporte Técnico");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Respuesta con contenido multilínea")
    void scenarioUsoReal_RespuestaConContenidoMultilinea() {
        // ARRANGE & ACT
        String contenidoMultilinea = "Agradecemos su comentario detallado.\n" +
                "Hemos tomado las siguientes acciones:\n" +
                "1. Revisamos el mantenimiento de la habitación\n" +
                "2. Capacitamos al personal\n" +
                "3. Mejoramos los protocolos de limpieza";

        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                204,
                contenidoMultilinea,
                LocalDateTime.of(2025, 10, 9, 11, 0),
                170L,
                "Departamento de Calidad"
        );

        // ASSERT
        assertThat(respuesta.getContenido()).contains("\n");
        assertThat(respuesta.getContenido()).contains("1.", "2.", "3.");
        assertThat(respuesta.getAutorNombre()).isEqualTo("Departamento de Calidad");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Respuesta con contenido mínimo")
    void casoBorde_RespuestaConContenidoMinimo() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                205,
                "OK",
                LocalDateTime.of(2025, 10, 10, 12, 0),
                175L,
                "A"
        );

        // ASSERT
        assertThat(respuesta.getContenido()).hasSize(2);
        assertThat(respuesta.getAutorNombre()).hasSize(1);
        assertThat(respuesta.getComentarioId()).isEqualTo(175L);
    }

    @Test
    @DisplayName("CASO BORDE - Respuesta sin autor")
    void casoBorde_RespuestaSinAutor() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                206,
                "Respuesta automática del sistema",
                LocalDateTime.now(),
                180L,
                null
        );

        // ASSERT
        assertThat(respuesta.getAutorNombre()).isNull();
        assertThat(respuesta.getContenido()).contains("automática");
        assertThat(respuesta.getComentarioId()).isEqualTo(180L);
    }

    @Test
    @DisplayName("CASO BORDE - Respuesta con fecha muy antigua")
    void casoBorde_RespuestaConFechaMuyAntigua() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                207,
                "Respuesta histórica",
                LocalDateTime.of(2000, 1, 1, 0, 0),
                185L,
                "Sistema Legacy"
        );

        // ASSERT
        assertThat(respuesta.getFechaRespuesta().getYear()).isEqualTo(2000);
        assertThat(respuesta.getContenido()).isEqualTo("Respuesta histórica");
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE - Crear dos objetos IDÉNTICOS
        RespuestaComentarioDTO respuesta1 = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        RespuestaComentarioDTO respuesta2 = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(respuesta1).isEqualTo(respuesta2); // Deben ser iguales
        assertThat(respuesta1.toString()).isNotNull();
        assertThat(respuesta1.hashCode()).isEqualTo(respuesta2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        RespuestaComentarioDTO respuestaVacia = new RespuestaComentarioDTO();
        assertThat(respuestaVacia).isNotNull();

        // Verificar que los getters y setters funcionan
        respuestaVacia.setId(300);
        assertThat(respuestaVacia.getId()).isEqualTo(300);
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ASSERT
        assertThat(respuesta.getId()).isInstanceOf(Integer.class);
        assertThat(respuesta.getContenido()).isInstanceOf(String.class);
        assertThat(respuesta.getFechaRespuesta()).isInstanceOf(LocalDateTime.class);
        assertThat(respuesta.getComentarioId()).isInstanceOf(Long.class);
        assertThat(respuesta.getAutorNombre()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("ESTRUCTURA - Instancia puede ser serializada correctamente")
    void estructura_InstanciaPuedeSerSerializadaCorrectamente() {
        // ARRANGE
        RespuestaComentarioDTO respuesta = new RespuestaComentarioDTO(
                ID_VALIDO, CONTENIDO_VALIDO, FECHA_RESPUESTA_VALIDA, COMENTARIO_ID_VALIDO, AUTOR_NOMBRE_VALIDO
        );

        // ACT - Simular serialización/deserialización
        RespuestaComentarioDTO copia = new RespuestaComentarioDTO(
                respuesta.getId(),
                respuesta.getContenido(),
                respuesta.getFechaRespuesta(),
                respuesta.getComentarioId(),
                respuesta.getAutorNombre()
        );

        // ASSERT
        assertThat(copia).isEqualTo(respuesta);
        assertThat(copia.getContenido()).isEqualTo(respuesta.getContenido());
        assertThat(copia.getFechaRespuesta()).isEqualTo(respuesta.getFechaRespuesta());
    }
}