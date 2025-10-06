package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.persistencia.Entity.Favorito;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FavoritoMapper {

    // === Entity → DTO ===
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    @Mapping(target = "nombreAlojamiento", source = "alojamiento.titulo")
    FavoritoDTO toDTO(Favorito entity);

    // === DTO → Entity ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    Favorito toEntity(FavoritoDTO dto);

    // === List<Entity> → List<DTO> ===
    List<FavoritoDTO> toDTOList(List<Favorito> favoritos);

    // === Actualización (DTO → Entity existente) ===
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    void updateFromDTO(@MappingTarget Favorito entity, FavoritoDTO dto);
}
