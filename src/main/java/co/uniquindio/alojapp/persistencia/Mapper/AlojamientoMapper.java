package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Imagen;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AlojamientoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "ciudad", source = "ciudad")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "latitud", source = "latitud")
    @Mapping(target = "longitud", source = "longitud")
    @Mapping(target = "precioNoche", source = "precioNoche")
    @Mapping(target = "capacidadMaxima", source = "capacidadMaxima")
    @Mapping(target = "imagenPrincipalUrl", source = "imagenPrincipalUrl")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    @Mapping(target = "fechaActualizacion", source = "fechaActualizacion")
    @Mapping(target = "anfitrionId", source = "anfitrion.id")
    @Mapping(target = "anfitrionNombre", source = "anfitrion.usuario.nombre")
    @Mapping(target = "imagenes", expression = "java(mapImagenes(alojamiento))")
    @Mapping(target = "servicios", source = "servicios")
    @Mapping(target = "calificacionPromedio", expression = "java(alojamiento.calcularCalificacionPromedio())")
    @Mapping(target = "cantidadComentarios", expression = "java(alojamiento.getComentarios() != null ? alojamiento.getComentarios().size() : 0)")
    AlojamientoDTO toDTO(Alojamiento alojamiento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Alojamiento toEntity(AlojamientoDTO dto);

    List<AlojamientoDTO> toDTOList(List<Alojamiento> alojamientos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateFromDTO(@MappingTarget Alojamiento alojamiento, AlojamientoDTO dto);

    // Método helper para mapear imágenes
    default List<String> mapImagenes(Alojamiento alojamiento) {
        if (alojamiento.getImagenes() == null) {
            return List.of();
        }
        return alojamiento.getImagenes().stream()
                .map(Imagen::getUrlImagen)
                .collect(Collectors.toList());
    }
}