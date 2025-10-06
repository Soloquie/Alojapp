package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

/**
 * Mapper responsable de convertir entre las entidades Reserva y sus DTOs correspondientes.
 * Utiliza MapStruct para automatizar el proceso de conversión.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ReservaMapper {

    // ===============================
    // ENTITY → DTO
    // ===============================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fechaInicio", source = "fechaInicio")
    @Mapping(target = "fechaFin", source = "fechaFin")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "huespedId", source = "huesped.id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    @Mapping(target = "pagoId", source = "pago.id")
    ReservaDTO reservaToDTO(Reserva reserva);

    // ===============================
    // DTO → ENTITY (para crear)
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "pago", ignore = true)
    Reserva dtoToReserva(ReservaDTO dto);

    // ===============================
    // LISTA ENTITY → LISTA DTO
    // ===============================
    List<ReservaDTO> reservasToDTO(List<Reserva> reservas);

    // ===============================
    // ACTUALIZAR ENTITY EXISTENTE DESDE DTO
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "pago", ignore = true)
    void updateReservaFromDTO(@MappingTarget Reserva reserva, ReservaDTO dto);
}
