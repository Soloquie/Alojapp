package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImagenService {

    /** Sube UNA imagen a Cloudinary y la registra en BD (respeta RN3) */
    ImagenDTO subirImagen(Integer alojamientoId, MultipartFile file, String descripcion) throws IOException;

    /** Sube VARIAS imágenes (batch) respetando RN3 */
    List<ImagenDTO> subirImagenes(Integer alojamientoId, List<MultipartFile> files, List<String> descripciones) throws IOException;

    /** Lista las imágenes (orden ascendente) */
    List<ImagenDTO> listarPorAlojamiento(Integer alojamientoId);

    /** Reordena por lista de IDs (1..N) */
    List<ImagenDTO> reordenar(Integer alojamientoId, List<Integer> idsEnOrden);

    /** Elimina una imagen (Cloudinary + BD). Evita dejar 0 imágenes (RN3) */
    void eliminarImagen(Integer imagenId) throws IOException;

    /**
     * Reemplaza TODAS las imágenes de un alojamiento por un nuevo set.
     * Implementa "todo o nada": si falla la subida, no altera BD.
     */
    List<ImagenDTO> reemplazarTodas(Integer alojamientoId, List<MultipartFile> nuevas, List<String> descripciones) throws IOException;
}
