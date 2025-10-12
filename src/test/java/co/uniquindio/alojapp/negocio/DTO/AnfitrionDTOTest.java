package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para AnfitrionDTO
 */
@DisplayName("AnfitrionDTO - Unit Tests")
public class AnfitrionDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 12;
    private final String NOMBRE_VALIDO = "María López";
    private final String EMAIL_VALIDO = "maria.lopez@correo.com";
    private final String TELEFONO_VALIDO = "+57 3125556677";
    private final int CANTIDAD_ALOJAMIENTOS_VALIDA = 5;
    private final double CALIFICACION_PROMEDIO_VALIDA = 4.8;

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros correctamente")
    void constructorAllArgs_ConTodosLosParametros_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        AnfitrionDTO anfitrion = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ASSERT
        assertThat(anfitrion).isNotNull();
        assertThat(anfitrion.getId()).isEqualTo(ID_VALIDO);
        assertThat(anfitrion.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(anfitrion.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(anfitrion.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(anfitrion.getCantidadAlojamientos()).isEqualTo(CANTIDAD_ALOJAMIENTOS_VALIDA);
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(CALIFICACION_PROMEDIO_VALIDA);
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Con parámetros null se maneja correctamente")
    void constructorAllArgs_ConParametrosNull_SeManejaCorrectamente() {
        // ARRANGE & ACT
        AnfitrionDTO anfitrion = new AnfitrionDTO(null, null, null, null, 0, 0.0);

        // ASSERT
        assertThat(anfitrion).isNotNull();
        assertThat(anfitrion.getId()).isNull();
        assertThat(anfitrion.getNombre()).isNull();
        assertThat(anfitrion.getEmail()).isNull();
        assertThat(anfitrion.getTelefono()).isNull();
        assertThat(anfitrion.getCantidadAlojamientos()).isZero();
        assertThat(anfitrion.getCalificacionPromedio()).isZero();
    }

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ASSERT
        assertThat(anfitrion).isNotNull();
        assertThat(anfitrion.getId()).isNull();
        assertThat(anfitrion.getNombre()).isNull();
        assertThat(anfitrion.getEmail()).isNull();
        assertThat(anfitrion.getTelefono()).isNull();
        assertThat(anfitrion.getCantidadAlojamientos()).isZero();
        assertThat(anfitrion.getCalificacionPromedio()).isZero();
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setId(ID_VALIDO);
        anfitrion.setNombre(NOMBRE_VALIDO);
        anfitrion.setEmail(EMAIL_VALIDO);
        anfitrion.setTelefono(TELEFONO_VALIDO);
        anfitrion.setCantidadAlojamientos(CANTIDAD_ALOJAMIENTOS_VALIDA);
        anfitrion.setCalificacionPromedio(CALIFICACION_PROMEDIO_VALIDA);

        // ASSERT
        assertThat(anfitrion.getId()).isEqualTo(ID_VALIDO);
        assertThat(anfitrion.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(anfitrion.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(anfitrion.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(anfitrion.getCantidadAlojamientos()).isEqualTo(CANTIDAD_ALOJAMIENTOS_VALIDA);
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(CALIFICACION_PROMEDIO_VALIDA);
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos vacíos se manejan correctamente")
    void gettersYSetters_CamposVacios_SeManejanCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setNombre("");
        anfitrion.setEmail("");
        anfitrion.setTelefono("");

        // ASSERT
        assertThat(anfitrion.getNombre()).isEmpty();
        assertThat(anfitrion.getEmail()).isEmpty();
        assertThat(anfitrion.getTelefono()).isEmpty();
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos con espacios en blanco se mantienen")
    void gettersYSetters_CamposConEspaciosEnBlanco_SeMantienen() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();
        String nombreConEspacios = "  María  López  ";
        String emailConEspacios = "  maria@test.com  ";
        String telefonoConEspacios = "  +57 123 456 789  ";

        // ACT
        anfitrion.setNombre(nombreConEspacios);
        anfitrion.setEmail(emailConEspacios);
        anfitrion.setTelefono(telefonoConEspacios);

        // ASSERT
        assertThat(anfitrion.getNombre()).isEqualTo(nombreConEspacios);
        assertThat(anfitrion.getEmail()).isEqualTo(emailConEspacios);
        assertThat(anfitrion.getTelefono()).isEqualTo(telefonoConEspacios);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        AnfitrionDTO anfitrion1 = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        AnfitrionDTO anfitrion2 = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ACT & ASSERT
        assertThat(anfitrion1).isEqualTo(anfitrion2);
        assertThat(anfitrion1.hashCode()).isEqualTo(anfitrion2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        AnfitrionDTO anfitrion1 = new AnfitrionDTO(1, NOMBRE_VALIDO, EMAIL_VALIDO,
                TELEFONO_VALIDO, CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA);

        AnfitrionDTO anfitrion2 = new AnfitrionDTO(2, NOMBRE_VALIDO, EMAIL_VALIDO,
                TELEFONO_VALIDO, CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA);

        // ACT & ASSERT
        assertThat(anfitrion1).isNotEqualTo(anfitrion2);
    }

    @Test
    @DisplayName("EQUALS - Misma instancia retorna true")
    void equals_MismaInstancia_RetornaTrue() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ACT & ASSERT
        assertThat(anfitrion).isEqualTo(anfitrion);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ACT & ASSERT
        assertThat(anfitrion).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(anfitrion).isNotEqualTo(objetoDiferente);
    }

    @Test
    @DisplayName("EQUALS - Campos diferentes excepto ID retorna false")
    void equals_CamposDiferentesExceptoId_RetornaFalse() {
        // ARRANGE
        AnfitrionDTO anfitrion1 = new AnfitrionDTO(
                ID_VALIDO, "María López", "maria@test.com", "+57 111111111", 5, 4.8
        );

        AnfitrionDTO anfitrion2 = new AnfitrionDTO(
                ID_VALIDO, "Juan Pérez", "juan@test.com", "+57 222222222", 3, 4.5
        );

        // ACT & ASSERT
        // Con @Data de Lombok, equals compara TODOS los campos, no solo el ID
        assertThat(anfitrion1).isNotEqualTo(anfitrion2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del anfitrión")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ACT
        String resultado = anfitrion.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(NOMBRE_VALIDO);
        assertThat(resultado).contains(EMAIL_VALIDO);
        assertThat(resultado).contains(String.valueOf(CANTIDAD_ALOJAMIENTOS_VALIDA));
        assertThat(resultado).contains(String.valueOf(CALIFICACION_PROMEDIO_VALIDA));
    }

    @Test
    @DisplayName("TO STRING - Con campos null se maneja correctamente")
    void toString_ConCamposNull_SeManejaCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO(null, null, null, null, 0, 0.0);

        // ACT
        String resultado = anfitrion.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
    }

    // ==================== VALIDACIÓN DE DATOS TESTS ====================

    @Test
    @DisplayName("VALIDACIÓN - Email con formato válido es aceptado")
    void validacion_EmailConFormatoValido_EsAceptado() {
        // ARRANGE
        String emailValido = "usuario.valido+alias@dominio.co.uk";
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setEmail(emailValido);

        // ASSERT
        assertThat(anfitrion.getEmail()).isEqualTo(emailValido);
    }

    @Test
    @DisplayName("VALIDACIÓN - Teléfono con formato internacional es aceptado")
    void validacion_TelefonoConFormatoInternacional_EsAceptado() {
        // ARRANGE
        String telefonoInternacional = "+34 912 345 678";
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setTelefono(telefonoInternacional);

        // ASSERT
        assertThat(anfitrion.getTelefono()).isEqualTo(telefonoInternacional);
    }

    @Test
    @DisplayName("VALIDACIÓN - Números de teléfono con diferentes formatos son aceptados")
    void validacion_TelefonosConDiferentesFormatos_SonAceptados() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();
        String telefonoConGuiones = "+57-312-555-6677";
        String telefonoConEspacios = "+57 312 555 6677";
        String telefonoSinEspacios = "+573125556677";

        // ACT & ASSERT
        anfitrion.setTelefono(telefonoConGuiones);
        assertThat(anfitrion.getTelefono()).isEqualTo(telefonoConGuiones);

        anfitrion.setTelefono(telefonoConEspacios);
        assertThat(anfitrion.getTelefono()).isEqualTo(telefonoConEspacios);

        anfitrion.setTelefono(telefonoSinEspacios);
        assertThat(anfitrion.getTelefono()).isEqualTo(telefonoSinEspacios);
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Cantidad de alojamientos negativa se maneja correctamente")
    void casoBorde_CantidadAlojamientosNegativa_SeManejaCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setCantidadAlojamientos(-5);

        // ASSERT
        assertThat(anfitrion.getCantidadAlojamientos()).isEqualTo(-5);
    }

    @Test
    @DisplayName("CASO BORDE - Calificación promedio fuera de rango se maneja correctamente")
    void casoBorde_CalificacionPromedioFueraDeRango_SeManejaCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setCalificacionPromedio(-1.5); // Por debajo del mínimo esperado
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(-1.5);

        anfitrion.setCalificacionPromedio(10.0); // Por encima del máximo esperado
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(10.0);
    }

    @Test
    @DisplayName("CASO BORDE - Calificación promedio con precisión decimal se maneja correctamente")
    void casoBorde_CalificacionPromedioConPrecisionDecimal_SeManejaCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setCalificacionPromedio(4.75);
        anfitrion.setCalificacionPromedio(3.333333);
        anfitrion.setCalificacionPromedio(2.1);

        // ASSERT
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(2.1);
    }

    @Test
    @DisplayName("CASO BORDE - Nombre muy largo se maneja correctamente")
    void casoBorde_NombreMuyLargo_SeManejaCorrectamente() {
        // ARRANGE
        String nombreLargo = "A".repeat(500);
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setNombre(nombreLargo);

        // ASSERT
        assertThat(anfitrion.getNombre()).isEqualTo(nombreLargo);
        assertThat(anfitrion.getNombre()).hasSize(500);
    }

    @Test
    @DisplayName("CASO BORDE - Campos con valores límite se manejan correctamente")
    void casoBorde_CamposConValoresLimite_SeManejanCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setCantidadAlojamientos(Integer.MAX_VALUE);
        anfitrion.setCalificacionPromedio(Double.MAX_VALUE);

        // ASSERT
        assertThat(anfitrion.getCantidadAlojamientos()).isEqualTo(Integer.MAX_VALUE);
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(Double.MAX_VALUE);
    }

    @Test
    @DisplayName("CASO BORDE - Valores cero se manejan correctamente")
    void casoBorde_ValoresCero_SeManejanCorrectamente() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT
        anfitrion.setCantidadAlojamientos(0);
        anfitrion.setCalificacionPromedio(0.0);

        // ASSERT
        assertThat(anfitrion.getCantidadAlojamientos()).isZero();
        assertThat(anfitrion.getCalificacionPromedio()).isZero();
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        AnfitrionDTO anfitrion1 = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        AnfitrionDTO anfitrion2 = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(anfitrion1).isEqualTo(anfitrion2);
        assertThat(anfitrion1.toString()).isNotNull();
        assertThat(anfitrion1.hashCode()).isEqualTo(anfitrion2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        AnfitrionDTO anfitrionVacio = new AnfitrionDTO();
        assertThat(anfitrionVacio).isNotNull();
    }

    // ==================== PRUEBAS DE INTEGRIDAD DE DATOS ====================

    @Test
    @DisplayName("INTEGRIDAD - Los datos se mantienen consistentes después de múltiples operaciones")
    void integridad_DatosSeMantienenConsistentes() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO();

        // ACT - Múltiples operaciones de setters
        anfitrion.setId(1);
        anfitrion.setNombre("Nombre Inicial");
        anfitrion.setEmail("inicial@test.com");
        anfitrion.setTelefono("111111111");
        anfitrion.setCantidadAlojamientos(10);
        anfitrion.setCalificacionPromedio(4.5);

        // Cambiar algunos valores
        anfitrion.setNombre("Nombre Final");
        anfitrion.setCantidadAlojamientos(15);
        anfitrion.setCalificacionPromedio(4.8);

        // ASSERT - Verificar que los valores finales son correctos
        assertThat(anfitrion.getId()).isEqualTo(1);
        assertThat(anfitrion.getNombre()).isEqualTo("Nombre Final");
        assertThat(anfitrion.getEmail()).isEqualTo("inicial@test.com"); // No cambió
        assertThat(anfitrion.getTelefono()).isEqualTo("111111111"); // No cambió
        assertThat(anfitrion.getCantidadAlojamientos()).isEqualTo(15);
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(4.8);
    }

    @Test
    @DisplayName("INTEGRIDAD - El objeto es inmutable una vez configurado (comportamiento esperado)")
    void integridad_ObjetoEsConsistenteUnaVezConfigurado() {
        // ARRANGE
        AnfitrionDTO anfitrion = new AnfitrionDTO(
                ID_VALIDO, NOMBRE_VALIDO, EMAIL_VALIDO, TELEFONO_VALIDO,
                CANTIDAD_ALOJAMIENTOS_VALIDA, CALIFICACION_PROMEDIO_VALIDA
        );

        // ACT - No cambiar nada

        // ASSERT - Los valores deben mantenerse iguales
        assertThat(anfitrion.getId()).isEqualTo(ID_VALIDO);
        assertThat(anfitrion.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(anfitrion.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(anfitrion.getTelefono()).isEqualTo(TELEFONO_VALIDO);
        assertThat(anfitrion.getCantidadAlojamientos()).isEqualTo(CANTIDAD_ALOJAMIENTOS_VALIDA);
        assertThat(anfitrion.getCalificacionPromedio()).isEqualTo(CALIFICACION_PROMEDIO_VALIDA);
    }
}