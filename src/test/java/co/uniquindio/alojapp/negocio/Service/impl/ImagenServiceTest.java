package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.config.CloudinaryProperties;
import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.persistencia.DAO.ImagenDAO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ImagenServiceIMPL
 *
 * OBJETIVO: Probar la lógica de gestión de imágenes para alojamientos
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ImagenService - Unit Tests")
public class ImagenServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private CloudinaryProperties cloudinaryProperties;

    @Mock
    private ImagenDAO imagenDAO;

    @Mock
    private Uploader uploader;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ImagenServiceIMPL imagenService;

    // DATOS DE PRUEBA
    private final Integer ALOJAMIENTO_ID_VALIDO = 1;
    private final Integer IMAGEN_ID_VALIDO = 501;
    private final String CLOUDINARY_SECURE_URL = "https://res.cloudinary.com/test/image/upload/v1234567890/alojapp/1/abc123.jpg";
    private final String CLOUDINARY_PUBLIC_ID = "alojapp/1/abc123";
    private final String FOLDER_PROPERTY = "alojapp";
    private final String DESCRIPCION_VALIDA = "Vista frontal de la casa";
    private final byte[] FILE_CONTENT = "contenido-de-imagen".getBytes();

    private ImagenDTO imagenDTOMock;

    @BeforeEach
    void setUp() {
        // Configurar mocks comunes
        lenient().when(cloudinary.uploader()).thenReturn(uploader);
        lenient().when(cloudinaryProperties.getFolder()).thenReturn(FOLDER_PROPERTY);

        // Configurar DTO mock
        imagenDTOMock = new ImagenDTO(
                IMAGEN_ID_VALIDO,
                CLOUDINARY_SECURE_URL,
                DESCRIPCION_VALIDA,
                ALOJAMIENTO_ID_VALIDO.longValue()
        );
    }

    // ==================== SUBIR IMAGEN SINGLE TESTS ====================

    @Test
    @DisplayName("SUBIR IMAGEN - Archivo válido sube exitosamente")
    void subirImagen_ArchivoValido_SubeExitosamente() throws IOException {
        // ARRANGE
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        when(imagenDAO.addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), eq(DESCRIPCION_VALIDA), isNull()))
                .thenReturn(imagenDTOMock);

        // ACT
        ImagenDTO resultado = imagenService.subirImagen(ALOJAMIENTO_ID_VALIDO, multipartFile, DESCRIPCION_VALIDA);

        // ASSERT
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(IMAGEN_ID_VALIDO);
        assertThat(resultado.getUrl()).isEqualTo(CLOUDINARY_SECURE_URL);
        assertThat(resultado.getDescripcion()).isEqualTo(DESCRIPCION_VALIDA);
        assertThat(resultado.getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO.longValue());

        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
        verify(imagenDAO).addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), eq(DESCRIPCION_VALIDA), isNull());
    }

    @Test
    @DisplayName("SUBIR IMAGEN - Archivo nulo lanza IllegalArgumentException")
    void subirImagen_ArchivoNulo_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.subirImagen(ALOJAMIENTO_ID_VALIDO, null, DESCRIPCION_VALIDA))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Archivo de imagen vacío");
    }

    @Test
    @DisplayName("SUBIR IMAGEN - Archivo vacío lanza IllegalArgumentException")
    void subirImagen_ArchivoVacio_LanzaExcepcion() throws IOException {
        // ARRANGE
        when(multipartFile.isEmpty()).thenReturn(true);

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.subirImagen(ALOJAMIENTO_ID_VALIDO, multipartFile, DESCRIPCION_VALIDA))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Archivo de imagen vacío");

        verify(multipartFile, never()).getBytes();
    }

    @Test
    @DisplayName("SUBIR IMAGEN - Cloudinary sin secure_url lanza IOException")
    void subirImagen_CloudinarySinSecureUrl_LanzaExcepcion() throws IOException {
        // ARRANGE
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("url", "http://url-no-segura.com");
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.subirImagen(ALOJAMIENTO_ID_VALIDO, multipartFile, DESCRIPCION_VALIDA))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("No se recibió URL segura desde Cloudinary");

        verify(imagenDAO, never()).addImagen(anyInt(), anyString(), anyString(), any());
    }

    // ==================== SUBIR IMAGENES MULTIPLES TESTS ====================

    @Test
    @DisplayName("SUBIR IMAGENES - Lista válida sube exitosamente")
    void subirImagenes_ListaValida_SubeExitosamente() throws IOException {
        // ARRANGE
        List<MultipartFile> files = Arrays.asList(multipartFile, multipartFile);
        List<String> descripciones = Arrays.asList("Vista frontal", "Vista trasera");

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        when(imagenDAO.addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), anyString(), isNull()))
                .thenReturn(imagenDTOMock);

        // ACT
        List<ImagenDTO> resultado = imagenService.subirImagenes(ALOJAMIENTO_ID_VALIDO, files, descripciones);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(2);
        verify(uploader, times(2)).upload(eq(FILE_CONTENT), any(Map.class));
        verify(imagenDAO, times(2)).addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), anyString(), isNull());
    }

    @Test
    @DisplayName("SUBIR IMAGENES - Lista vacía retorna lista vacía")
    void subirImagenes_ListaVacia_RetornaListaVacia() throws IOException {
        // ACT
        List<ImagenDTO> resultado = imagenService.subirImagenes(ALOJAMIENTO_ID_VALIDO, List.of(), List.of());

        // ASSERT
        assertThat(resultado).isEmpty();
        verify(uploader, never()).upload(any(byte[].class), any(Map.class));
    }

    @Test
    @DisplayName("SUBIR IMAGENES - Tamaño descripciones no coincide lanza excepción")
    void subirImagenes_TamanioDescripcionesNoCoincide_LanzaExcepcion() {
        // ARRANGE
        List<MultipartFile> files = Arrays.asList(multipartFile);
        List<String> descripciones = Arrays.asList("Desc1", "Desc2"); // Más descripciones que archivos

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.subirImagenes(ALOJAMIENTO_ID_VALIDO, files, descripciones))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El tamaño de descripciones no coincide");
    }

    @Test
    @DisplayName("SUBIR IMAGENES - Fallo en Cloudinary hace rollback")
    void subirImagenes_FalloCloudinary_HaceRollback() throws IOException {
        // ARRANGE
        List<MultipartFile> files = Arrays.asList(multipartFile, multipartFile);

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        // Primera llamada exitosa, segunda falla
        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class)))
                .thenReturn(cloudinaryResult) // Primera llamada OK
                .thenThrow(new IOException("Error Cloudinary")); // Segunda llamada falla

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.subirImagenes(ALOJAMIENTO_ID_VALIDO, files, null))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Error Cloudinary");

        // Verificar que se intenta hacer rollback de la primera imagen
        verify(uploader, times(1)).destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class));
        verify(imagenDAO, never()).addImagen(anyInt(), anyString(), anyString(), any());
    }

    // ==================== LISTAR POR ALOJAMIENTO TESTS ====================

    @Test
    @DisplayName("LISTAR POR ALOJAMIENTO - Retorna lista de imágenes")
    void listarPorAlojamiento_RetornaListaImagenes() {
        // ARRANGE
        List<ImagenDTO> imagenesMock = Arrays.asList(imagenDTOMock);
        when(imagenDAO.findByAlojamiento(ALOJAMIENTO_ID_VALIDO)).thenReturn(imagenesMock);

        // ACT
        List<ImagenDTO> resultado = imagenService.listarPorAlojamiento(ALOJAMIENTO_ID_VALIDO);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getAlojamientoId()).isEqualTo(ALOJAMIENTO_ID_VALIDO.longValue());
        verify(imagenDAO).findByAlojamiento(ALOJAMIENTO_ID_VALIDO);
    }

    // ==================== REORDENAR TESTS ====================

    @Test
    @DisplayName("REORDENAR - Lista válida reordena exitosamente")
    void reordenar_ListaValida_ReordenaExitosamente() {
        // ARRANGE
        List<Integer> idsEnOrden = Arrays.asList(2, 1, 3);
        List<ImagenDTO> resultadoEsperado = Arrays.asList(imagenDTOMock);

        when(imagenDAO.reordenar(ALOJAMIENTO_ID_VALIDO, idsEnOrden)).thenReturn(resultadoEsperado);

        // ACT
        List<ImagenDTO> resultado = imagenService.reordenar(ALOJAMIENTO_ID_VALIDO, idsEnOrden);

        // ASSERT
        assertThat(resultado).isEqualTo(resultadoEsperado);
        verify(imagenDAO).reordenar(ALOJAMIENTO_ID_VALIDO, idsEnOrden);
    }

    // ==================== ELIMINAR IMAGEN TESTS ====================

    @Test
    @DisplayName("ELIMINAR IMAGEN - Imagen existe elimina exitosamente")
    void eliminarImagen_ImagenExiste_EliminaExitosamente() throws IOException {
        // ARRANGE
        when(imagenDAO.findById(IMAGEN_ID_VALIDO)).thenReturn(Optional.of(imagenDTOMock));
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenReturn(Map.of("result", "ok"));

        // ACT & ASSERT
        assertThatCode(() -> imagenService.eliminarImagen(IMAGEN_ID_VALIDO))
                .doesNotThrowAnyException();

        verify(uploader).destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class));
        verify(imagenDAO).delete(IMAGEN_ID_VALIDO);
    }

    @Test
    @DisplayName("ELIMINAR IMAGEN - Imagen no existe no hace nada")
    void eliminarImagen_ImagenNoExiste_NoHaceNada() throws IOException {
        // ARRANGE
        when(imagenDAO.findById(IMAGEN_ID_VALIDO)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatCode(() -> imagenService.eliminarImagen(IMAGEN_ID_VALIDO))
                .doesNotThrowAnyException();

        verify(uploader, never()).destroy(anyString(), any(Map.class));
        verify(imagenDAO, never()).delete(anyInt());
    }

    @Test
    @DisplayName("ELIMINAR IMAGEN - URL sin public_id no elimina en Cloudinary")
    void eliminarImagen_UrlSinPublicId_NoEliminaEnCloudinary() throws IOException {
        // ARRANGE
        ImagenDTO imagenSinPublicId = new ImagenDTO(
                IMAGEN_ID_VALIDO,
                "https://otro-servicio.com/imagen.jpg", // URL que no matchea el patrón
                "Descripción",
                ALOJAMIENTO_ID_VALIDO.longValue()
        );

        when(imagenDAO.findById(IMAGEN_ID_VALIDO)).thenReturn(Optional.of(imagenSinPublicId));

        // ACT & ASSERT
        assertThatCode(() -> imagenService.eliminarImagen(IMAGEN_ID_VALIDO))
                .doesNotThrowAnyException();

        verify(uploader, never()).destroy(anyString(), any(Map.class));
        verify(imagenDAO).delete(IMAGEN_ID_VALIDO);
    }

    @Test
    @DisplayName("ELIMINAR IMAGEN - Fallo en Cloudinary propaga IOException")
    void eliminarImagen_FalloCloudinary_PropagaIOException() throws IOException {
        // ARRANGE
        when(imagenDAO.findById(IMAGEN_ID_VALIDO)).thenReturn(Optional.of(imagenDTOMock));
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenThrow(new IOException("Error Cloudinary"));

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.eliminarImagen(IMAGEN_ID_VALIDO))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Error Cloudinary");

        // Cuando Cloudinary falla, NO se debe eliminar en BD
        verify(imagenDAO, never()).delete(anyInt());
    }

    // ==================== REEMPLAZAR TODAS TESTS ====================

    @Test
    @DisplayName("REEMPLAZAR TODAS - Lista válida reemplaza exitosamente")
    void reemplazarTodas_ListaValida_ReemplazaExitosamente() throws IOException {
        // ARRANGE
        List<MultipartFile> nuevasFiles = Arrays.asList(multipartFile);
        List<String> descripciones = Arrays.asList("Nueva descripción");

        List<ImagenDTO> imagenesActuales = Arrays.asList(imagenDTOMock);
        List<ImagenDTO> imagenesNuevas = Arrays.asList(imagenDTOMock);

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        when(imagenDAO.findByAlojamiento(ALOJAMIENTO_ID_VALIDO)).thenReturn(imagenesActuales);
        when(imagenDAO.addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), anyString(), isNull()))
                .thenReturn(imagenDTOMock);
        when(imagenDAO.findByAlojamiento(ALOJAMIENTO_ID_VALIDO)).thenReturn(imagenesNuevas);

        // ACT
        List<ImagenDTO> resultado = imagenService.reemplazarTodas(ALOJAMIENTO_ID_VALIDO, nuevasFiles, descripciones);

        // ASSERT
        assertThat(resultado).isEqualTo(imagenesNuevas);
        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
        verify(imagenDAO).addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), anyString(), isNull());
    }

    @Test
    @DisplayName("REEMPLAZAR TODAS - Lista vacía lanza excepción")
    void reemplazarTodas_ListaVacia_LanzaExcepcion() {
        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.reemplazarTodas(ALOJAMIENTO_ID_VALIDO, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Debes proporcionar al menos una imagen");
    }

    @Test
    @DisplayName("REEMPLAZAR TODAS - Tamaño descripciones no coincide lanza excepción")
    void reemplazarTodas_TamanioDescripcionesNoCoincide_LanzaExcepcion() {
        // ARRANGE
        List<MultipartFile> files = Arrays.asList(multipartFile);
        List<String> descripciones = Arrays.asList("Desc1", "Desc2"); // Más descripciones que archivos

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.reemplazarTodas(ALOJAMIENTO_ID_VALIDO, files, descripciones))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El tamaño de descripciones no coincide");
    }

    @Test
    @DisplayName("REEMPLAZAR TODAS - Fallo en subida hace rollback")
    void reemplazarTodas_FalloSubida_HaceRollback() throws IOException {
        // ARRANGE
        List<MultipartFile> nuevasFiles = Arrays.asList(multipartFile);

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.upload(any(byte[].class), any(Map.class)))
                .thenThrow(new IOException("Error Cloudinary"));

        // ACT & ASSERT
        assertThatThrownBy(() -> imagenService.reemplazarTodas(ALOJAMIENTO_ID_VALIDO, nuevasFiles, null))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Error Cloudinary");

        verify(uploader, never()).destroy(anyString(), any(Map.class));
        verify(imagenDAO, never()).addImagen(anyInt(), anyString(), anyString(), any());
    }

    // ==================== CASOS BORDE Y VALIDACIONES TESTS ====================

    @Test
    @DisplayName("SUBIR IMAGEN - Descripción nula es aceptada")
    void subirImagen_DescripcionNula_Aceptada() throws IOException {
        // ARRANGE
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        when(imagenDAO.addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), isNull(), isNull()))
                .thenReturn(imagenDTOMock);

        // ACT
        ImagenDTO resultado = imagenService.subirImagen(ALOJAMIENTO_ID_VALIDO, multipartFile, null);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(imagenDAO).addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), isNull(), isNull());
    }

    @Test
    @DisplayName("SUBIR IMAGENES - Sin descripciones es aceptado")
    void subirImagenes_SinDescripciones_Aceptado() throws IOException {
        // ARRANGE
        List<MultipartFile> files = Arrays.asList(multipartFile);

        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        when(imagenDAO.addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), isNull(), isNull()))
                .thenReturn(imagenDTOMock);

        // ACT
        List<ImagenDTO> resultado = imagenService.subirImagenes(ALOJAMIENTO_ID_VALIDO, files, null);

        // ASSERT
        assertThat(resultado).isNotEmpty();
        verify(imagenDAO).addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), isNull(), isNull());
    }

    @Test
    @DisplayName("SUBIR IMAGEN - Usa folder por defecto cuando properties está vacío")
    void subirImagen_FolderPropertiesVacio_UsaFolderPorDefecto() throws IOException {
        // ARRANGE
        when(cloudinaryProperties.getFolder()).thenReturn("");
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getBytes()).thenReturn(FILE_CONTENT);
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> cloudinaryResult = Map.of("secure_url", CLOUDINARY_SECURE_URL);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(cloudinaryResult);

        when(imagenDAO.addImagen(eq(ALOJAMIENTO_ID_VALIDO), eq(CLOUDINARY_SECURE_URL), eq(DESCRIPCION_VALIDA), isNull()))
                .thenReturn(imagenDTOMock);

        // ACT
        ImagenDTO resultado = imagenService.subirImagen(ALOJAMIENTO_ID_VALIDO, multipartFile, DESCRIPCION_VALIDA);

        // ASSERT
        assertThat(resultado).isNotNull();
        verify(uploader).upload(eq(FILE_CONTENT), any(Map.class));
    }

    @Test
    @DisplayName("ELIMINAR IMAGEN - Comportamiento consistente con fallo de Cloudinary")
    void eliminarImagen_ComportamientoFalloCloudinary() throws IOException {
        // ARRANGE
        when(imagenDAO.findById(IMAGEN_ID_VALIDO)).thenReturn(Optional.of(imagenDTOMock));
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.destroy(eq(CLOUDINARY_PUBLIC_ID), any(Map.class)))
                .thenThrow(new IOException("Error Cloudinary"));

        // ACT & ASSERT
        // Según el comportamiento actual del servicio, propaga la excepción
        assertThatThrownBy(() -> imagenService.eliminarImagen(IMAGEN_ID_VALIDO))
                .isInstanceOf(IOException.class);

        // Cuando Cloudinary falla, no se elimina en BD
        verify(imagenDAO, never()).delete(anyInt());
    }
}