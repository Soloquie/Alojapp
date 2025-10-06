package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.RespuestaComentarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.RespuestaComentario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RespuestaComentarioMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "contenido", source = "respuestaTexto")
    @Mapping(target = "fechaRespuesta", source = "fechaRespuesta")
    @Mapping(target = "comentarioId", source = "comentario.id")
    @Mapping(target = "autorNombre", source = "anfitrion.usuario.nombre")
    RespuestaComentarioDTO toDTO(RespuestaComentario entity);

    @Mapping(target = "respuestaTexto", source = "contenido")
    @Mapping(target = "comentario", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "fechaRespuesta", ignore = true)
    RespuestaComentario toEntity(RespuestaComentarioDTO dto);

    List<RespuestaComentarioDTO> toDTOList(List<RespuestaComentario> respuestas);
}