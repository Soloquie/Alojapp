package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ComentarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.Comentario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RespuestaComentarioMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComentarioMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "reservaId", source = "reserva.id")
    @Mapping(target = "usuarioId", source = "autor.id")
    @Mapping(target = "usuarioNombre", source = "autor.nombre")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    @Mapping(target = "calificacion", source = "calificacion")
    @Mapping(target = "comentarioTexto", source = "comentarioTexto")
    @Mapping(target = "fechaComentario", source = "fechaComentario")
    @Mapping(target = "respuestas", source = "respuestas")
    ComentarioDTO toDTO(Comentario comentario);

    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "respuestas", ignore = true)
    @Mapping(target = "fechaComentario", ignore = true)
    Comentario toEntity(ComentarioDTO dto);

    List<ComentarioDTO> toDTOList(List<Comentario> comentarios);
}