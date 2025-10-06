package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ImagenDTO;
import co.uniquindio.alojapp.persistencia.Entity.Imagen;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImagenMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "url", source = "urlImagen")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    ImagenDTO toDTO(Imagen imagen);

    @Mapping(target = "urlImagen", source = "url")
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "orden", ignore = true)
    Imagen toEntity(ImagenDTO dto);

    List<ImagenDTO> toDTOList(List<Imagen> imagenes);

    @Mapping(target = "alojamiento", ignore = true)
    void updateFromDTO(@MappingTarget Imagen imagen, ImagenDTO dto);
}