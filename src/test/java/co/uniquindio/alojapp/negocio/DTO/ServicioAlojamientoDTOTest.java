package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ServicioAlojamientoDTO
 *
 * OBJETIVO: Probar el DTO de servicios de alojamiento
 * - Validar constructor, getters/setters
 * - Verificar equals/hashCode
 * - Probar diferentes escenarios de uso
 */
@DisplayName("ServicioAlojamientoDTO - Unit Tests")
public class ServicioAlojamientoDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 7;
    private final String NOMBRE_VALIDO = "Piscina privada";
    private final String DESCRIPCION_VALIDA = "Piscina climatizada con vista al mar";

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO();

        // ASSERT
        assertThat(servicio).isNotNull();
        assertThat(servicio.getId()).isNull();
        assertThat(servicio.getNombre()).isNull();
        assertThat(servicio.getDescripcion()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ASSERT
        assertThat(servicio).isNotNull();
        assertThat(servicio.getId()).isEqualTo(ID_VALIDO);
        assertThat(servicio.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(servicio.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO();

        // ACT
        servicio.setId(ID_VALIDO);
        servicio.setNombre(NOMBRE_VALIDO);
        servicio.setDescripcion(DESCRIPCION_VALIDA);

        // ASSERT
        assertThat(servicio.getId()).isEqualTo(ID_VALIDO);
        assertThat(servicio.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(servicio.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en TODOS los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT & ASSERT
        assertThat(servicio1).isEqualTo(servicio2);
        assertThat(servicio1.hashCode()).isEqualTo(servicio2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                1, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                2, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT & ASSERT
        assertThat(servicio1).isNotEqualTo(servicio2);
    }

    @Test
    @DisplayName("EQUALS - Diferente nombre retorna false")
    void equals_DiferenteNombre_RetornaFalse() {
        // ARRANGE
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                ID_VALIDO, "Piscina privada", DESCRIPCION_VALIDA
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                ID_VALIDO, "Wi-Fi gratuito", DESCRIPCION_VALIDA
        );

        // ACT & ASSERT
        assertThat(servicio1).isNotEqualTo(servicio2);
    }

    @Test
    @DisplayName("EQUALS - Diferente descripción retorna false")
    void equals_DiferenteDescripcion_RetornaFalse() {
        // ARRANGE
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, "Piscina climatizada con vista al mar"
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, "Piscina exterior sin climatizar"
        );

        // ACT & ASSERT
        assertThat(servicio1).isNotEqualTo(servicio2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT & ASSERT
        assertThat(servicio).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT & ASSERT
        assertThat(servicio).isNotEqualTo("No soy un servicio");
    }

    @Test
    @DisplayName("EQUALS - Ambos objetos null en ID son iguales")
    void equals_AmbosObjetosNullEnId_SonIguales() {
        // ARRANGE
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                null, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                null, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT & ASSERT
        assertThat(servicio1).isEqualTo(servicio2);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del servicio")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT
        String resultado = servicio.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(NOMBRE_VALIDO);
        assertThat(resultado).contains(DESCRIPCION_VALIDA);
    }

    // ==================== NOMBRE TESTS ====================

    @Test
    @DisplayName("NOMBRE - Diferentes nombres de servicio son aceptados")
    void nombre_DiferentesNombresDeServicio_SonAceptados() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO();

        // ACT & ASSERT
        servicio.setNombre("Wi-Fi");
        assertThat(servicio.getNombre()).isEqualTo("Wi-Fi");

        servicio.setNombre("Estacionamiento gratuito");
        assertThat(servicio.getNombre()).isEqualTo("Estacionamiento gratuito");

        servicio.setNombre("Desayuno buffet incluido");
        assertThat(servicio.getNombre()).hasSizeGreaterThan(10);

        servicio.setNombre("");
        assertThat(servicio.getNombre()).isEmpty();

        servicio.setNombre(null);
        assertThat(servicio.getNombre()).isNull();
    }

    // ==================== DESCRIPCIÓN TESTS ====================

    @Test
    @DisplayName("DESCRIPCIÓN - Diferentes descripciones son aceptadas")
    void descripcion_DiferentesDescripciones_SonAceptadas() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO();

        // ACT & ASSERT
        servicio.setDescripcion("Servicio básico");
        assertThat(servicio.getDescripcion()).isEqualTo("Servicio básico");

        servicio.setDescripcion("Amplio estacionamiento cubierto con vigilancia 24/7, capacidad para vehículos grandes y punto de carga para autos eléctricos");
        assertThat(servicio.getDescripcion()).hasSizeGreaterThan(50);

        servicio.setDescripcion("");
        assertThat(servicio.getDescripcion()).isEmpty();

        servicio.setDescripcion(null);
        assertThat(servicio.getDescripcion()).isNull();
    }

    // ==================== ID TESTS ====================

    @Test
    @DisplayName("ID - Diferentes IDs son aceptados")
    void id_DiferentesIds_SonAceptados() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO();

        // ACT & ASSERT
        servicio.setId(1);
        assertThat(servicio.getId()).isEqualTo(1);

        servicio.setId(999);
        assertThat(servicio.getId()).isEqualTo(999);

        servicio.setId(0);
        assertThat(servicio.getId()).isZero();

        servicio.setId(null);
        assertThat(servicio.getId()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Servicio de alojamiento básico")
    void scenarioUsoReal_ServicioAlojamientoBasico() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                1,
                "Wi-Fi gratuito",
                "Conexión a internet de alta velocidad en todas las áreas del alojamiento"
        );

        // ASSERT
        assertThat(servicio.getId()).isEqualTo(1);
        assertThat(servicio.getNombre()).isEqualTo("Wi-Fi gratuito");
        assertThat(servicio.getDescripcion()).contains("internet", "alta velocidad");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Servicio de lujo")
    void scenarioUsoReal_ServicioDeLujo() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                15,
                "Spa completo",
                "Acceso ilimitado a sauna finlandesa, baño turco, jacuzzi y tratamientos de belleza personalizados"
        );

        // ASSERT
        assertThat(servicio.getId()).isEqualTo(15);
        assertThat(servicio.getNombre()).isEqualTo("Spa completo");
        assertThat(servicio.getDescripcion()).contains("sauna", "jacuzzi", "tratamientos");
        assertThat(servicio.getDescripcion()).hasSizeGreaterThan(30);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Servicio para familias")
    void scenarioUsoReal_ServicioParaFamilias() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                22,
                "Zona infantil",
                "Área de juegos segura para niños con supervisión, actividades recreativas y menú infantil especial"
        );

        // ASSERT
        assertThat(servicio.getNombre()).isEqualTo("Zona infantil");
        assertThat(servicio.getDescripcion()).contains("niños", "juegos", "segura");
        assertThat(servicio.getId()).isEqualTo(22);
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Servicio sin descripción")
    void scenarioUsoReal_ServicioSinDescripcion() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                30,
                "Aire acondicionado",
                null
        );

        // ASSERT
        assertThat(servicio.getId()).isEqualTo(30);
        assertThat(servicio.getNombre()).isEqualTo("Aire acondicionado");
        assertThat(servicio.getDescripcion()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Nuevo servicio sin ID")
    void scenarioUsoReal_NuevoServicioSinId() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO nuevoServicio = new ServicioAlojamientoDTO(
                null,
                "Servicio de lavandería",
                "Lavado y planchado de ropa con recogida y entrega en la habitación"
        );

        // ASSERT - Nuevo servicio sin ID asignado
        assertThat(nuevoServicio.getId()).isNull();
        assertThat(nuevoServicio.getNombre()).isEqualTo("Servicio de lavandería");

        assertThat(nuevoServicio.getDescripcion()).contains("Lavado", "planchado");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Servicio con nombre mínimo")
    void casoBorde_ServicioConNombreMinimo() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                99,
                "TV",
                "Televisor pantalla plana"
        );

        // ASSERT
        assertThat(servicio.getNombre()).hasSize(2);
        assertThat(servicio.getId()).isEqualTo(99);
        assertThat(servicio.getDescripcion()).isEqualTo("Televisor pantalla plana");
    }

    @Test
    @DisplayName("CASO BORDE - Servicio sin nombre")
    void casoBorde_ServicioSinNombre() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                100,
                null,
                "Servicio sin nombre específico"
        );

        // ASSERT
        assertThat(servicio.getNombre()).isNull();
        assertThat(servicio.getId()).isEqualTo(100);
        assertThat(servicio.getDescripcion()).isNotNull();
    }

    @Test
    @DisplayName("CASO BORDE - Servicio con ID cero")
    void casoBorde_ServicioConIdCero() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                0,
                "Servicio temporal",
                "Servicio con ID no asignado"
        );

        // ASSERT
        assertThat(servicio.getId()).isZero();
        assertThat(servicio.getNombre()).isEqualTo("Servicio temporal");
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE - Crear dos objetos IDÉNTICOS
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(servicio1).isEqualTo(servicio2);
        assertThat(servicio1.toString()).isNotNull();
        assertThat(servicio1.hashCode()).isEqualTo(servicio2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        ServicioAlojamientoDTO servicioVacio = new ServicioAlojamientoDTO();
        assertThat(servicioVacio).isNotNull();

        // Verificar que los getters y setters funcionan
        servicioVacio.setId(50);
        assertThat(servicioVacio.getId()).isEqualTo(50);
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ASSERT
        assertThat(servicio.getId()).isInstanceOf(Integer.class);
        assertThat(servicio.getNombre()).isInstanceOf(String.class);
        assertThat(servicio.getDescripcion()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("ESTRUCTURA - Instancia puede ser serializada correctamente")
    void estructura_InstanciaPuedeSerSerializadaCorrectamente() {
        // ARRANGE
        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                ID_VALIDO, NOMBRE_VALIDO, DESCRIPCION_VALIDA
        );

        // ACT - Simular serialización/deserialización
        ServicioAlojamientoDTO copia = new ServicioAlojamientoDTO(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion()
        );

        // ASSERT
        assertThat(copia).isEqualTo(servicio);
        assertThat(copia.getNombre()).isEqualTo(servicio.getNombre());
        assertThat(copia.getDescripcion()).isEqualTo(servicio.getDescripcion());
    }

    // ==================== BUSINESS LOGIC TESTS ====================

    @Test
    @DisplayName("BUSINESS - Servicio con descripción larga es aceptado")
    void business_ServicioConDescripcionLarga_EsAceptado() {
        // ARRANGE & ACT
        String descripcionLarga = "Este servicio incluye acceso completo a todas las instalaciones del resort: " +
                "piscina principal, piscina infantil, jacuzzi, gimnasio equipado con máquinas de última generación, " +
                "sauna seco y húmedo, área de descanso con hamacas y servicio de toallas ilimitado.";

        ServicioAlojamientoDTO servicio = new ServicioAlojamientoDTO(
                25,
                "Acceso completo al resort",
                descripcionLarga
        );

        // ASSERT
        assertThat(servicio.getDescripcion()).hasSizeGreaterThan(100);
        assertThat(servicio.getDescripcion()).contains("piscina", "gimnasio", "sauna");
    }

    @Test
    @DisplayName("BUSINESS - Servicios con nombres similares pero diferentes IDs son distintos")
    void business_ServiciosConNombresSimilaresPeroDiferentesIds_SonDistintos() {
        // ARRANGE
        ServicioAlojamientoDTO servicio1 = new ServicioAlojamientoDTO(
                1, "Piscina", "Piscina exterior"
        );

        ServicioAlojamientoDTO servicio2 = new ServicioAlojamientoDTO(
                2, "Piscina", "Piscina interior climatizada"
        );

        // ACT & ASSERT
        assertThat(servicio1).isNotEqualTo(servicio2);
        assertThat(servicio1.getNombre()).isEqualTo(servicio2.getNombre());
        assertThat(servicio1.getId()).isNotEqualTo(servicio2.getId());
    }
}