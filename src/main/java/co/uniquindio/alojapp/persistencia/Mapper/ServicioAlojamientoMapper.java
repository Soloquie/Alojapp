package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ServicioAlojamientoMapper {

    // === Entity → DTO ===
    ServicioAlojamientoDTO toDTO(ServicioAlojamiento entity);

    // === DTO → Entity ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    ServicioAlojamiento toEntity(ServicioAlojamientoDTO dto);

    // === List<Entity> → List<DTO> ===
    List<ServicioAlojamientoDTO> toDTOList(List<ServicioAlojamiento> servicios);

    // === Actualización (DTO → Entity existente) ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    void updateFromDTO(@MappingTarget ServicioAlojamiento entity, ServicioAlojamientoDTO dto);
}
