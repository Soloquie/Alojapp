package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.RespuestaComentarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.RespuestaComentario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RespuestaComentarioMapper {

    // === Entity → DTO ===
    @Mapping(target = "comentarioId", source = "comentario.id")
    @Mapping(target = "autorNombre", source = "autor.nombre")
    RespuestaComentarioDTO toDTO(RespuestaComentario entity);

    // === DTO → Entity ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comentario", ignore = true)
    @Mapping(target = "autor", ignore = true)
    RespuestaComentario toEntity(RespuestaComentarioDTO dto);

    // === List<Entity> → List<DTO> ===
    List<RespuestaComentarioDTO> toDTOList(List<RespuestaComentario> respuestas);

    // === Actualización (DTO → Entity existente) ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comentario", ignore = true)
    @Mapping(target = "autor", ignore = true)
    void updateFromDTO(@MappingTarget RespuestaComentario entity, RespuestaComentarioDTO dto);
}
