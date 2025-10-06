package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicioAlojamientoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    ServicioAlojamientoDTO toDTO(ServicioAlojamiento entity);

    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "iconoUrl", ignore = true)
    ServicioAlojamiento toEntity(ServicioAlojamientoDTO dto);

    List<ServicioAlojamientoDTO> toDTOList(List<ServicioAlojamiento> servicios);
}