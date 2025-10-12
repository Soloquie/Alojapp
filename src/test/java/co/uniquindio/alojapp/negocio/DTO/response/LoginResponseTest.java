package co.uniquindio.alojapp.negocio.DTO.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para LoginResponse
 */
@DisplayName("LoginResponse - Unit Tests")
public class LoginResponseTest {

    private final String TOKEN_VALIDO = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
    private final String TOKEN_TYPE_VALIDO = "Bearer";
    private final Integer USUARIO_ID_VALIDO = 123;
    private final String NOMBRE_VALIDO = "Juan Pérez";
    private final String EMAIL_VALIDO = "juan.perez@correo.com";
    private final String ROL_VALIDO = "HUESPED";
    private final Long EXPIRES_IN_VALIDO = 900000L;

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        LoginResponse response = new LoginResponse();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNull();
        assertThat(response.getTokenType()).isNull();
        assertThat(response.getUsuarioId()).isNull();
        assertThat(response.getNombre()).isNull();
        assertThat(response.getEmail()).isNull();
        assertThat(response.getRol()).isNull();
        assertThat(response.getExpiresIn()).isNull();
    }

    @Test
    @DisplayName("Constructor con argumentos - Crea instancia con todos los parámetros")
    void constructorConArgumentos_CreaInstanciaConTodosLosParametros() {
        // Act
        LoginResponse response = new LoginResponse(
                TOKEN_VALIDO, TOKEN_TYPE_VALIDO, USUARIO_ID_VALIDO,
                NOMBRE_VALIDO, EMAIL_VALIDO, ROL_VALIDO, EXPIRES_IN_VALIDO
        );

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(TOKEN_VALIDO);
        assertThat(response.getTokenType()).isEqualTo(TOKEN_TYPE_VALIDO);
        assertThat(response.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(response.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(response.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(response.getRol()).isEqualTo(ROL_VALIDO);
        assertThat(response.getExpiresIn()).isEqualTo(EXPIRES_IN_VALIDO);
    }

    @Test
    @DisplayName("Builder - Crea instancia correctamente")
    void builder_CreaInstanciaCorrectamente() {
        // Act
        LoginResponse response = LoginResponse.builder()
                .token(TOKEN_VALIDO)
                .tokenType(TOKEN_TYPE_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .email(EMAIL_VALIDO)
                .rol(ROL_VALIDO)
                .expiresIn(EXPIRES_IN_VALIDO)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(TOKEN_VALIDO);
        assertThat(response.getTokenType()).isEqualTo(TOKEN_TYPE_VALIDO);
        assertThat(response.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(response.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(response.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(response.getRol()).isEqualTo(ROL_VALIDO);
        assertThat(response.getExpiresIn()).isEqualTo(EXPIRES_IN_VALIDO);
    }

    @Test
    @DisplayName("Builder - Permite construcción parcial")
    void builder_PermiteConstruccionParcial() {
        // Act
        LoginResponse response = LoginResponse.builder()
                .token(TOKEN_VALIDO)
                .usuarioId(USUARIO_ID_VALIDO)
                .nombre(NOMBRE_VALIDO)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo(TOKEN_VALIDO);
        assertThat(response.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(response.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(response.getTokenType()).isNull();
        assertThat(response.getEmail()).isNull();
        assertThat(response.getRol()).isNull();
        assertThat(response.getExpiresIn()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act
        response.setToken(TOKEN_VALIDO);
        response.setTokenType(TOKEN_TYPE_VALIDO);
        response.setUsuarioId(USUARIO_ID_VALIDO);
        response.setNombre(NOMBRE_VALIDO);
        response.setEmail(EMAIL_VALIDO);
        response.setRol(ROL_VALIDO);
        response.setExpiresIn(EXPIRES_IN_VALIDO);

        // Assert
        assertThat(response.getToken()).isEqualTo(TOKEN_VALIDO);
        assertThat(response.getTokenType()).isEqualTo(TOKEN_TYPE_VALIDO);
        assertThat(response.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(response.getNombre()).isEqualTo(NOMBRE_VALIDO);
        assertThat(response.getEmail()).isEqualTo(EMAIL_VALIDO);
        assertThat(response.getRol()).isEqualTo(ROL_VALIDO);
        assertThat(response.getExpiresIn()).isEqualTo(EXPIRES_IN_VALIDO);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        LoginResponse response1 = new LoginResponse(
                TOKEN_VALIDO, TOKEN_TYPE_VALIDO, USUARIO_ID_VALIDO,
                NOMBRE_VALIDO, EMAIL_VALIDO, ROL_VALIDO, EXPIRES_IN_VALIDO
        );
        LoginResponse response2 = new LoginResponse(
                TOKEN_VALIDO, TOKEN_TYPE_VALIDO, USUARIO_ID_VALIDO,
                NOMBRE_VALIDO, EMAIL_VALIDO, ROL_VALIDO, EXPIRES_IN_VALIDO
        );

        // Act & Assert - Verificar manualmente cada campo
        assertThat(response1.getToken()).isEqualTo(response2.getToken());
        assertThat(response1.getTokenType()).isEqualTo(response2.getTokenType());
        assertThat(response1.getUsuarioId()).isEqualTo(response2.getUsuarioId());
        assertThat(response1.getNombre()).isEqualTo(response2.getNombre());
        assertThat(response1.getEmail()).isEqualTo(response2.getEmail());
        assertThat(response1.getRol()).isEqualTo(response2.getRol());
        assertThat(response1.getExpiresIn()).isEqualTo(response2.getExpiresIn());
    }

    @Test
    @DisplayName("Diferente token - Campos no son iguales")
    void diferenteToken_CamposNoSonIguales() {
        // Arrange
        LoginResponse response1 = new LoginResponse();
        response1.setToken("token1");

        LoginResponse response2 = new LoginResponse();
        response2.setToken("token2");

        // Act & Assert
        assertThat(response1.getToken()).isNotEqualTo(response2.getToken());
    }

    @Test
    @DisplayName("Diferente usuarioId - Campos no son iguales")
    void diferenteUsuarioId_CamposNoSonIguales() {
        // Arrange
        LoginResponse response1 = new LoginResponse();
        response1.setUsuarioId(1);

        LoginResponse response2 = new LoginResponse();
        response2.setUsuarioId(2);

        // Act & Assert
        assertThat(response1.getUsuarioId()).isNotEqualTo(response2.getUsuarioId());
    }

    @Test
    @DisplayName("Diferente rol - Campos no son iguales")
    void diferenteRol_CamposNoSonIguales() {
        // Arrange
        LoginResponse response1 = new LoginResponse();
        response1.setRol("HUESPED");

        LoginResponse response2 = new LoginResponse();
        response2.setRol("ANFITRION");

        // Act & Assert
        assertThat(response1.getRol()).isNotEqualTo(response2.getRol());
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        LoginResponse response = new LoginResponse(
                TOKEN_VALIDO, TOKEN_TYPE_VALIDO, USUARIO_ID_VALIDO,
                NOMBRE_VALIDO, EMAIL_VALIDO, ROL_VALIDO, EXPIRES_IN_VALIDO
        );

        // Act & Assert
        assertThatCode(() -> response.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        LoginResponse response = new LoginResponse(
                TOKEN_VALIDO, TOKEN_TYPE_VALIDO, USUARIO_ID_VALIDO,
                NOMBRE_VALIDO, EMAIL_VALIDO, ROL_VALIDO, EXPIRES_IN_VALIDO
        );

        // Act
        String resultado = response.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("ToString - Contiene información básica")
    void toString_ContieneInformacionBasica() {
        // Arrange
        LoginResponse response = new LoginResponse();
        response.setToken(TOKEN_VALIDO);
        response.setTokenType(TOKEN_TYPE_VALIDO);
        response.setUsuarioId(USUARIO_ID_VALIDO);
        response.setNombre(NOMBRE_VALIDO);
        response.setEmail(EMAIL_VALIDO);
        response.setRol(ROL_VALIDO);
        response.setExpiresIn(EXPIRES_IN_VALIDO);

        // Act
        String resultado = response.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains("LoginResponse");
        assertThat(resultado).contains("tokenType");
        assertThat(resultado).contains("usuarioId");
        assertThat(resultado).contains("nombre");
        // No verificamos el contenido del token por seguridad
    }

    @Test
    @DisplayName("Token - Acepta diferentes formatos de JWT")
    void token_AceptaDiferentesFormatosDeJWT() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - Token JWT estándar
        response.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        assertThat(response.getToken()).startsWith("eyJ");

        // Token vacío
        response.setToken("");
        assertThat(response.getToken()).isEmpty();

        // Token nulo
        response.setToken(null);
        assertThat(response.getToken()).isNull();
    }

    @Test
    @DisplayName("TokenType - Acepta diferentes tipos")
    void tokenType_AceptaDiferentesTipos() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - Bearer
        response.setTokenType("Bearer");
        assertThat(response.getTokenType()).isEqualTo("Bearer");

        // Basic
        response.setTokenType("Basic");
        assertThat(response.getTokenType()).isEqualTo("Basic");

        // Vacío
        response.setTokenType("");
        assertThat(response.getTokenType()).isEmpty();

        // Nulo
        response.setTokenType(null);
        assertThat(response.getTokenType()).isNull();
    }

    @Test
    @DisplayName("UsuarioId - Acepta diferentes valores")
    void usuarioId_AceptaDiferentesValores() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - Valor positivo
        response.setUsuarioId(1);
        assertThat(response.getUsuarioId()).isEqualTo(1);

        // Valor grande
        response.setUsuarioId(999999);
        assertThat(response.getUsuarioId()).isEqualTo(999999);

        // Nulo
        response.setUsuarioId(null);
        assertThat(response.getUsuarioId()).isNull();
    }

    @Test
    @DisplayName("Nombre - Acepta diferentes nombres")
    void nombre_AceptaDiferentesNombres() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - Nombre simple
        response.setNombre("Ana García");
        assertThat(response.getNombre()).isEqualTo("Ana García");

        // Nombre con caracteres especiales
        response.setNombre("María José Rodríguez-López");
        assertThat(response.getNombre()).contains("Rodríguez");

        // Vacío
        response.setNombre("");
        assertThat(response.getNombre()).isEmpty();

        // Nulo
        response.setNombre(null);
        assertThat(response.getNombre()).isNull();
    }

    @Test
    @DisplayName("Email - Acepta diferentes formatos")
    void email_AceptaDiferentesFormatos() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - Email estándar
        response.setEmail("usuario@dominio.com");
        assertThat(response.getEmail()).isEqualTo("usuario@dominio.com");

        // Email corporativo
        response.setEmail("contacto@empresa.com.co");
        assertThat(response.getEmail()).contains("empresa");

        // Vacío
        response.setEmail("");
        assertThat(response.getEmail()).isEmpty();

        // Nulo
        response.setEmail(null);
        assertThat(response.getEmail()).isNull();
    }

    @Test
    @DisplayName("Rol - Acepta diferentes roles")
    void rol_AceptaDiferentesRoles() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - HUESPED
        response.setRol("HUESPED");
        assertThat(response.getRol()).isEqualTo("HUESPED");

        // ANFITRION
        response.setRol("ANFITRION");
        assertThat(response.getRol()).isEqualTo("ANFITRION");

        // ADMIN
        response.setRol("ADMIN");
        assertThat(response.getRol()).isEqualTo("ADMIN");

        // Vacío
        response.setRol("");
        assertThat(response.getRol()).isEmpty();

        // Nulo
        response.setRol(null);
        assertThat(response.getRol()).isNull();
    }

    @Test
    @DisplayName("ExpiresIn - Acepta diferentes valores de tiempo")
    void expiresIn_AceptaDiferentesValoresDeTiempo() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act & Assert - 15 minutos (900000 ms)
        response.setExpiresIn(900000L);
        assertThat(response.getExpiresIn()).isEqualTo(900000L);

        // 1 hora (3600000 ms)
        response.setExpiresIn(3600000L);
        assertThat(response.getExpiresIn()).isEqualTo(3600000L);

        // 24 horas (86400000 ms)
        response.setExpiresIn(86400000L);
        assertThat(response.getExpiresIn()).isEqualTo(86400000L);

        // Nulo
        response.setExpiresIn(null);
        assertThat(response.getExpiresIn()).isNull();
    }

    @Test
    @DisplayName("Escenario - Login exitoso de huésped")
    void escenario_LoginExitosoDeHuesped() {
        // Act
        LoginResponse response = LoginResponse.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.huesped_token")
                .tokenType("Bearer")
                .usuarioId(456)
                .nombre("María García López")
                .email("maria.garcia@correo.com")
                .rol("HUESPED")
                .expiresIn(1800000L) // 30 minutos
                .build();

        // Assert
        assertThat(response.getToken()).isNotNull();
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getUsuarioId()).isEqualTo(456);
        assertThat(response.getNombre()).isEqualTo("María García López");
        assertThat(response.getEmail()).isEqualTo("maria.garcia@correo.com");
        assertThat(response.getRol()).isEqualTo("HUESPED");
        assertThat(response.getExpiresIn()).isEqualTo(1800000L);
    }

    @Test
    @DisplayName("Escenario - Login exitoso de anfitrión")
    void escenario_LoginExitosoDeAnfitrion() {
        // Act
        LoginResponse response = new LoginResponse();
        response.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.anfitrion_token");
        response.setTokenType("Bearer");
        response.setUsuarioId(789);
        response.setNombre("Carlos Rodríguez");
        response.setEmail("carlos.rodriguez@correo.com");
        response.setRol("ANFITRION");
        response.setExpiresIn(3600000L); // 1 hora

        // Assert
        assertThat(response.getToken()).contains("anfitrion_token");
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getUsuarioId()).isEqualTo(789);
        assertThat(response.getNombre()).isEqualTo("Carlos Rodríguez");
        assertThat(response.getEmail()).contains("carlos.rodriguez");
        assertThat(response.getRol()).isEqualTo("ANFITRION");
        assertThat(response.getExpiresIn()).isEqualTo(3600000L);
    }

    @Test
    @DisplayName("Escenario - Login con token de larga duración")
    void escenario_LoginConTokenDeLargaDuracion() {
        // Act
        LoginResponse response = LoginResponse.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.long_lived_token")
                .tokenType("Bearer")
                .usuarioId(321)
                .nombre("Ana Martínez")
                .email("ana.martinez@correo.com")
                .rol("ADMIN")
                .expiresIn(86400000L) // 24 horas
                .build();

        // Assert
        assertThat(response.getToken()).isNotNull();
        assertThat(response.getRol()).isEqualTo("ADMIN");
        assertThat(response.getExpiresIn()).isEqualTo(86400000L);
        assertThat(response.getExpiresIn()).isGreaterThan(3600000L); // Más de 1 hora
    }

    @Test
    @DisplayName("Casos borde - Token muy largo")
    void casosBorde_TokenMuyLargo() {
        // Arrange & Act
        String tokenLargo = "T".repeat(1000);
        LoginResponse response = new LoginResponse();
        response.setToken(tokenLargo);

        // Assert
        assertThat(response.getToken()).hasSize(1000);
        assertThat(response.getToken()).isEqualTo(tokenLargo);
    }

    @Test
    @DisplayName("Casos borde - ExpiresIn con valor máximo")
    void casosBorde_ExpiresInConValorMaximo() {
        // Arrange & Act
        Long expiresInMaximo = Long.MAX_VALUE;
        LoginResponse response = new LoginResponse();
        response.setExpiresIn(expiresInMaximo);

        // Assert
        assertThat(response.getExpiresIn()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("Casos borde - UsuarioId con valor máximo")
    void casosBorde_UsuarioIdConValorMaximo() {
        // Arrange & Act
        Integer usuarioIdMaximo = Integer.MAX_VALUE;
        LoginResponse response = new LoginResponse();
        response.setUsuarioId(usuarioIdMaximo);

        // Assert
        assertThat(response.getUsuarioId()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        LoginResponse response = new LoginResponse();

        // Assert
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Constructor con parámetros funciona")
    void lombok_ConstructorConParametrosFunciona() {
        // Act
        LoginResponse response = new LoginResponse(
                "test_token", "Bearer", 999, "Test User",
                "test@test.com", "TEST_ROLE", 60000L
        );

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("test_token");
        assertThat(response.getUsuarioId()).isEqualTo(999);
        assertThat(response.getNombre()).isEqualTo("Test User");
        assertThat(response.getEmail()).isEqualTo("test@test.com");
        assertThat(response.getRol()).isEqualTo("TEST_ROLE");
        assertThat(response.getExpiresIn()).isEqualTo(60000L);
    }

    @Test
    @DisplayName("Lombok - Builder funciona")
    void lombok_BuilderFunciona() {
        // Act
        LoginResponse response = LoginResponse.builder()
                .token("builder_token")
                .tokenType("Bearer")
                .usuarioId(777)
                .nombre("Builder User")
                .email("builder@test.com")
                .rol("BUILDER")
                .expiresIn(300000L)
                .build();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("builder_token");
        assertThat(response.getUsuarioId()).isEqualTo(777);
        assertThat(response.getNombre()).isEqualTo("Builder User");
        assertThat(response.getEmail()).isEqualTo("builder@test.com");
        assertThat(response.getRol()).isEqualTo("BUILDER");
        assertThat(response.getExpiresIn()).isEqualTo(300000L);
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        LoginResponse response = new LoginResponse();

        // Act
        response.setToken("setter_token");
        response.setTokenType("Bearer");
        response.setUsuarioId(888);
        response.setNombre("Setter User");
        response.setEmail("setter@test.com");
        response.setRol("SETTER");
        response.setExpiresIn(120000L);

        // Assert
        assertThat(response.getToken()).isEqualTo("setter_token");
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getUsuarioId()).isEqualTo(888);
        assertThat(response.getNombre()).isEqualTo("Setter User");
        assertThat(response.getEmail()).isEqualTo("setter@test.com");
        assertThat(response.getRol()).isEqualTo("SETTER");
        assertThat(response.getExpiresIn()).isEqualTo(120000L);
    }
}