package co.uniquindio.alojapp.negocio.DTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para CodigoRecuperacionDTO
 */
@DisplayName("CodigoRecuperacionDTO - Unit Tests")
public class CodigoRecuperacionDTOTest {

    // DATOS DE PRUEBA
    private final Integer ID_VALIDO = 1;
    private final Long USUARIO_ID_VALIDO = 123L;
    private final String CODIGO_VALIDO = "ABC123XYZ";
    private final LocalDateTime FECHA_EXPIRACION_VALIDA = LocalDateTime.of(2024, 12, 31, 23, 59);
    private final Boolean USADO_VALIDO = false;
    private final Boolean ES_VALIDO_VALIDO = true;

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Crea instancia con todos los parámetros correctamente")
    void constructorAllArgs_ConTodosLosParametros_CreaInstanciaCorrectamente() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        // ASSERT
        assertThat(codigo).isNotNull();
        assertThat(codigo.getId()).isEqualTo(ID_VALIDO);
        assertThat(codigo.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(codigo.getCodigo()).isEqualTo(CODIGO_VALIDO);
        assertThat(codigo.getFechaExpiracion()).isEqualTo(FECHA_EXPIRACION_VALIDA);
        assertThat(codigo.getUsado()).isEqualTo(USADO_VALIDO);
        assertThat(codigo.getEsValido()).isEqualTo(ES_VALIDO_VALIDO);
    }

    @Test
    @DisplayName("CONSTRUCTOR ALL ARGS - Con parámetros null se maneja correctamente")
    void constructorAllArgs_ConParametrosNull_SeManejaCorrectamente() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(null, null, null, null, null, null);

        // ASSERT
        assertThat(codigo).isNotNull();
        assertThat(codigo.getId()).isNull();
        assertThat(codigo.getUsuarioId()).isNull();
        assertThat(codigo.getCodigo()).isNull();
        assertThat(codigo.getFechaExpiracion()).isNull();
        assertThat(codigo.getUsado()).isNull();
        assertThat(codigo.getEsValido()).isNull();
    }

    @Test
    @DisplayName("CONSTRUCTOR NO ARGS - Crea instancia correctamente")
    void noArgsConstructor_CreaInstanciaCorrectamente() {
        // ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ASSERT
        assertThat(codigo).isNotNull();
        assertThat(codigo.getId()).isNull();
        assertThat(codigo.getUsuarioId()).isNull();
        assertThat(codigo.getCodigo()).isNull();
        assertThat(codigo.getFechaExpiracion()).isNull();
        assertThat(codigo.getUsado()).isNull();
        assertThat(codigo.getEsValido()).isNull();
    }

    // ==================== GETTERS Y SETTERS TESTS ====================

    @Test
    @DisplayName("GETTERS Y SETTERS - Funcionan correctamente para todos los campos")
    void gettersYSetters_FuncionanCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT
        codigo.setId(ID_VALIDO);
        codigo.setUsuarioId(USUARIO_ID_VALIDO);
        codigo.setCodigo(CODIGO_VALIDO);
        codigo.setFechaExpiracion(FECHA_EXPIRACION_VALIDA);
        codigo.setUsado(USADO_VALIDO);
        codigo.setEsValido(ES_VALIDO_VALIDO);

        // ASSERT
        assertThat(codigo.getId()).isEqualTo(ID_VALIDO);
        assertThat(codigo.getUsuarioId()).isEqualTo(USUARIO_ID_VALIDO);
        assertThat(codigo.getCodigo()).isEqualTo(CODIGO_VALIDO);
        assertThat(codigo.getFechaExpiracion()).isEqualTo(FECHA_EXPIRACION_VALIDA);
        assertThat(codigo.getUsado()).isEqualTo(USADO_VALIDO);
        assertThat(codigo.getEsValido()).isEqualTo(ES_VALIDO_VALIDO);
    }

    @Test
    @DisplayName("GETTERS Y SETTERS - Campos booleanos con diferentes valores funcionan correctamente")
    void gettersYSetters_CamposBooleanosConDiferentesValores_FuncionanCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT & ASSERT - Probar diferentes combinaciones booleanas
        codigo.setUsado(true);
        codigo.setEsValido(false);
        assertThat(codigo.getUsado()).isTrue();
        assertThat(codigo.getEsValido()).isFalse();

        codigo.setUsado(false);
        codigo.setEsValido(true);
        assertThat(codigo.getUsado()).isFalse();
        assertThat(codigo.getEsValido()).isTrue();

        codigo.setUsado(true);
        codigo.setEsValido(true);
        assertThat(codigo.getUsado()).isTrue();
        assertThat(codigo.getEsValido()).isTrue();

        codigo.setUsado(false);
        codigo.setEsValido(false);
        assertThat(codigo.getUsado()).isFalse();
        assertThat(codigo.getEsValido()).isFalse();
    }

    // ==================== EQUALS Y HASHCODE TESTS ====================

    @Test
    @DisplayName("EQUALS - Mismos valores en todos los campos retorna true")
    void equals_MismosValoresEnTodosLosCampos_RetornaTrue() {
        // ARRANGE
        CodigoRecuperacionDTO codigo1 = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        CodigoRecuperacionDTO codigo2 = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        // ACT & ASSERT
        assertThat(codigo1).isEqualTo(codigo2);
        assertThat(codigo1.hashCode()).isEqualTo(codigo2.hashCode());
    }

    @Test
    @DisplayName("EQUALS - Diferente ID retorna false")
    void equals_DiferenteId_RetornaFalse() {
        // ARRANGE
        CodigoRecuperacionDTO codigo1 = new CodigoRecuperacionDTO(1, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO);

        CodigoRecuperacionDTO codigo2 = new CodigoRecuperacionDTO(2, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO);

        // ACT & ASSERT
        assertThat(codigo1).isNotEqualTo(codigo2);
    }

    @Test
    @DisplayName("EQUALS - Comparación con null retorna false")
    void equals_ComparacionConNull_RetornaFalse() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        // ACT & ASSERT
        assertThat(codigo).isNotEqualTo(null);
    }

    @Test
    @DisplayName("EQUALS - Comparación con objeto de diferente clase retorna false")
    void equals_ComparacionConDiferenteClase_RetornaFalse() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        String objetoDiferente = "Soy un String";

        // ACT & ASSERT
        assertThat(codigo).isNotEqualTo(objetoDiferente);
    }

    // ==================== TO STRING TESTS ====================

    @Test
    @DisplayName("TO STRING - Contiene información relevante del código")
    void toString_ContieneInformacionRelevante() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        // ACT
        String resultado = codigo.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).contains(String.valueOf(ID_VALIDO));
        assertThat(resultado).contains(String.valueOf(USUARIO_ID_VALIDO));
        assertThat(resultado).contains(CODIGO_VALIDO);
        assertThat(resultado).contains(String.valueOf(USADO_VALIDO));
        assertThat(resultado).contains(String.valueOf(ES_VALIDO_VALIDO));
    }

    @Test
    @DisplayName("TO STRING - Con campos null se maneja correctamente")
    void toString_ConCamposNull_SeManejaCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(null, null, null, null, null, null);

        // ACT
        String resultado = codigo.toString();

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotEmpty();
    }

    // ==================== VALIDACIÓN DE CÓDIGO TESTS ====================

    @Test
    @DisplayName("VALIDACIÓN - Código con diferentes formatos es aceptado")
    void validacion_CodigoConDiferentesFormatos_EsAceptado() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT & ASSERT - Códigos alfanuméricos
        codigo.setCodigo("ABC123");
        assertThat(codigo.getCodigo()).isEqualTo("ABC123");

        codigo.setCodigo("123456");
        assertThat(codigo.getCodigo()).isEqualTo("123456");

        codigo.setCodigo("A1B2C3");
        assertThat(codigo.getCodigo()).isEqualTo("A1B2C3");

        // Códigos con caracteres especiales (si están permitidos)
        codigo.setCodigo("ABC-123");
        assertThat(codigo.getCodigo()).isEqualTo("ABC-123");

        codigo.setCodigo("ABC_123");
        assertThat(codigo.getCodigo()).isEqualTo("ABC_123");
    }

    @Test
    @DisplayName("VALIDACIÓN - Código muy largo se maneja correctamente")
    void validacion_CodigoMuyLargo_SeManejaCorrectamente() {
        // ARRANGE
        String codigoLargo = "A".repeat(1000);
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT
        codigo.setCodigo(codigoLargo);

        // ASSERT
        assertThat(codigo.getCodigo()).isEqualTo(codigoLargo);
        assertThat(codigo.getCodigo()).hasSize(1000);
    }

    // ==================== FECHAS TESTS ====================

    @Test
    @DisplayName("FECHAS - Fechas de expiración en diferentes momentos funcionan correctamente")
    void fechas_FechasExpiracionEnDiferentesMomentos_FuncionanCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        LocalDateTime fechaPasado = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime fechaFuturo = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaLimite = LocalDateTime.of(2030, 12, 31, 23, 59, 59);

        // ACT & ASSERT
        codigo.setFechaExpiracion(fechaPasado);
        assertThat(codigo.getFechaExpiracion()).isEqualTo(fechaPasado);

        codigo.setFechaExpiracion(fechaFuturo);
        assertThat(codigo.getFechaExpiracion()).isEqualTo(fechaFuturo);

        codigo.setFechaExpiracion(fechaLimite);
        assertThat(codigo.getFechaExpiracion()).isEqualTo(fechaLimite);
    }

    @Test
    @DisplayName("FECHAS - Fecha de expiración null se maneja correctamente")
    void fechas_FechaExpiracionNull_SeManejaCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT
        codigo.setFechaExpiracion(null);

        // ASSERT
        assertThat(codigo.getFechaExpiracion()).isNull();
    }

    // ==================== CASOS BORDE TESTS ====================

    @Test
    @DisplayName("CASO BORDE - UsuarioId con valores límite se maneja correctamente")
    void casoBorde_UsuarioIdConValoresLimite_SeManejaCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT & ASSERT
        codigo.setUsuarioId(1L); // Mínimo valor positivo
        assertThat(codigo.getUsuarioId()).isEqualTo(1L);

        codigo.setUsuarioId(Long.MAX_VALUE); // Máximo valor
        assertThat(codigo.getUsuarioId()).isEqualTo(Long.MAX_VALUE);

        codigo.setUsuarioId(0L); // Cero
        assertThat(codigo.getUsuarioId()).isZero();
    }

    @Test
    @DisplayName("CASO BORDE - Booleanos null se manejan correctamente")
    void casoBorde_BooleanosNull_SeManejanCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT
        codigo.setUsado(null);
        codigo.setEsValido(null);

        // ASSERT
        assertThat(codigo.getUsado()).isNull();
        assertThat(codigo.getEsValido()).isNull();
    }

    @Test
    @DisplayName("CASO BORDE - Código vacío se maneja correctamente")
    void casoBorde_CodigoVacio_SeManejaCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT
        codigo.setCodigo("");

        // ASSERT
        assertThat(codigo.getCodigo()).isEmpty();
    }

    @Test
    @DisplayName("CASO BORDE - Código con solo espacios se maneja correctamente")
    void casoBorde_CodigoConSoloEspacios_SeManejaCorrectamente() {
        // ARRANGE
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO();

        // ACT
        codigo.setCodigo("   ");

        // ASSERT
        assertThat(codigo.getCodigo()).isEqualTo("   ");
    }

    // ==================== ESTADOS LÓGICOS TESTS ====================

    @Test
    @DisplayName("ESTADOS LÓGICOS - Código usado y no válido")
    void estadosLogicos_CodigoUsadoYNoValido() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, true, false
        );

        // ASSERT
        assertThat(codigo.getUsado()).isTrue();
        assertThat(codigo.getEsValido()).isFalse();
    }

    @Test
    @DisplayName("ESTADOS LÓGICOS - Código no usado y válido")
    void estadosLogicos_CodigoNoUsadoYValido() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, false, true
        );

        // ASSERT
        assertThat(codigo.getUsado()).isFalse();
        assertThat(codigo.getEsValido()).isTrue();
    }

    @Test
    @DisplayName("ESTADOS LÓGICOS - Código usado pero marcado como válido (caso inusual)")
    void estadosLogicos_CodigoUsadoPeroValido_CasoInusual() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, true, true
        );

        // ASSERT
        assertThat(codigo.getUsado()).isTrue();
        assertThat(codigo.getEsValido()).isTrue();
    }

    @Test
    @DisplayName("ESTADOS LÓGICOS - Código no usado pero marcado como no válido")
    void estadosLogicos_CodigoNoUsadoPeroNoValido() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, false, false
        );

        // ASSERT
        assertThat(codigo.getUsado()).isFalse();
        assertThat(codigo.getEsValido()).isFalse();
    }

    // ==================== LOMBOK FUNCTIONALITY TESTS ====================

    @Test
    @DisplayName("LOMBOK - Anotaciones Lombok funcionan correctamente")
    void lombok_AnotacionesFuncionanCorrectamente() {
        // ARRANGE & ACT
        CodigoRecuperacionDTO codigo1 = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        CodigoRecuperacionDTO codigo2 = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, CODIGO_VALIDO,
                FECHA_EXPIRACION_VALIDA, USADO_VALIDO, ES_VALIDO_VALIDO
        );

        // ASSERT - Verificar que @Data, @NoArgsConstructor, @AllArgsConstructor funcionan
        assertThat(codigo1).isEqualTo(codigo2);
        assertThat(codigo1.toString()).isNotNull();
        assertThat(codigo1.hashCode()).isEqualTo(codigo2.hashCode());

        // Verificar que no hay error con constructor sin parámetros
        CodigoRecuperacionDTO codigoVacio = new CodigoRecuperacionDTO();
        assertThat(codigoVacio).isNotNull();
    }

    // ==================== SCENARIOS DE USO REAL TESTS ====================

    @Test
    @DisplayName("SCENARIO USO REAL - Código de recuperación recién generado")
    void scenarioUsoReal_CodigoRecuperacionRecienGenerado() {
        // ARRANGE & ACT - Simular un código recién generado
        LocalDateTime fechaExpiracion = LocalDateTime.now().plusHours(24);
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, "NUEVO123",
                fechaExpiracion, false, true
        );

        // ASSERT - Debe estar listo para usar
        assertThat(codigo.getUsado()).isFalse();
        assertThat(codigo.getEsValido()).isTrue();
        assertThat(codigo.getFechaExpiracion()).isAfter(LocalDateTime.now());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Código de recuperación expirado")
    void scenarioUsoReal_CodigoRecuperacionExpirado() {
        // ARRANGE & ACT - Simular un código expirado
        LocalDateTime fechaExpirada = LocalDateTime.now().minusHours(1);
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, "EXP456",
                fechaExpirada, false, false
        );

        // ASSERT - Debe ser inválido
        assertThat(codigo.getEsValido()).isFalse();
        assertThat(codigo.getFechaExpiracion()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("SCENARIO USO REAL - Código de recuperación ya utilizado")
    void scenarioUsoReal_CodigoRecuperacionYaUtilizado() {
        // ARRANGE & ACT - Simular un código ya usado
        CodigoRecuperacionDTO codigo = new CodigoRecuperacionDTO(
                ID_VALIDO, USUARIO_ID_VALIDO, "USADO789",
                FECHA_EXPIRACION_VALIDA, true, false
        );

        // ASSERT - Debe estar marcado como usado e inválido
        assertThat(codigo.getUsado()).isTrue();
        assertThat(codigo.getEsValido()).isFalse();
    }
}