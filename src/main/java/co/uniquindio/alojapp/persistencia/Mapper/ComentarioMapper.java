package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

/**
 * Mapper responsable de convertir entre la entidad Comentario y su DTO correspondiente.
 * Gestiona los mapeos para la documentación y capa de servicio.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ComentarioMapper {

    // ===============================
    // ENTITY → DTO
    // ===============================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "contenido", source = "contenido")
    @Mapping(target = "calificacion", source = "calificacion")
    @Mapping(target = "fechaPublicacion", source = "fechaPublicacion")
    @Mapping(target = "autorId", source = "autor.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    ComentarioDTO comentarioToDTO(Comentario comentario);

    // ===============================
    // DTO → ENTITY (para crear)
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    Comentario dtoToComentario(ComentarioDTO dto);

    // ===============================
    // LISTA ENTITY → LISTA DTO
    // ===============================
    List<ComentarioDTO> comentariosToDTO(List<Comentario> comentarios);

    // ===============================
    // ACTUALIZAR ENTITY EXISTENTE DESDE DTO
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    void updateComentarioFromDTO(@MappingTarget Comentario comentario, ComentarioDTO dto);
}
