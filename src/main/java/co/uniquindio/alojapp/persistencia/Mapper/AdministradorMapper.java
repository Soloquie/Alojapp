package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.persistencia.Entity.Administrador;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdministradorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "usuario.nombre")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "telefono", source = "usuario.telefono")
    @Mapping(target = "estado", source = "usuario.estado")          // enum -> String
    @Mapping(target = "permisosJson", source = "permisos")          // <- IMPRESCINDIBLE
    AdministradorDTO toDTO(Administrador admin);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "nivelAcceso", ignore = true)
    @Mapping(target = "permisos", ignore = true)
    @Mapping(target = "fechaAsignacion", ignore = true)
    Administrador toEntity(AdministradorDTO dto);

    List<AdministradorDTO> toDTOList(List<Administrador> admins);

    // Opcional: garantiza enum -> String si tu versión de MapStruct no lo hace sola
    default String map(EstadoUsuario estado) {
        return estado != null ? estado.name() : null;
    }
}
