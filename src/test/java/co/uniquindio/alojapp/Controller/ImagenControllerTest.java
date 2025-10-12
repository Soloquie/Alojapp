package co.uniquindio.alojapp.Controller;

import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.negocio.Service.ImagenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests para ImagenController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ImagenController - Unit Tests")
public class ImagenControllerTest {

    @Mock
    private ImagenService imagenService;

    @InjectMocks
    private ImagenController imagenController;

    private ImagenDTO imagenDTO;
    private MultipartFile multipartFile;
    private List<MultipartFile> multipartFiles;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes
        imagenDTO = new ImagenDTO(
                501,
                "https://alojapp.com/images/alojamientos/501.jpg",
                "Vista frontal de la casa",
                15L
        );

        multipartFile = mock(MultipartFile.class);
        multipartFiles = Arrays.asList(multipartFile, multipartFile);
    }

    // =========================================================================
    // TESTS PARA LISTAR IMÁGENES POR ALOJAMIENTO
    // =========================================================================

    @Test
    @DisplayName("Listar imágenes por alojamiento - Debería retornar 200 OK")
    void listarPorAlojamiento_DeberiaRetornar200Ok() {
        // Arrange
        Integer alojamientoId = 15;
        List<ImagenDTO> imagenes = Arrays.asList(imagenDTO);
        when(imagenService.listarPorAlojamiento(alojamientoId)).thenReturn(imagenes);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.listarPorAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(imagenes);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getAlojamientoId()).isEqualTo(15L);
        assertThat(response.getBody().get(0).getUrl()).contains("alojapp.com");

        verify(imagenService, times(1)).listarPorAlojamiento(alojamientoId);
    }

    @Test
    @DisplayName("Listar imágenes por alojamiento - Lista vacía debería retornar 200 OK")
    void listarPorAlojamiento_ListaVacia_DeberiaRetornar200Ok() {
        // Arrange
        Integer alojamientoId = 999;
        List<ImagenDTO> imagenesVacias = Arrays.asList();
        when(imagenService.listarPorAlojamiento(alojamientoId)).thenReturn(imagenesVacias);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.listarPorAlojamiento(alojamientoId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();

        verify(imagenService, times(1)).listarPorAlojamiento(alojamientoId);
    }

    // =========================================================================
    // TESTS PARA SUBIR UNA IMAGEN
    // =========================================================================

    @Test
    @DisplayName("Subir imagen - Debería retornar 201 CREATED")
    void subirImagen_DeberiaRetornar201Created() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        String descripcion = "Vista frontal de la casa";
        when(imagenService.subirImagen(eq(alojamientoId), any(MultipartFile.class), eq(descripcion)))
                .thenReturn(imagenDTO);

        // Act
        ResponseEntity<ImagenDTO> response = imagenController.subirImagen(alojamientoId, multipartFile, descripcion);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(imagenDTO);
        assertThat(response.getBody().getId()).isEqualTo(501);
        assertThat(response.getBody().getDescripcion()).isEqualTo("Vista frontal de la casa");

        verify(imagenService, times(1)).subirImagen(alojamientoId, multipartFile, descripcion);
    }

    @Test
    @DisplayName("Subir imagen - Sin descripción debería procesar correctamente")
    void subirImagen_SinDescripcion_DeberiaProcesarCorrectamente() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        when(imagenService.subirImagen(eq(alojamientoId), any(MultipartFile.class), isNull()))
                .thenReturn(imagenDTO);

        // Act
        ResponseEntity<ImagenDTO> response = imagenController.subirImagen(alojamientoId, multipartFile, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(imagenService, times(1)).subirImagen(alojamientoId, multipartFile, null);
    }

    // =========================================================================
    // TESTS PARA SUBIR VARIAS IMÁGENES (BATCH)
    // =========================================================================

    @Test
    @DisplayName("Subir imágenes batch - Debería retornar 201 CREATED")
    void subirImagenes_DeberiaRetornar201Created() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        List<String> descripciones = Arrays.asList("Fachada", "Interior");
        List<ImagenDTO> imagenesCreadas = Arrays.asList(imagenDTO, imagenDTO);

        when(imagenService.subirImagenes(eq(alojamientoId), anyList(), eq(descripciones)))
                .thenReturn(imagenesCreadas);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.subirImagenes(alojamientoId, multipartFiles, descripciones);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(imagenesCreadas);
        assertThat(response.getBody()).hasSize(2);

        verify(imagenService, times(1)).subirImagenes(alojamientoId, multipartFiles, descripciones);
    }

    @Test
    @DisplayName("Subir imágenes batch - Sin descripciones debería procesar correctamente")
    void subirImagenes_SinDescripciones_DeberiaProcesarCorrectamente() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        List<ImagenDTO> imagenesCreadas = Arrays.asList(imagenDTO);

        when(imagenService.subirImagenes(eq(alojamientoId), anyList(), isNull()))
                .thenReturn(imagenesCreadas);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.subirImagenes(alojamientoId, multipartFiles, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(imagenService, times(1)).subirImagenes(alojamientoId, multipartFiles, null);
    }

    // =========================================================================
    // TESTS PARA REORDENAR IMÁGENES
    // =========================================================================

    @Test
    @DisplayName("Reordenar imágenes - Debería retornar 200 OK")
    void reordenar_DeberiaRetornar200Ok() {
        // Arrange
        Integer alojamientoId = 15;
        List<Integer> idsEnOrden = Arrays.asList(3, 1, 2);
        List<ImagenDTO> imagenesReordenadas = Arrays.asList(imagenDTO);

        when(imagenService.reordenar(alojamientoId, idsEnOrden)).thenReturn(imagenesReordenadas);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.reordenar(alojamientoId, idsEnOrden);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(imagenesReordenadas);
        assertThat(response.getBody()).hasSize(1);

        verify(imagenService, times(1)).reordenar(alojamientoId, idsEnOrden);
    }

    @Test
    @DisplayName("Reordenar imágenes - Diferentes órdenes deberían procesarse correctamente")
    void reordenar_DiferentesOrdenes_DeberianProcesarseCorrectamente() {
        // Arrange
        Integer alojamientoId = 15;
        List<Integer> idsEnOrden1 = Arrays.asList(1, 2, 3);
        List<Integer> idsEnOrden2 = Arrays.asList(2, 3, 1);
        List<ImagenDTO> imagenesReordenadas = Arrays.asList(imagenDTO);

        when(imagenService.reordenar(alojamientoId, idsEnOrden1)).thenReturn(imagenesReordenadas);
        when(imagenService.reordenar(alojamientoId, idsEnOrden2)).thenReturn(imagenesReordenadas);

        // Act
        ResponseEntity<List<ImagenDTO>> response1 = imagenController.reordenar(alojamientoId, idsEnOrden1);
        ResponseEntity<List<ImagenDTO>> response2 = imagenController.reordenar(alojamientoId, idsEnOrden2);

        // Assert
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(imagenService, times(1)).reordenar(alojamientoId, idsEnOrden1);
        verify(imagenService, times(1)).reordenar(alojamientoId, idsEnOrden2);
    }

    // =========================================================================
    // TESTS PARA ELIMINAR IMAGEN
    // =========================================================================

    @Test
    @DisplayName("Eliminar imagen - Debería retornar 204 NO_CONTENT")
    void eliminar_DeberiaRetornar204NoContent() throws IOException {
        // Arrange
        Integer imagenId = 501;
        doNothing().when(imagenService).eliminarImagen(imagenId);

        // Act
        ResponseEntity<Void> response = imagenController.eliminar(imagenId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        verify(imagenService, times(1)).eliminarImagen(imagenId);
    }

    @Test
    @DisplayName("Eliminar imagen - Diferentes IDs deberían procesarse correctamente")
    void eliminar_DiferentesIds_DeberianProcesarseCorrectamente() throws IOException {
        // Arrange
        Integer imagenId1 = 501;
        Integer imagenId2 = 502;
        doNothing().when(imagenService).eliminarImagen(anyInt());

        // Act
        ResponseEntity<Void> response1 = imagenController.eliminar(imagenId1);
        ResponseEntity<Void> response2 = imagenController.eliminar(imagenId2);

        // Assert
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(imagenService, times(1)).eliminarImagen(imagenId1);
        verify(imagenService, times(1)).eliminarImagen(imagenId2);
    }

    // =========================================================================
    // TESTS PARA REEMPLAZAR TODAS LAS IMÁGENES
    // =========================================================================

    @Test
    @DisplayName("Reemplazar todas las imágenes - Debería retornar 200 OK")
    void reemplazarTodas_DeberiaRetornar200Ok() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        List<String> descripciones = Arrays.asList("Nueva fachada", "Nuevo interior");
        List<ImagenDTO> imagenesReemplazadas = Arrays.asList(imagenDTO, imagenDTO);

        when(imagenService.reemplazarTodas(eq(alojamientoId), anyList(), eq(descripciones)))
                .thenReturn(imagenesReemplazadas);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.reemplazarTodas(alojamientoId, multipartFiles, descripciones);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(imagenesReemplazadas);
        assertThat(response.getBody()).hasSize(2);

        verify(imagenService, times(1)).reemplazarTodas(alojamientoId, multipartFiles, descripciones);
    }

    @Test
    @DisplayName("Reemplazar todas las imágenes - Sin descripciones debería procesar correctamente")
    void reemplazarTodas_SinDescripciones_DeberiaProcesarCorrectamente() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        List<ImagenDTO> imagenesReemplazadas = Arrays.asList(imagenDTO);

        when(imagenService.reemplazarTodas(eq(alojamientoId), anyList(), isNull()))
                .thenReturn(imagenesReemplazadas);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.reemplazarTodas(alojamientoId, multipartFiles, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(imagenService, times(1)).reemplazarTodas(alojamientoId, multipartFiles, null);
    }

    // =========================================================================
    // TESTS DE CASOS BORDE Y VALIDACIONES
    // =========================================================================

    @Test
    @DisplayName("ImagenDTO - Campos completos tienen valores correctos")
    void imagenDTO_CamposCompletos_TienenValoresCorrectos() {
        // Arrange & Act
        ImagenDTO dtoCompleto = new ImagenDTO(
                502,
                "https://alojapp.com/images/alojamientos/502.jpg",
                "Vista de la piscina",
                20L
        );

        // Assert
        assertThat(dtoCompleto.getId()).isEqualTo(502);
        assertThat(dtoCompleto.getUrl()).isEqualTo("https://alojapp.com/images/alojamientos/502.jpg");
        assertThat(dtoCompleto.getDescripcion()).isEqualTo("Vista de la piscina");
        assertThat(dtoCompleto.getAlojamientoId()).isEqualTo(20L);
    }

    @Test
    @DisplayName("Múltiples operaciones con el mismo alojamiento")
    void multiplesOperaciones_MismoAlojamiento_DeberianProcesarseCorrectamente() throws IOException {
        // Arrange
        Integer alojamientoId = 15;
        List<ImagenDTO> imagenes = Arrays.asList(imagenDTO);
        when(imagenService.listarPorAlojamiento(alojamientoId)).thenReturn(imagenes);
        when(imagenService.subirImagen(eq(alojamientoId), any(MultipartFile.class), anyString()))
                .thenReturn(imagenDTO);
        doNothing().when(imagenService).eliminarImagen(anyInt());

        // Act
        ResponseEntity<List<ImagenDTO>> listarResponse = imagenController.listarPorAlojamiento(alojamientoId);
        ResponseEntity<ImagenDTO> subirResponse = imagenController.subirImagen(alojamientoId, multipartFile, "Desc");
        ResponseEntity<Void> eliminarResponse = imagenController.eliminar(501);

        // Assert
        assertThat(listarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(subirResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(eliminarResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(imagenService, times(1)).listarPorAlojamiento(alojamientoId);
        verify(imagenService, times(1)).subirImagen(eq(alojamientoId), any(MultipartFile.class), anyString());
        verify(imagenService, times(1)).eliminarImagen(anyInt());
    }

    @Test
    @DisplayName("Operaciones con diferentes alojamientos")
    void operaciones_DiferentesAlojamientos_DeberianProcesarseCorrectamente() {
        // Arrange
        Integer alojamientoId1 = 15;
        Integer alojamientoId2 = 20;
        List<ImagenDTO> imagenes1 = Arrays.asList(imagenDTO);
        List<ImagenDTO> imagenes2 = Arrays.asList();

        when(imagenService.listarPorAlojamiento(alojamientoId1)).thenReturn(imagenes1);
        when(imagenService.listarPorAlojamiento(alojamientoId2)).thenReturn(imagenes2);

        // Act
        ResponseEntity<List<ImagenDTO>> response1 = imagenController.listarPorAlojamiento(alojamientoId1);
        ResponseEntity<List<ImagenDTO>> response2 = imagenController.listarPorAlojamiento(alojamientoId2);

        // Assert
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).hasSize(1);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEmpty();

        verify(imagenService, times(1)).listarPorAlojamiento(alojamientoId1);
        verify(imagenService, times(1)).listarPorAlojamiento(alojamientoId2);
    }

    @Test
    @DisplayName("Reordenar - Lista vacía debería procesarse correctamente")
    void reordenar_ListaVacia_DeberiaProcesarseCorrectamente() {
        // Arrange
        Integer alojamientoId = 15;
        List<Integer> idsVacios = Arrays.asList();
        List<ImagenDTO> imagenesVacias = Arrays.asList();

        when(imagenService.reordenar(alojamientoId, idsVacios)).thenReturn(imagenesVacias);

        // Act
        ResponseEntity<List<ImagenDTO>> response = imagenController.reordenar(alojamientoId, idsVacios);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();

        verify(imagenService, times(1)).reordenar(alojamientoId, idsVacios);
    }

    @Test
    @DisplayName("ImagenDTO - Constructor sin argumentos funciona correctamente")
    void imagenDTO_ConstructorSinArgumentos_FuncionaCorrectamente() {
        // Arrange & Act
        ImagenDTO imagenVacia = new ImagenDTO();
        imagenVacia.setId(503);
        imagenVacia.setUrl("https://alojapp.com/images/alojamientos/503.jpg");
        imagenVacia.setDescripcion("Vista del jardín");
        imagenVacia.setAlojamientoId(25L);

        // Assert
        assertThat(imagenVacia.getId()).isEqualTo(503);
        assertThat(imagenVacia.getUrl()).isEqualTo("https://alojapp.com/images/alojamientos/503.jpg");
        assertThat(imagenVacia.getDescripcion()).isEqualTo("Vista del jardín");
        assertThat(imagenVacia.getAlojamientoId()).isEqualTo(25L);
    }
}