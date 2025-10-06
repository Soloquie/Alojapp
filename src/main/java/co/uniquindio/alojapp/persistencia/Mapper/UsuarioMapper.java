package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UsuarioMapper {

    // =========================
    // USUARIO MAPPINGS
    // =========================

    // Usuario Entity -> UsuarioDTO
    @Mapping(target = "rol", source = "rol")
    @Mapping(target = "estado", source = "estado")
    UsuarioDTO usuarioToDTO(Usuario usuario);

    // UsuarioDTO -> Usuario Entity (para crear)
    @Mapping(target = "reservasRealizadas", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    Usuario dtoToUsuario(UsuarioDTO dto);

    // Lista de Usuario Entity -> Lista de UsuarioDTO
    List<UsuarioDTO> usuariosToDTO(List<Usuario> usuarios);

    // Actualizar Usuario existente con datos del DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservasRealizadas", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    void updateUsuarioFromDTO(@MappingTarget Usuario usuario, UsuarioDTO dto);
}
