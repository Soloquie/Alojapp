package co.uniquindio.alojapp.negocio.DTO.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests para RegistrarPerfilAnfitrionRequest
 */
@DisplayName("RegistrarPerfilAnfitrionRequest - Unit Tests")
public class RegistrarPerfilAnfitrionRequestTest {

    private final String DESCRIPCION_VALIDA = "Soy un anfitrión experimentado con 5 años en la plataforma";
    private final String DOCUMENTOS_URL_VALIDA = "https://drive.google.com/documentos/anfitrion123.pdf";
    private final LocalDate FECHA_REGISTRO_VALIDA = LocalDate.now();

    @Test
    @DisplayName("Constructor sin argumentos - Crea instancia correctamente")
    void constructorSinArgumentos_CreaInstanciaCorrectamente() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Assert
        assertThat(request).isNotNull();
        assertThat(request.getDescripcionPersonal()).isNull();
        assertThat(request.getDocumentosLegalesUrl()).isNull();
        assertThat(request.getFechaRegistro()).isNull();
    }

    @Test
    @DisplayName("Getters y Setters - Funcionan correctamente")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Act
        request.setDescripcionPersonal(DESCRIPCION_VALIDA);
        request.setDocumentosLegalesUrl(DOCUMENTOS_URL_VALIDA);
        request.setFechaRegistro(FECHA_REGISTRO_VALIDA);

        // Assert
        assertThat(request.getDescripcionPersonal()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo(DOCUMENTOS_URL_VALIDA);
        assertThat(request.getFechaRegistro()).isEqualTo(FECHA_REGISTRO_VALIDA);
    }

    @Test
    @DisplayName("Equals - Verificación manual de igualdad")
    void equals_VerificacionManualDeIgualdad() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request1 = new RegistrarPerfilAnfitrionRequest();
        request1.setDescripcionPersonal(DESCRIPCION_VALIDA);
        request1.setDocumentosLegalesUrl(DOCUMENTOS_URL_VALIDA);
        request1.setFechaRegistro(FECHA_REGISTRO_VALIDA);

        RegistrarPerfilAnfitrionRequest request2 = new RegistrarPerfilAnfitrionRequest();
        request2.setDescripcionPersonal(DESCRIPCION_VALIDA);
        request2.setDocumentosLegalesUrl(DOCUMENTOS_URL_VALIDA);
        request2.setFechaRegistro(FECHA_REGISTRO_VALIDA);

        // Act & Assert - Verificar manualmente cada campo
        assertThat(request1.getDescripcionPersonal()).isEqualTo(request2.getDescripcionPersonal());
        assertThat(request1.getDocumentosLegalesUrl()).isEqualTo(request2.getDocumentosLegalesUrl());
        assertThat(request1.getFechaRegistro()).isEqualTo(request2.getFechaRegistro());
    }

    @Test
    @DisplayName("Diferente descripcionPersonal - Campos no son iguales")
    void diferenteDescripcionPersonal_CamposNoSonIguales() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request1 = new RegistrarPerfilAnfitrionRequest();
        request1.setDescripcionPersonal("Descripción 1");

        RegistrarPerfilAnfitrionRequest request2 = new RegistrarPerfilAnfitrionRequest();
        request2.setDescripcionPersonal("Descripción 2");

        // Act & Assert
        assertThat(request1.getDescripcionPersonal()).isNotEqualTo(request2.getDescripcionPersonal());
    }

    @Test
    @DisplayName("Diferente documentosLegalesUrl - Campos no son iguales")
    void diferenteDocumentosLegalesUrl_CamposNoSonIguales() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request1 = new RegistrarPerfilAnfitrionRequest();
        request1.setDocumentosLegalesUrl("https://url1.com");

        RegistrarPerfilAnfitrionRequest request2 = new RegistrarPerfilAnfitrionRequest();
        request2.setDocumentosLegalesUrl("https://url2.com");

        // Act & Assert
        assertThat(request1.getDocumentosLegalesUrl()).isNotEqualTo(request2.getDocumentosLegalesUrl());
    }

    @Test
    @DisplayName("Diferente fechaRegistro - Campos no son iguales")
    void diferenteFechaRegistro_CamposNoSonIguales() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request1 = new RegistrarPerfilAnfitrionRequest();
        request1.setFechaRegistro(LocalDate.of(2024, 1, 1));

        RegistrarPerfilAnfitrionRequest request2 = new RegistrarPerfilAnfitrionRequest();
        request2.setFechaRegistro(LocalDate.of(2024, 1, 2));

        // Act & Assert
        assertThat(request1.getFechaRegistro()).isNotEqualTo(request2.getFechaRegistro());
    }

    @Test
    @DisplayName("ToString - No es nulo")
    void toString_NoEsNulo() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDescripcionPersonal(DESCRIPCION_VALIDA);
        request.setDocumentosLegalesUrl(DOCUMENTOS_URL_VALIDA);

        // Act
        String resultado = request.toString();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isNotBlank();
    }

    @Test
    @DisplayName("DescripcionPersonal - Acepta diferentes valores")
    void descripcionPersonal_AceptaDiferentesValores() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Act & Assert - Descripción larga
        request.setDescripcionPersonal("Soy un anfitrión muy experimentado con más de 10 años de experiencia en hospitalidad");
        assertThat(request.getDescripcionPersonal()).hasSizeGreaterThan(20);

        // Descripción corta
        request.setDescripcionPersonal("Anfitrión nuevo");
        assertThat(request.getDescripcionPersonal()).isEqualTo("Anfitrión nuevo");

        // Descripción vacía
        request.setDescripcionPersonal("");
        assertThat(request.getDescripcionPersonal()).isEmpty();

        // Descripción nula
        request.setDescripcionPersonal(null);
        assertThat(request.getDescripcionPersonal()).isNull();
    }

    @Test
    @DisplayName("DocumentosLegalesUrl - Acepta diferentes formatos de URL")
    void documentosLegalesUrl_AceptaDiferentesFormatosDeUrl() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Act & Assert - URL HTTPS
        request.setDocumentosLegalesUrl("https://drive.google.com/documentos/anfitrion.pdf");
        assertThat(request.getDocumentosLegalesUrl()).startsWith("https://");

        // URL HTTP
        request.setDocumentosLegalesUrl("http://mi-sitio.com/docs/licencia.pdf");
        assertThat(request.getDocumentosLegalesUrl()).startsWith("http://");

        // URL con parámetros
        request.setDocumentosLegalesUrl("https://cloudstorage.com/docs?id=12345&token=abc");
        assertThat(request.getDocumentosLegalesUrl()).contains("?");

        // URL vacía
        request.setDocumentosLegalesUrl("");
        assertThat(request.getDocumentosLegalesUrl()).isEmpty();

        // URL nula
        request.setDocumentosLegalesUrl(null);
        assertThat(request.getDocumentosLegalesUrl()).isNull();
    }

    @Test
    @DisplayName("FechaRegistro - Acepta diferentes fechas")
    void fechaRegistro_AceptaDiferentesFechas() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Act & Assert - Fecha pasada
        LocalDate fechaPasada = LocalDate.of(2020, 5, 15);
        request.setFechaRegistro(fechaPasada);
        assertThat(request.getFechaRegistro()).isEqualTo(fechaPasada);

        // Fecha presente
        LocalDate fechaPresente = LocalDate.now();
        request.setFechaRegistro(fechaPresente);
        assertThat(request.getFechaRegistro()).isEqualTo(fechaPresente);

        // Fecha futura
        LocalDate fechaFutura = LocalDate.now().plusDays(30);
        request.setFechaRegistro(fechaFutura);
        assertThat(request.getFechaRegistro()).isEqualTo(fechaFutura);

        // Fecha nula
        request.setFechaRegistro(null);
        assertThat(request.getFechaRegistro()).isNull();
    }

    @Test
    @DisplayName("Escenario - Registro completo con todos los campos")
    void escenario_RegistroCompletoConTodosLosCampos() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDescripcionPersonal("Anfitrión profesional con amplia experiencia en atención al cliente");
        request.setDocumentosLegalesUrl("https://drive.google.com/documents/legal/anfitrion_profesional.pdf");
        request.setFechaRegistro(LocalDate.of(2024, 3, 15));

        // Assert
        assertThat(request.getDescripcionPersonal()).contains("profesional");
        assertThat(request.getDocumentosLegalesUrl()).contains("anfitrion_profesional");
        assertThat(request.getFechaRegistro()).isEqualTo(LocalDate.of(2024, 3, 15));
    }

    @Test
    @DisplayName("Escenario - Registro mínimo sin fecha")
    void escenario_RegistroMinimoSinFecha() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDescripcionPersonal("Nuevo anfitrión entusiasta");
        request.setDocumentosLegalesUrl("https://docs.com/licencia.pdf");

        // Assert
        assertThat(request.getDescripcionPersonal()).isNotBlank();
        assertThat(request.getDocumentosLegalesUrl()).isNotBlank();
        assertThat(request.getFechaRegistro()).isNull(); // Fecha opcional
    }

    @Test
    @DisplayName("Escenario - Registro solo con descripción")
    void escenario_RegistroSoloConDescripcion() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDescripcionPersonal("Me encanta recibir huéspedes y compartir mi cultura local");

        // Assert
        assertThat(request.getDescripcionPersonal()).isNotBlank();
        assertThat(request.getDocumentosLegalesUrl()).isNull();
        assertThat(request.getFechaRegistro()).isNull();
    }

    @Test
    @DisplayName("Escenario - Registro solo con documentos")
    void escenario_RegistroSoloConDocumentos() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDocumentosLegalesUrl("https://storage.com/permisos_municipales.pdf");

        // Assert
        assertThat(request.getDescripcionPersonal()).isNull();
        assertThat(request.getDocumentosLegalesUrl()).isNotBlank();
        assertThat(request.getFechaRegistro()).isNull();
    }

    @Test
    @DisplayName("Casos borde - Descripción con longitud extensa")
    void casosBorde_DescripcionConLongitudExtensa() {
        // Arrange & Act
        String descripcionLarga = "D".repeat(1000);
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDescripcionPersonal(descripcionLarga);

        // Assert
        assertThat(request.getDescripcionPersonal()).hasSize(1000);
        assertThat(request.getDescripcionPersonal()).isEqualTo(descripcionLarga);
    }

    @Test
    @DisplayName("Casos borde - URL muy larga")
    void casosBorde_UrlMuyLarga() {
        // Arrange & Act
        String urlLarga = "https://" + "domain".repeat(100) + ".com/document.pdf";
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setDocumentosLegalesUrl(urlLarga);

        // Assert
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo(urlLarga);
        assertThat(request.getDocumentosLegalesUrl()).startsWith("https://");
    }

    @Test
    @DisplayName("Casos borde - Fecha muy antigua")
    void casosBorde_FechaMuyAntigua() {
        // Arrange & Act
        LocalDate fechaAntigua = LocalDate.of(1900, 1, 1);
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setFechaRegistro(fechaAntigua);

        // Assert
        assertThat(request.getFechaRegistro()).isEqualTo(fechaAntigua);
        assertThat(request.getFechaRegistro().getYear()).isEqualTo(1900);
    }

    @Test
    @DisplayName("Casos borde - Fecha futura lejana")
    void casosBorde_FechaFuturaLejana() {
        // Arrange & Act
        LocalDate fechaFutura = LocalDate.now().plusYears(10);
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();
        request.setFechaRegistro(fechaFutura);

        // Assert
        assertThat(request.getFechaRegistro()).isEqualTo(fechaFutura);
        assertThat(request.getFechaRegistro()).isAfter(LocalDate.now());
    }

    @Test
    @DisplayName("Lombok - Constructor sin parámetros funciona")
    void lombok_ConstructorSinParametrosFunciona() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Assert
        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Lombok - Getters y Setters funcionan")
    void lombok_GettersYSettersFuncionan() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Act
        request.setDescripcionPersonal("Test descripción");
        request.setDocumentosLegalesUrl("https://test.com/doc.pdf");
        request.setFechaRegistro(LocalDate.of(2024, 6, 1));

        // Assert
        assertThat(request.getDescripcionPersonal()).isEqualTo("Test descripción");
        assertThat(request.getDocumentosLegalesUrl()).isEqualTo("https://test.com/doc.pdf");
        assertThat(request.getFechaRegistro()).isEqualTo(LocalDate.of(2024, 6, 1));
    }

    @Test
    @DisplayName("HashCode - No lanza excepción")
    void hashCode_NoLanzaExcepcion() {
        // Arrange
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Act & Assert
        assertThatCode(() -> request.hashCode()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Campos opcionales - Pueden ser nulos")
    void camposOpcionales_PuedenSerNulos() {
        // Act
        RegistrarPerfilAnfitrionRequest request = new RegistrarPerfilAnfitrionRequest();

        // Assert - Todos los campos pueden ser nulos
        assertThat(request.getDescripcionPersonal()).isNull();
        assertThat(request.getDocumentosLegalesUrl()).isNull();
        assertThat(request.getFechaRegistro()).isNull();
    }
}