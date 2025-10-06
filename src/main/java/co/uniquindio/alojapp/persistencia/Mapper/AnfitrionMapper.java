package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.AnfitrionDTO;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AnfitrionMapper {

    // === Entity → DTO ===
    AnfitrionDTO toDTO(Anfitrion entity);

    // === DTO → Entity ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    Anfitrion toEntity(AnfitrionDTO dto);

    // === List<Entity> → List<DTO> ===
    List<AnfitrionDTO> toDTOList(List<Anfitrion> anfitriones);

    // === Actualización (DTO → Entity existente) ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    void updateFromDTO(@MappingTarget Anfitrion entity, AnfitrionDTO dto);
}
