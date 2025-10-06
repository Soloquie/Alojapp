package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.CodigoRecuperacionDTO;
import co.uniquindio.alojapp.persistencia.Entity.CodigoRecuperacion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CodigoRecuperacionMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "fechaExpiracion", source = "fechaExpiracion")
    @Mapping(target = "usado", source = "usado")
    @Mapping(target = "esValido", expression = "java(entity.esValido())")
    CodigoRecuperacionDTO toDTO(CodigoRecuperacion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "fechaExpiracion", ignore = true)
    @Mapping(target = "usado", ignore = true)
    CodigoRecuperacion toEntity(CodigoRecuperacionDTO dto);

    List<CodigoRecuperacionDTO> toDTOList(List<CodigoRecuperacion> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateFromDTO(@MappingTarget CodigoRecuperacion entity, CodigoRecuperacionDTO dto);
}