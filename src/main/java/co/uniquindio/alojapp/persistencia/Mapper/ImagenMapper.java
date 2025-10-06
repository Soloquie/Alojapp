package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.persistencia.Entity.Imagen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper encargado de la conversión entre la entidad Imagen y su DTO correspondiente.
 * Facilita el paso de datos entre las capas de persistencia y negocio.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ImagenMapper {

    // ===============================
    // ENTITY → DTO
    // ===============================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    ImagenDTO imagenToDTO(Imagen imagen);

    // ===============================
    // DTO → ENTITY (para crear)
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    Imagen dtoToImagen(ImagenDTO dto);

    // ===============================
    // LISTA ENTITY → LISTA DTO
    // ===============================
    List<ImagenDTO> imagenesToDTO(List<Imagen> imagenes);

    // ===============================
    // ACTUALIZAR ENTITY EXISTENTE DESDE DTO
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    void updateImagenFromDTO(@MappingTarget Imagen imagen, ImagenDTO dto);
}
