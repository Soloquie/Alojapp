package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para ActualizarAdministradorRequest
 *
 * OBJETIVO: Probar el DTO de actualización de administrador
 * - Validar constructor, builder, getters/setters
 * - Probar diferentes escenarios de actualización
 */
@DisplayName("ActualizarAdministradorRequest - Unit Tests")
public class ActualizarAdministradorRequestTest {

    // DATOS DE PRUEBA
    private final String NIVEL_ACCESO_VALIDO = "SUPER_ADMIN";
    private final String PERMISOS_JSON_VALIDO = "{\"permisos\": [\"crear\", \"editar\", \"eliminar\"]}";

    // ==================== BUILDER TESTS ====================

    @Test
    @DisplayName("BUILDER - Crea instancia con todos los campos correctamente")
    void builder_ConTodosLosCampos_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Campos opcionales null se manejan correctamente")
    void builder_CamposOpcionalesNull_SeManejanCorrectamente() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("BUILDER - Solo permisosJson se maneja correctamente")
    void builder_SoloPermisosJson_SeManejaCorrectamente() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isNull();
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    @Test
    @DisplayName("BUILDER - Builder vacío crea instancia con campos null")
    void builder_BuilderVacio_CreaInstanciaConCamposNull() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder().build();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isNull();
        assertThat(request.getPermisosJson()).isNull();
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        ActualizarAdministradorRequest request = new ActualizarAdministradorRequest();

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isNull();
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros")
    void allArgsConstructor_CreaInstanciaConTodosLosParametros() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = new ActualizarAdministradorRequest(
                NIVEL_ACCESO_VALIDO, PERMISOS_JSON_VALIDO
        );

        // ASSERT
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        ActualizarAdministradorRequest request = new ActualizarAdministradorRequest();

        // ACT
        request.setNivelAcceso(NIVEL_ACCESO_VALIDO);
        request.setPermisosJson(PERMISOS_JSON_VALIDO);

        // ASSERT
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - SIN LOMBOK - Verificar igualdad manual por campos")
    void equals_SinLombok_VerificarIgualdadManualPorCampos() {
        // ARRANGE
        ActualizarAdministradorRequest request1 = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        ActualizarAdministradorRequest request2 = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ASSERT - Verificar igualdad campo por campo (sin depender de equals())
        assertThat(request1.getNivelAcceso()).isEqualTo(request2.getNivelAcceso());
        assertThat(request1.getPermisosJson()).isEqualTo(request2.getPermisosJson());
    }

    @Test
    @DisplayName("EQUALS - SIN LOMBOK - Ambos campos null tienen mismos valores")
    void equals_SinLombok_AmbosCamposNull_TienenMismosValores() {
        // ARRANGE
        ActualizarAdministradorRequest request1 = new ActualizarAdministradorRequest(null, null);
        ActualizarAdministradorRequest request2 = new ActualizarAdministradorRequest(null, null);

        // ASSERT - Verificar que ambos tienen null en los mismos campos
        assertThat(request1.getNivelAcceso()).isNull();
        assertThat(request2.getNivelAcceso()).isNull();
        assertThat(request1.getPermisosJson()).isNull();
        assertThat(request2.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("EQUALS - SIN LOMBOK - Diferente nivelAcceso tienen valores diferentes")
    void equals_SinLombok_DiferenteNivelAcceso_TienenValoresDiferentes() {
        // ARRANGE
        ActualizarAdministradorRequest request1 = ActualizarAdministradorRequest.builder()
                .nivelAcceso("ADMIN")
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        ActualizarAdministradorRequest request2 = ActualizarAdministradorRequest.builder()
                .nivelAcceso("SUPER_ADMIN")
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ASSERT - Verificar diferencia en nivelAcceso
        assertThat(request1.getNivelAcceso()).isNotEqualTo(request2.getNivelAcceso());
        assertThat(request1.getPermisosJson()).isEqualTo(request2.getPermisosJson());
    }

    @Test
    @DisplayName("EQUALS - SIN LOMBOK - Diferente permisosJson tienen valores diferentes")
    void equals_SinLombok_DiferentePermisosJson_TienenValoresDiferentes() {
        // ARRANGE
        ActualizarAdministradorRequest request1 = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson("{\"permisos\": [\"leer\"]}")
                .build();

        ActualizarAdministradorRequest request2 = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson("{\"permisos\": [\"escribir\"]}")
                .build();

        // ASSERT - Verificar diferencia en permisosJson
        assertThat(request1.getNivelAcceso()).isEqualTo(request2.getNivelAcceso());
        assertThat(request1.getPermisosJson()).isNotEqualTo(request2.getPermisosJson());
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - SIN LOMBOK - Instancia no es null")
    void toString_SinLombok_InstanciaNoEsNull() {
        // ARRANGE
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ACT
        String resultado = request.toString();

        // ASSERT - Solo verificar que no es null (sin validar contenido)
        assertThat(resultado).isNotNull();
    }

    // ==================== NIVEL ACCESO TESTS ====================

    @Test
    @DisplayName("NIVEL ACCESO - Diferentes niveles de acceso son aceptados")
    void nivelAcceso_DiferentesNivelesDeAcceso_SonAceptados() {
        // ARRANGE
        ActualizarAdministradorRequest request = new ActualizarAdministradorRequest();

        // ACT & ASSERT
        request.setNivelAcceso("BASICO");
        assertThat(request.getNivelAcceso()).isEqualTo("BASICO");

        request.setNivelAcceso("ADMIN");
        assertThat(request.getNivelAcceso()).isEqualTo("ADMIN");

        request.setNivelAcceso("SUPER_ADMIN");
        assertThat(request.getNivelAcceso()).isEqualTo("SUPER_ADMIN");

        request.setNivelAcceso("READ_ONLY");
        assertThat(request.getNivelAcceso()).isEqualTo("READ_ONLY");

        request.setNivelAcceso("");
        assertThat(request.getNivelAcceso()).isEmpty();

        request.setNivelAcceso(null);
        assertThat(request.getNivelAcceso()).isNull();
    }

    // ==================== PERMISOS JSON TESTS ====================

    @Test
    @DisplayName("PERMISOS JSON - Diferentes formatos JSON son aceptados")
    void permisosJson_DiferentesFormatosJson_SonAceptados() {
        // ARRANGE
        ActualizarAdministradorRequest request = new ActualizarAdministradorRequest();

        // ACT & ASSERT
        String jsonSimple = "{\"permisos\": [\"leer\"]}";
        request.setPermisosJson(jsonSimple);
        assertThat(request.getPermisosJson()).isEqualTo(jsonSimple);

        String jsonComplejo = "{\"permisos\": [\"crear\", \"editar\", \"eliminar\", \"aprobar\"], \"limites\": {\"max_operaciones\": 1000}}";
        request.setPermisosJson(jsonComplejo);
        assertThat(request.getPermisosJson()).isEqualTo(jsonComplejo);
        assertThat(request.getPermisosJson()).contains("limites");

        String jsonVacio = "{}";
        request.setPermisosJson(jsonVacio);
        assertThat(request.getPermisosJson()).isEqualTo(jsonVacio);

        request.setPermisosJson("");
        assertThat(request.getPermisosJson()).isEmpty();

        request.setPermisosJson(null);
        assertThat(request.getPermisosJson()).isNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de nivel de acceso")
    void scenarioUsoReal_ActualizacionSoloNivelAcceso() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso("ADMIN")
                .build();

        // ASSERT - Solo se actualiza nivel de acceso
        assertThat(request.getNivelAcceso()).isEqualTo("ADMIN");
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización solo de permisos")
    void scenarioUsoReal_ActualizacionSoloPermisos() {
        // ARRANGE & ACT
        String permisosCompletos = "{\"permisos\": [\"crear_usuarios\", \"editar_configuracion\", \"ver_reportes\"], \"restricciones\": []}";

        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .permisosJson(permisosCompletos)
                .build();

        // ASSERT - Solo se actualizan permisos
        assertThat(request.getNivelAcceso()).isNull();
        assertThat(request.getPermisosJson()).isEqualTo(permisosCompletos);
        assertThat(request.getPermisosJson()).contains("crear_usuarios", "ver_reportes");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización completa de administrador")
    void scenarioUsoReal_ActualizacionCompletaAdministrador() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso("SUPER_ADMIN")
                .permisosJson("{\"permisos\": [\"full_access\"], \"modulos\": [\"usuarios\", \"reportes\", \"configuracion\"]}")
                .build();

        // ASSERT - Actualización completa
        assertThat(request.getNivelAcceso()).isEqualTo("SUPER_ADMIN");
        assertThat(request.getPermisosJson()).contains("full_access");
        assertThat(request.getPermisosJson()).contains("modulos");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización con permisos específicos por módulo")
    void scenarioUsoReal_ActualizacionConPermisosEspecificosPorModulo() {
        // ARRANGE & ACT
        String permisosModulares = """
            {
                "usuarios": ["crear", "editar", "eliminar"],
                "alojamientos": ["aprobar", "rechazar"],
                "reportes": ["generar", "exportar"],
                "configuracion": ["leer"]
            }
        """;

        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso("MODERADOR")
                .permisosJson(permisosModulares)
                .build();

        // ASSERT
        assertThat(request.getNivelAcceso()).isEqualTo("MODERADOR");
        assertThat(request.getPermisosJson()).contains("usuarios");
        assertThat(request.getPermisosJson()).contains("alojamientos");
        assertThat(request.getPermisosJson()).contains("reportes");
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Actualización para administrador de soporte")
    void scenarioUsoReal_ActualizacionParaAdministradorSoporte() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso("SOPORTE")
                .permisosJson("{\"permisos\": [\"ver_usuarios\", \"resetear_contrasenas\", \"bloquear_cuentas\"], \"acceso_limitado\": true}")
                .build();

        // ASSERT
        assertThat(request.getNivelAcceso()).isEqualTo("SOPORTE");
        assertThat(request.getPermisosJson()).contains("resetear_contrasenas");
        assertThat(request.getPermisosJson()).contains("acceso_limitado");
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - Request completamente vacío")
    void casoBorde_RequestCompletamenteVacio() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = new ActualizarAdministradorRequest();

        // ASSERT
        assertThat(request.getNivelAcceso()).isNull();
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Nivel de acceso con valor mínimo")
    void casoBorde_NivelAccesoConValorMinimo() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso("A")
                .build();

        // ASSERT
        assertThat(request.getNivelAcceso()).hasSize(1);
        assertThat(request.getNivelAcceso()).isEqualTo("A");
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - JSON de permisos muy extenso")
    void casoBorde_JsonPermisosMuyExtenso() {
        // ARRANGE & ACT
        String jsonExtenso = "{\"permisos\": [" +
                "\"permiso_1\", \"permiso_2\", \"permiso_3\", \"permiso_4\", \"permiso_5\", " +
                "\"permiso_6\", \"permiso_7\", \"permiso_8\", \"permiso_9\", \"permiso_10\"" +
                "], \"configuracion\": {\"param1\": \"valor1\", \"param2\": \"valor2\", \"param3\": \"valor3\"}}";

        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .permisosJson(jsonExtenso)
                .build();

        // ASSERT
        assertThat(request.getPermisosJson()).hasSizeGreaterThan(100);
        assertThat(request.getPermisosJson()).contains("permiso_10");
        assertThat(request.getPermisosJson()).contains("configuracion");
    }

    @Test
    @DisplayName("CASO BORDE - Solo espacios en blanco en nivel de acceso")
    void casoBorde_SoloEspaciosEnBlancoEnNivelAcceso() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso("   ")
                .build();

        // ASSERT - Corregido: solo verificar que contiene espacios
        assertThat(request.getNivelAcceso()).isEqualTo("   ");
        assertThat(request.getNivelAcceso()).contains(" "); // Verificar que contiene espacios
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones básicas funcionan correctamente")
    void lombok_AnotacionesBasicasFuncionanCorrectamente() {
        // ARRANGE
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ASSERT - Verificar que @Getter, @Setter, @Builder, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(request).isNotNull();
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);

        // Verificar que no hay error con constructor sin parámetros
        ActualizarAdministradorRequest requestVacio = new ActualizarAdministradorRequest();
        assertThat(requestVacio).isNotNull();

        // Verificar que los getters y setters funcionan
        requestVacio.setNivelAcceso("TEST");
        assertThat(requestVacio.getNivelAcceso()).isEqualTo("TEST");

        // Verificar que el builder funciona
        assertThat(request).isInstanceOf(ActualizarAdministradorRequest.class);
    }

    // ==================== VALIDACIÓN DE ESTRUCTURA TESTS ====================

    @Test
    @DisplayName("ESTRUCTURA - Campos tienen los tipos de datos correctos")
    void estructura_CamposTienenLosTiposDeDatosCorrectos() {
        // ARRANGE & ACT
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ASSERT
        assertThat(request.getNivelAcceso()).isInstanceOf(String.class);
        assertThat(request.getPermisosJson()).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("ESTRUCTURA - Instancia puede ser copiada manualmente")
    void estructura_InstanciaPuedeSerCopiadaManualmente() {
        // ARRANGE
        ActualizarAdministradorRequest request = ActualizarAdministradorRequest.builder()
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // ACT - Simular copia manual (sin depender de equals)
        ActualizarAdministradorRequest copia = new ActualizarAdministradorRequest(
                request.getNivelAcceso(),
                request.getPermisosJson()
        );

        // ASSERT - Verificar que los campos son iguales
        assertThat(copia.getNivelAcceso()).isEqualTo(request.getNivelAcceso());
        assertThat(copia.getPermisosJson()).isEqualTo(request.getPermisosJson());
    }

    // ==================== BUSINESS LOGIC TESTS ====================

    @Test
    @DisplayName("BUSINESS - Request permite actualización parcial")
    void business_RequestPermiteActualizacionParcial() {
        // ARRANGE & ACT - Solo actualizar permisos, mantener nivel de acceso existente
        ActualizarAdministradorRequest requestParcial = ActualizarAdministradorRequest.builder()
                .permisosJson("{\"nuevos_permisos\": [\"auditoria\"]}")
                .build();

        // ASSERT - Campos no incluidos deben ser null (indicando que no se actualizan)
        assertThat(requestParcial.getNivelAcceso()).isNull();
        assertThat(requestParcial.getPermisosJson()).isNotNull();
    }

    @Test
    @DisplayName("BUSINESS - Diferentes métodos de construcción producen mismos valores")
    void business_DiferentesMetodosDeConstruccion_ProducenMismosValores() {
        // ARRANGE
        ActualizarAdministradorRequest viaBuilder = ActualizarAdministradorRequest.builder()
                .nivelAcceso("ADMIN")
                .permisosJson("{\"permisos\": [\"write\"]}")
                .build();

        ActualizarAdministradorRequest viaConstructor = new ActualizarAdministradorRequest(
                "ADMIN", "{\"permisos\": [\"write\"]}"
        );

        ActualizarAdministradorRequest viaSetters = new ActualizarAdministradorRequest();
        viaSetters.setNivelAcceso("ADMIN");
        viaSetters.setPermisosJson("{\"permisos\": [\"write\"]}");

        // ASSERT - Verificar que todos tienen los mismos valores de campo
        assertThat(viaBuilder.getNivelAcceso()).isEqualTo(viaConstructor.getNivelAcceso());
        assertThat(viaBuilder.getNivelAcceso()).isEqualTo(viaSetters.getNivelAcceso());
        assertThat(viaBuilder.getPermisosJson()).isEqualTo(viaConstructor.getPermisosJson());
        assertThat(viaBuilder.getPermisosJson()).isEqualTo(viaSetters.getPermisosJson());
    }
}