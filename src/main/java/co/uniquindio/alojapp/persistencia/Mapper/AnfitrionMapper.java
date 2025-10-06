package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.AnfitrionDTO;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnfitrionMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "usuario.nombre")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "telefono", source = "usuario.telefono")
    @Mapping(target = "cantidadAlojamientos", expression = "java(anfitrion.getAlojamientos() != null ? anfitrion.getAlojamientos().size() : 0)")
    @Mapping(target = "calificacionPromedio", constant = "0.0")
    AnfitrionDTO toDTO(Anfitrion anfitrion);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "respuestas", ignore = true)
    @Mapping(target = "descripcionPersonal", ignore = true)
    @Mapping(target = "documentosLegalesUrl", ignore = true)
    @Mapping(target = "fechaRegistroAnfitrion", ignore = true)
    @Mapping(target = "verificado", ignore = true)
    Anfitrion toEntity(AnfitrionDTO dto);

    List<AnfitrionDTO> toDTOList(List<Anfitrion> anfitriones);
}