package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.FavoritoDTO;
import co.uniquindio.alojapp.persistencia.Entity.Favorito;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavoritoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    @Mapping(target = "nombreAlojamiento", source = "alojamiento.titulo")
    FavoritoDTO toDTO(Favorito entity);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "fechaAgregado", ignore = true)
    Favorito toEntity(FavoritoDTO dto);

    List<FavoritoDTO> toDTOList(List<Favorito> favoritos);
}