package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.config.CloudinaryProperties;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para FotoPerfilServiceIMPL
 *
 * OBJETIVO: Probar la lógica de subida y eliminación de fotos de perfil
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FotoPerfilService - Unit Tests")
public class FotoPerfilServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private CloudinaryProperties cloudinaryProperties;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private FotoPerfilServiceIMPL fotoPerfilService;

    // DATOS DE PRUEBA
    private final Integer USUARIO_ID_VALIDO = 1;
    private final String CLOUDINARY_SECURE_URL = "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.jpg";
    private final String CLOUDINARY_PUBLIC_ID = "alojapp/usuarios/1/abc123";
    private final String FOLDER_PROPERTY = "alojapp";
    private final byte[] FILE_CONTENT = "contenido-de-imagen".getBytes();

    @BeforeEach
    void setUp() {
        // Configurar el mock de Cloudinary para que devuelva el Uploader mock
        // Usamos lenient para evitar UnnecessaryStubbingException
        lenient().when(cloudinary.uploader()).thenReturn(uploader);
    }

    // ==================== SUBIR FOTO PERFIL TESTS ====================

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Archivo válido sube exitosamente")
    void subirFotoPerfil_ArchivoValido_SubeExitosamente() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn(FOLDER_PROPERTY);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT
        String resultado = fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile);

        // ASSERT
        assertThat(resultado).isEqualTo(CLOUDINARY_SECURE_URL);

        verify(multipartFile).isEmpty();
        verify(multipartFile).getBytes();
        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Archivo nulo lanza IllegalArgumentException")
    void subirFotoPerfil_ArchivoNulo_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Archivo vacío");
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Archivo vacío lanza IllegalArgumentException")
    void subirFotoPerfil_ArchivoVacio_LanzaExcepcion() throws IOException {
        // ARRANGE
        when(multipartFile.isEmpty()).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Archivo vacío");

        verify(multipartFile).isEmpty();
        verify(multipartFile, never()).getBytes();
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Cloudinary sin secure_url lanza ResponseStatusException")
    void subirFotoPerfil_CloudinarySinSecureUrl_LanzaResponseStatusException() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn(FOLDER_PROPERTY);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("url", "http://url-no-segura.com");
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT & ASSERT
        assertThatThrownBy(() -> fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_GATEWAY)
                .hasMessageContaining("Cloudinary no devolvió secure_url");
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Excepción de Cloudinary lanza ResponseStatusException")
    void subirFotoPerfil_CloudinaryException_LanzaResponseStatusException() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn(FOLDER_PROPERTY);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.upload(any(byte[].class), any(Map.class)))
                .thenThrow(new IOException("Error de conexión a Cloudinary"));

        // ACT & ASSERT
        assertThatThrownBy(() -> fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_GATEWAY)
                .hasMessageContaining("Error subiendo a Cloudinary");
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Usa folder por defecto cuando properties está vacío")
    void subirFotoPerfil_FolderPropertiesVacio_UsaFolderPorDefecto() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn("");
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT
        String resultado = fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile);

        // ASSERT
        assertThat(resultado).isEqualTo(CLOUDINARY_SECURE_URL);

        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Usa folder personalizado de properties")
    void subirFotoPerfil_FolderPropertiesPersonalizado_UsaFolderPersonalizado() throws IOException {
        // ARRANGE
        String folderPersonalizado = "mi-app-personalizada";
        when(cloudinaryProperties.getFolder()).thenReturn(folderPersonalizado);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT
        String resultado = fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile);

        // ASSERT
        assertThat(resultado).isEqualTo(CLOUDINARY_SECURE_URL);

        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
    }

    // ==================== ELIMINAR FOTO PERFIL TESTS ====================

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL válida elimina exitosamente")
    void eliminarFotoPerfil_UrlValida_EliminaExitosamente() throws IOException {
        // ARRANGE
        String urlValida = "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.jpg";

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenReturn(Map.of("result", "ok"));

        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(urlValida))
                .doesNotThrowAnyException();

        verify(uploader).destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class));
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL nula no hace nada")
    void eliminarFotoPerfil_UrlNula_NoHaceNada() throws IOException {
        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(null))
                .doesNotThrowAnyException();

        // No debería interactuar con Cloudinary
        verify(cloudinary, never()).uploader();
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL vacía no hace nada")
    void eliminarFotoPerfil_UrlVacia_NoHaceNada() throws IOException {
        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(""))
                .doesNotThrowAnyException();

        // No debería interactuar con Cloudinary
        verify(cloudinary, never()).uploader();
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL con espacios en blanco no hace nada")
    void eliminarFotoPerfil_UrlConEspacios_NoHaceNada() throws IOException {
        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil("   "))
                .doesNotThrowAnyException();

        // No debería interactuar con Cloudinary
        verify(cloudinary, never()).uploader();
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL con formato diferente no hace nada")
    void eliminarFotoPerfil_UrlFormatoDiferente_NoHaceNada() throws IOException {
        // ARRANGE
        String urlFormatoDiferente = "https://otro-servicio.com/imagen.jpg";

        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(urlFormatoDiferente))
                .doesNotThrowAnyException();

        // No debería interactuar con Cloudinary
        verify(cloudinary, never()).uploader();
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL Cloudinary con version extrae public_id correctamente")
    void eliminarFotoPerfil_UrlCloudinaryConVersion_ExtraePublicIdCorrectamente() throws IOException {
        // ARRANGE
        String urlConVersion = "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.jpg";

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenReturn(Map.of("result", "ok"));

        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(urlConVersion))
                .doesNotThrowAnyException();

        verify(uploader).destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class));
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL Cloudinary sin version extrae public_id correctamente")
    void eliminarFotoPerfil_UrlCloudinarySinVersion_ExtraePublicIdCorrectamente() throws IOException {
        // ARRANGE
        String urlSinVersion = "https://res.cloudinary.com/test/image/upload/alojapp/usuarios/1/abc123.jpg";
        String publicIdEsperado = "alojapp/usuarios/1/abc123";

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(publicIdEsperado), any(Map.class)))
                .thenReturn(Map.of("result", "ok"));

        // ACT & ASSERT
        assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(urlSinVersion))
                .doesNotThrowAnyException();

        verify(uploader).destroy(eq(publicIdEsperado), any(Map.class));
    }

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - Excepción de Cloudinary propaga IOException")
    void eliminarFotoPerfil_CloudinaryException_PropagaIOException() throws IOException {
        // ARRANGE
        String urlValida = "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.jpg";

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenThrow(new IOException("Error eliminando de Cloudinary"));

        // ACT & ASSERT
        assertThatThrownBy(() -> fotoPerfilService.eliminarFotoPerfil(urlValida))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Error eliminando de Cloudinary");
    }

    // ==================== CASOS BORDE Y PATRONES URL TESTS ====================

    @Test
    @DisplayName("ELIMINAR FOTO PERFIL - URL con diferentes extensiones extrae public_id")
    void eliminarFotoPerfil_UrlDiferentesExtensiones_ExtraePublicId() throws IOException {
        // ARRANGE
        String[] urlsConExtensiones = {
                "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.jpg",
                "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.png",
                "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.jpeg",
                "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/usuarios/1/abc123.webp"
        };

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenReturn(Map.of("result", "ok"));

        // ACT & ASSERT
        for (String url : urlsConExtensiones) {
            assertThatCode(() -> fotoPerfilService.eliminarFotoPerfil(url))
                    .doesNotThrowAnyException();
        }

        verify(uploader, times(urlsConExtensiones.length))
                .destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class));
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - Overwrite true permite reemplazar fotos")
    void subirFotoPerfil_OverwriteTrue_PermiteReemplazar() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn(FOLDER_PROPERTY);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT
        String resultado = fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile);

        // ASSERT
        assertThat(resultado).isEqualTo(CLOUDINARY_SECURE_URL);

        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
    }

    @Test
    @DisplayName("SUBIR FOTO PERFIL - UUID en public_id asegura unicidad")
    void subirFotoPerfil_GeneraUUID_PublicIdUnico() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn(FOLDER_PROPERTY);
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT
        String resultado = fotoPerfilService.subirFotoPerfil(USUARIO_ID_VALIDO, multipartFile);

        // ASSERT
        assertThat(resultado).isEqualTo(CLOUDINARY_SECURE_URL);

        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
    }
}