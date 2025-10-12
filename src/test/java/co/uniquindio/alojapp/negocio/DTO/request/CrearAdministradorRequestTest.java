package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para CrearAdministradorRequest
 */
@DisplayName("CrearAdministradorRequest - Unit Tests")
public class CrearAdministradorRequestTest {

    private final Integer USUARIO_ID_VALIDO = 1;
    private final String NIVEL_ACCESO_VALIDO = "SUPER_ADMIN";
    private final String PERMISOS_JSON_VALIDO = "{\"permisos\": [\"CREAR\", \"EDITAR\", \"ELIMINAR\"]}";

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        CrearAdministradorRequest request = new CrearAdministradorRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getUsuarioId()).isNull();
        assertThat(request.getNivelAcceso()).isNull();
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        CrearAdministradorRequest request = new CrearAdministradorRequest(
                USUARIO_ID_VALIDO,
                NIVEL_ACCESO_VALIDO,
                PERMISOS_JSON_VALIDO
        );

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        CrearAdministradorRequest request = CrearAdministradorRequest.builder()
                .usuarioId(USUARIO_ID_VALIDO)
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .permisosJson(PERMISOS_JSON_VALIDO)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    @Test
    @DisplayName("Builder - Permite construcción parcial")
    void builder_PermiteConstruccionParcial() {
        // Act
        CrearAdministradorRequest request = CrearAdministradorRequest.builder()
                .usuarioId(USUARIO_ID_VALIDO)
                .nivelAcceso(NIVEL_ACCESO_VALIDO)
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        CrearAdministradorRequest request = new CrearAdministradorRequest();

        // Act
        request.setUsuarioId(USUARIO_ID_VALIDO);
        request.setNivelAcceso(NIVEL_ACCESO_VALIDO);
        request.setPermisosJson(PERMISOS_JSON_VALIDO);

        // Assert
        assertThat(request.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(request.getNivelAcceso()).isEqualTo(NIVEL_ACCESO_VALIDO);
        assertThat(request.getPermisosJson()).isEqualTo(PERMISOS_JSON_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        CrearAdministradorRequest request1 = new CrearAdministradorRequest(
                USUARIO_ID_VALIDO,
                NIVEL_ACCESO_VALIDO,
                PERMISOS_JSON_VALIDO
        );
        CrearAdministradorRequest request2 = new CrearAdministradorRequest(
                USUARIO_ID_VALIDO,
                NIVEL_ACCESO_VALIDO,
                PERMISOS_JSON_VALIDO
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getUsuarioId()).isEqualTo(request2.getUsuarioId());
        assertThat(request1.getNivelAcceso()).isEqualTo(request2.getNivelAcceso());
        assertThat(request1.getPermisosJson()).isEqualTo(request2.getPermisosJson());
    }

    @Test
    @DisplayName("Diferente usuarioId - Campos no son iguales")
    void diferenteUsuarioId_CamposNoSonIguales() {
        // Arrange
        CrearAdministradorRequest request1 = new CrearAdministradorRequest(1, NIVEL_ACCESO_VALIDO, PERMISOS_JSON_VALIDO);
        CrearAdministradorRequest request2 = new CrearAdministradorRequest(2, NIVEL_ACCESO_VALIDO, PERMISOS_JSON_VALIDO);

        // Act & Assert
        assertThat(request1.getUsuarioId()).isNotEqualTo(request2.getUsuarioId());
    }

    @Test
    @DisplayName("Diferente nivelAcceso - Campos no son iguales")
    void diferenteNivelAcceso_CamposNoSonIguales() {
        // Arrange
        CrearAdministradorRequest request1 = new CrearAdministradorRequest(USUARIO_ID_VALIDO, "ADMIN", PERMISOS_JSON_VALIDO);
        CrearAdministradorRequest request2 = new CrearAdministradorRequest(USUARIO_ID_VALIDO, "SUPER_ADMIN", PERMISOS_JSON_VALIDO);

        // Act & Assert
        assertThat(request1.getNivelAcceso()).isNotEqualTo(request2.getNivelAcceso());
    }

    @Test
    @DisplayName("Diferente permisosJson - Campos no son iguales")
    void diferentePermisosJson_CamposNoSonIguales() {
        // Arrange
        CrearAdministradorRequest request1 = new CrearAdministradorRequest(USUARIO_ID_VALIDO, NIVEL_ACCESO_VALIDO, "{\"permisos\": [\"CREAR\"]}");
        CrearAdministradorRequest request2 = new CrearAdministradorRequest(USUARIO_ID_VALIDO, NIVEL_ACCESO_VALIDO, "{\"permisos\": [\"EDITAR\"]}");

        // Act & Assert
        assertThat(request1.getPermisosJson()).isNotEqualTo(request2.getPermisosJson());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        CrearAdministradorRequest request = new CrearAdministradorRequest(
                USUARIO_ID_VALIDO,
                NIVEL_ACCESO_VALIDO,
                PERMISOS_JSON_VALIDO
        );

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("Campos - Aceptan diferentes valores válidos")
    void campos_AceptanDiferentesValoresValidos() {
        // Arrange
        CrearAdministradorRequest request = new CrearAdministradorRequest();

        // Act & Assert - usuarioId
        request.setUsuarioId(100);
        assertThat(request.getUsuarioId()).isEqualTo(100);

        request.setUsuarioId(null);
        assertThat(request.getUsuarioId()).isNull();

        // nivelAcceso
        request.setNivelAcceso("ADMIN");
        assertThat(request.getNivelAcceso()).isEqualTo("ADMIN");

        request.setNivelAcceso("MODERADOR");
        assertThat(request.getNivelAcceso()).isEqualTo("MODERADOR");

        request.setNivelAcceso("");
        assertThat(request.getNivelAcceso()).isEmpty();

        request.setNivelAcceso(null);
        assertThat(request.getNivelAcceso()).isNull();

        // permisosJson
        request.setPermisosJson("{\"roles\": [\"USER_MANAGEMENT\"]}");
        assertThat(request.getPermisosJson()).contains("USER_MANAGEMENT");

        request.setPermisosJson("");
        assertThat(request.getPermisosJson()).isEmpty();

        request.setPermisosJson(null);
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("Escenario - Creación de administrador completo")
    void escenario_CreacionDeAdministradorCompleto() {
        // Act
        CrearAdministradorRequest request = CrearAdministradorRequest.builder()
                .usuarioId(5)
                .nivelAcceso("SUPER_ADMIN")
                .permisosJson("{\"permisos\": [\"FULL_ACCESS\"]}")
                .build();

        // Assert
        assertThat(request.getUsuarioId()).isEqualTo(5);
        assertThat(request.getNivelAcceso()).isEqualTo("SUPER_ADMIN");
        assertThat(request.getPermisosJson()).contains("FULL_ACCESS");
    }

    @Test
    @DisplayName("Escenario - Creación de administrador básico")
    void escenario_CreacionDeAdministradorBasico() {
        // Act
        CrearAdministradorRequest request = new CrearAdministradorRequest();
        request.setUsuarioId(10);
        request.setNivelAcceso("BASICO");

        // Assert
        assertThat(request.getUsuarioId()).isEqualTo(10);
        assertThat(request.getNivelAcceso()).isEqualTo("BASICO");
        assertThat(request.getPermisosJson()).isNull();
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        CrearAdministradorRequest request = new CrearAdministradorRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        CrearAdministradorRequest request = new CrearAdministradorRequest(1, "ADMIN", "{}");

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getUsuarioId()).isEqualTo(1);
        assertThat(request.getNivelAcceso()).isEqualTo("ADMIN");
        assertThat(request.getPermisosJson()).isEqualTo("{}");
    }

    @Test
    @DisplayName("Lombok - Builder funciona")
    void lombok_BuilderFunciona() {
        // Act
        CrearAdministradorRequest request = CrearAdministradorRequest.builder()
                .usuarioId(1)
                .nivelAcceso("TEST")
                .permisosJson("{\"test\": true}")
                .build();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getUsuarioId()).isEqualTo(1);
        assertThat(request.getNivelAcceso()).isEqualTo("TEST");
        assertThat(request.getPermisosJson()).isEqualTo("{\"test\": true}");
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        CrearAdministradorRequest request = new CrearAdministradorRequest();

        // Act
        request.setUsuarioId(99);
        request.setNivelAcceso("SETTER_TEST");
        request.setPermisosJson("{\"setter\": \"works\"}");

        // Assert
        assertThat(request.getUsuarioId()).isEqualTo(99);
        assertThat(request.getNivelAcceso()).isEqualTo("SETTER_TEST");
        assertThat(request.getPermisosJson()).isEqualTo("{\"setter\": \"works\"}");
    }
}