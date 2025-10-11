package co.uniquindio.alojapp.persistencia.DAO;

import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Imagen;
import co.uniquindio.alojapp.persistencia.Mapper.ImagenMapper;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.ImagenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DAO para operaciones de persistencia de imágenes de alojamientos.
 * RN3: Cada alojamiento debe tener al menos 1 imagen y máximo 10.
 *
 * Nota: Este DAO trabaja con URLs ya generadas.
 * La subida/borrado en Cloudinary u otro storage se hará en el Service superior.
 */
@Repository
@RequiredArgsConstructor
public class ImagenDAO {

    private static final int MAX_IMAGENES_POR_ALOJAMIENTO = 10;

    private final ImagenRepository imagenRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final ImagenMapper imagenMapper;

    // ===============================
    // Consultas
    // ===============================

    /** Listar imágenes por alojamiento (orden ascendente) */
    public List<ImagenDTO> findByAlojamiento(Integer alojamientoId) {
        List<Imagen> entidades = imagenRepository.findByAlojamientoIdOrderByOrden(alojamientoId);
        return imagenMapper.toDTOList(entidades);
    }

    /** Paginado por alojamiento conservando orden (orden + id para consistencia) */
    public PaginacionResponse<ImagenDTO> findByAlojamiento(Integer alojamientoId, int pagina, int tamano) {
        List<Imagen> todas = imagenRepository.findByAlojamientoIdOrderByOrden(alojamientoId);
        // Paginación manual porque el repo devuelve lista ordenada
        int from = Math.min(pagina * tamano, todas.size());
        int to = Math.min(from + tamano, todas.size());
        List<Imagen> slice = todas.subList(from, to);

        List<ImagenDTO> contenido = imagenMapper.toDTOList(slice);
        int total = todas.size();
        int totalPaginas = (int) Math.ceil((double) total / tamano);

        return PaginacionResponse.<ImagenDTO>builder()
                .contenido(contenido)
                .paginaActual(pagina)
                .tamanoPagina(tamano)
                .totalElementos((long) total)
                .totalPaginas(totalPaginas)
                .esPrimera(pagina == 0)
                .esUltima(pagina >= totalPaginas - 1)
                .tieneAnterior(pagina > 0)
                .tieneSiguiente(pagina < totalPaginas - 1)
                .build();
    }

    /** Buscar por id */
    public Optional<ImagenDTO> findById(Integer imagenId) {
        return imagenRepository.findById(imagenId).map(imagenMapper::toDTO);
    }

    /** Buscar por URL exacta */
    public List<ImagenDTO> findByUrl(String url) {
        return imagenMapper.toDTOList(imagenRepository.findByUrlImagen(url));
    }

    /** Contar imágenes del alojamiento */
    public Long countByAlojamiento(Integer alojamientoId) {
        return imagenRepository.countByAlojamientoId(alojamientoId);
    }

    /** ¿Tiene al menos una imagen? */
    public boolean tieneImagenes(Integer alojamientoId) {
        return imagenRepository.tieneImagenes(alojamientoId);
    }

    // ===============================
    // Creación
    // ===============================

    /**
     * Agregar una imagen (URL ya generada) respetando RN3 (máx. 10).
     * Si no se especifica orden, se coloca al final (max+1).
     */
    @Transactional
    public ImagenDTO addImagen(Integer alojamientoId, String url, String descripcion, Integer orden) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new NoSuchElementException("Alojamiento no encontrado"));

        long actuales = imagenRepository.countByAlojamientoId(alojamientoId);
        if (actuales >= MAX_IMAGENES_POR_ALOJAMIENTO) {
            throw new IllegalStateException("Se alcanzó el límite de 10 imágenes para este alojamiento");
        }

        // Calcular orden por defecto = último + 1
        int ordenFinal = (orden != null && orden > 0) ? orden : (int) actuales + 1;

        Imagen entidad = Imagen.builder()
                .alojamiento(alojamiento)
                .urlImagen(url)
                .descripcion(descripcion)
                .orden(ordenFinal)
                .build();

        Imagen guardada = imagenRepository.save(entidad);
        return imagenMapper.toDTO(guardada);
    }

    /**
     * Agregar varias imágenes en bloque (batch).
     * Lanza excepción si el total superaría 10.
     */
    @Transactional
    public List<ImagenDTO> addImagenes(Integer alojamientoId, List<ImagenDTO> nuevas) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new NoSuchElementException("Alojamiento no encontrado"));

        long actuales = imagenRepository.countByAlojamientoId(alojamientoId);
        if (actuales + nuevas.size() > MAX_IMAGENES_POR_ALOJAMIENTO) {
            throw new IllegalStateException("El total de imágenes supera el máximo permitido (10)");
        }

        // Determinar orden inicial
        int nextOrder = (int) actuales + 1;
        List<Imagen> aGuardar = new ArrayList<>();

        for (ImagenDTO dto : nuevas) {
            Imagen entidad = Imagen.builder()
                    .alojamiento(alojamiento)
                    .urlImagen(dto.getUrl())
                    .descripcion(dto.getDescripcion())
                    .orden((dto.getId() != null && dto.getId() > 0) ? nextOrder : nextOrder) // siempre secuencial
                    .build();
            aGuardar.add(entidad);
            nextOrder++;
        }

        List<Imagen> guardadas = imagenRepository.saveAll(aGuardar);
        return imagenMapper.toDTOList(guardadas);
    }

    // ===============================
    // Actualización
    // ===============================

    /** Actualizar descripción y/o orden de una imagen */
    @Transactional
    public ImagenDTO actualizar(Integer imagenId, String descripcion, Integer nuevoOrden) {
        Imagen img = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NoSuchElementException("Imagen no encontrada"));

        if (descripcion != null) img.setDescripcion(descripcion);
        if (nuevoOrden != null && nuevoOrden > 0) img.setOrden(nuevoOrden);

        return imagenMapper.toDTO(imagenRepository.save(img));
    }

    /**
     * Reordenar todas las imágenes de un alojamiento.
     * Recibe la lista de IDs en el orden deseado (1..N).
     */
    @Transactional
    public List<ImagenDTO> reordenar(Integer alojamientoId, List<Integer> idsEnOrden) {
        List<Imagen> actuales = imagenRepository.findByAlojamientoIdOrderByOrden(alojamientoId);
        Set<Integer> idsAloj = actuales.stream().map(Imagen::getId).collect(Collectors.toSet());

        // Validar consistencia
        if (!idsAloj.containsAll(idsEnOrden) || idsEnOrden.size() != actuales.size()) {
            throw new IllegalArgumentException("La lista de IDs no coincide con las imágenes actuales del alojamiento");
        }

        Map<Integer, Integer> nuevoOrden = new HashMap<>();
        int orden = 1;
        for (Integer id : idsEnOrden) {
            nuevoOrden.put(id, orden++);
        }

        for (Imagen i : actuales) {
            i.setOrden(nuevoOrden.get(i.getId()));
        }

        List<Imagen> guardadas = imagenRepository.saveAll(actuales);
        // Devuelve ya ordenadas
        guardadas.sort(Comparator.comparingInt(Imagen::getOrden));
        return imagenMapper.toDTOList(guardadas);
    }

    // ===============================
    // Eliminación
    // ===============================

    /**
     * Eliminar una imagen. RN3: impedir que el alojamiento quede sin imágenes.
     * Devuelve true si se eliminó; false si no existía.
     */
    @Transactional
    public boolean delete(Integer imagenId) {
        Optional<Imagen> imgOpt = imagenRepository.findById(imagenId);
        if (imgOpt.isEmpty()) return false;

        Imagen img = imgOpt.get();
        Integer alojamientoId = img.getAlojamiento().getId();

        long restantes = imagenRepository.countByAlojamientoId(alojamientoId);
        if (restantes <= 1) {
            throw new IllegalStateException("El alojamiento debe conservar al menos 1 imagen");
        }

        imagenRepository.delete(img);
        // Recompactar orden tras borrar
        compactarOrden(alojamientoId);

        return true;
    }

    /**
     * Eliminar todas las imágenes de un alojamiento. RN3: No permitimos dejar en 0.
     * Si quieres borrar todas para reemplazar, usa replaceAll (ver Service superior).
     */
    @Transactional
    public void deleteAllByAlojamiento(Integer alojamientoId) {
        long count = imagenRepository.countByAlojamientoId(alojamientoId);
        if (count > 0) {
            throw new IllegalStateException("No se permite eliminar todas las imágenes (RN3). Usa reemplazo controlado en el Service.");
        }
        imagenRepository.deleteByAlojamientoId(alojamientoId);
    }

    // ===============================
    // Helpers
    // ===============================

    /** Recompacta el orden 1..N después de un borrado/movimiento */
    @Transactional
    protected void compactarOrden(Integer alojamientoId) {
        List<Imagen> imgs = imagenRepository.findByAlojamientoIdOrderByOrden(alojamientoId);
        int orden = 1;
        for (Imagen i : imgs) {
            i.setOrden(orden++);
        }
        imagenRepository.saveAll(imgs);
    }
}
