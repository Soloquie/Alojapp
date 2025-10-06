package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.ReservaDTO;
import co.uniquindio.alojapp.persistencia.Entity.Reserva;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ReservaMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "alojamientoId", source = "alojamiento.id")
    @Mapping(target = "alojamientoNombre", source = "alojamiento.titulo")
    @Mapping(target = "huespedId", source = "huesped.id")
    @Mapping(target = "huespedNombre", source = "huesped.nombre")
    @Mapping(target = "fechaCheckin", source = "fechaCheckin")
    @Mapping(target = "fechaCheckout", source = "fechaCheckout")
    @Mapping(target = "numeroHuespedes", source = "numeroHuespedes")
    @Mapping(target = "precioTotal", source = "precioTotal")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    @Mapping(target = "fechaCancelacion", source = "fechaCancelacion")
    @Mapping(target = "motivoCancelacion", source = "motivoCancelacion")
    @Mapping(target = "cantidadNoches", expression = "java(reserva.calcularCantidadNoches())")
    @Mapping(target = "puedeCancelarse", expression = "java(reserva.puedeSerCancelada())")
    ReservaDTO toDTO(Reserva reserva);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Reserva toEntity(ReservaDTO dto);

    List<ReservaDTO> toDTOList(List<Reserva> reservas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "alojamiento", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    void updateFromDTO(@MappingTarget Reserva reserva, ReservaDTO dto);
}