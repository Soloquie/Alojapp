package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

/**
 * Mapper responsable de convertir entre las entidades Alojamiento y sus DTOs correspondientes.
 * Utiliza MapStruct para la generación automática del código de mapeo.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AlojamientoMapper {

    // ===============================
    // ENTITY → DTO
    // ===============================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "ubicacion", source = "ubicacion")
    @Mapping(target = "precioPorNoche", source = "precioPorNoche")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "anfitrionId", source = "anfitrion.id")
    AlojamientoDTO alojamientoToDTO(Alojamiento alojamiento);

    // ===============================
    // DTO → ENTITY (para crear)
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    Alojamiento dtoToAlojamiento(AlojamientoDTO dto);

    // ===============================
    // LISTA ENTITY → LISTA DTO
    // ===============================
    List<AlojamientoDTO> alojamientosToDTO(List<Alojamiento> alojamientos);

    // ===============================
    // ACTUALIZAR ENTITY EXISTENTE DESDE DTO
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anfitrion", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    void updateAlojamientoFromDTO(@MappingTarget Alojamiento alojamiento, AlojamientoDTO dto);
}
