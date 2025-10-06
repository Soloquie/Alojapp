package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AdministradorMapper {

    // === Entity → DTO ===
    AdministradorDTO toDTO(Administrador admin);

    // === DTO → Entity ===
    @Mapping(target = "id", ignore = true)
    Administrador toEntity(AdministradorDTO dto);

    // === List<Entity> → List<DTO> ===
    List<AdministradorDTO> toDTOList(List<Administrador> admins);

    // === Actualización (DTO → Entity existente) ===
    @Mapping(target = "id", ignore = true)
    void updateFromDTO(@MappingTarget Administrador admin, AdministradorDTO dto);
}
