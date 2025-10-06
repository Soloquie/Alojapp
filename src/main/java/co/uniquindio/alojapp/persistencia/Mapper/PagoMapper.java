package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.PagoDTO;
import co.uniquindio.alojapp.persistencia.Entity.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.List;

/**
 * Mapper encargado de la conversión entre la entidad Pago y su DTO correspondiente.
 * Maneja los mapeos usados en la capa de servicio y controladores.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PagoMapper {

    // ===============================
    // ENTITY → DTO
    // ===============================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fechaPago", source = "fechaPago")
    @Mapping(target = "monto", source = "monto")
    @Mapping(target = "metodoPago", source = "metodoPago")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "reservaId", source = "reserva.id")
    PagoDTO pagoToDTO(Pago pago);

    // ===============================
    // DTO → ENTITY (para crear)
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    Pago dtoToPago(PagoDTO dto);

    // ===============================
    // LISTA ENTITY → LISTA DTO
    // ===============================
    List<PagoDTO> pagosToDTO(List<Pago> pagos);

    // ===============================
    // ACTUALIZAR ENTITY EXISTENTE DESDE DTO
    // ===============================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    void updatePagoFromDTO(@MappingTarget Pago pago, PagoDTO dto);
}
