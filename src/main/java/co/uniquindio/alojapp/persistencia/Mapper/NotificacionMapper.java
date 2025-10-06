package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.NotificacionDTO;
import co.uniquindio.alojapp.persistencia.Entity.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper encargado de la conversión entre la entidad Notificacion y su DTO correspondiente.
 * Permite transformar objetos entre las capas de persistencia y negocio.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface NotificacionMapper {

    // ===============================
    // ENTITY → DTO
    // ===============================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "mensaje", source = "mensaje")
    @Mapping(target = "fechaEnvio", source = "fechaEnvio")
    @Mapping(target = "leida", source = "leida")
    @Mapping(target = "usuarioId", source = "usuario.id")
    NotificacionDTO notificacionToDTO(Notificacion notificacion);

    // ===============================
    // DTO → ENTITY (para crear)
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Notificacion dtoToNotificacion(NotificacionDTO dto);

    // ===============================
    // LISTA ENTITY → LISTA DTO
    // ===============================
    List<NotificacionDTO> notificacionesToDTO(List<Notificacion> notificaciones);

    // ===============================
    // ACTUALIZAR ENTITY EXISTENTE DESDE DTO
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateNotificacionFromDTO(@MappingTarget Notificacion notificacion, NotificacionDTO dto);
}
