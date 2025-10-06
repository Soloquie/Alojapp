package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UsuarioMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "fechaRegistro", source = "fechaRegistro")
    @Mapping(target = "fechaUltimaConexion", source = "fechaUltimaConexion")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fotoPerfilUrl", source = "fotoPerfilUrl")
    @Mapping(target = "esAnfitrion", expression = "java(usuario.getPerfilAnfitrion() != null)")
    @Mapping(target = "esAdministrador", expression = "java(usuario.getPerfilAdministrador() != null)")
    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasenaHash", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "fechaUltimaConexion", ignore = true)
    @Mapping(target = "reservasRealizadas", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "codigosRecuperacion", ignore = true)
    @Mapping(target = "perfilAnfitrion", ignore = true)
    @Mapping(target = "perfilAdministrador", ignore = true)
    Usuario toEntity(UsuarioDTO dto);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasenaHash", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "fechaUltimaConexion", ignore = true)
    @Mapping(target = "reservasRealizadas", ignore = true)
    @Mapping(target = "alojamientos", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "codigosRecuperacion", ignore = true)
    @Mapping(target = "perfilAnfitrion", ignore = true)
    @Mapping(target = "perfilAdministrador", ignore = true)
    void updateFromDTO(@MappingTarget Usuario usuario, UsuarioDTO dto);
}