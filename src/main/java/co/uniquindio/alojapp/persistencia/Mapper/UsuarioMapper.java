package co.uniquindio.alojapp.persistencia.Mapper;

import co.uniquindio.alojapp.negocio.DTO.UsuarioDTO;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoUsuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        builder = @Builder(disableBuilder = true) // evita usar Usuario.UsuarioBuilder de Lombok
)
public interface UsuarioMapper {

    /*// ======= Entity -> DTO =======
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "fechaRegistro", source = "fechaRegistro")
    @Mapping(target = "fechaUltimaConexion", source = "fechaUltimaConexion")
    // Enum -> String
    @Mapping(target = "estado", expression = "java(usuario.getEstado() != null ? usuario.getEstado().name() : null)")
    @Mapping(target = "fotoPerfilUrl", source = "fotoPerfilUrl")
    @Mapping(target = "esAnfitrion", expression = "java(usuario.getPerfilAnfitrion() != null)")
    @Mapping(target = "esAdministrador", expression = "java(usuario.getPerfilAdministrador() != null)")
    UsuarioDTO toDTO(Usuario usuario);



    // ======= DTO -> Entity (para crear desde DTO básico) =======
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasenaHash", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "fechaUltimaConexion", ignore = true)
    // Colecciones y relaciones: se ignoran aquí (las maneja la lógica de negocio)
    @Mapping(target = "reservasRealizadas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "codigosRecuperacion", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "perfilAnfitrion", ignore = true)
    @Mapping(target = "perfilAdministrador", ignore = true)
    // Estado: lo pone @PrePersist o la capa de servicio
    @Mapping(target = "estado", ignore = true)
    Usuario toEntity(UsuarioDTO dto);*/

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    UsuarioDTO toDTO(Usuario usuario);

    @InheritInverseConfiguration
    Usuario toEntity(UsuarioDTO dto);

    // ======= Actualización parcial sobre la Entity existente =======
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contrasenaHash", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "fechaUltimaConexion", ignore = true)
    @Mapping(target = "reservasRealizadas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "codigosRecuperacion", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "perfilAnfitrion", ignore = true)
    @Mapping(target = "perfilAdministrador", ignore = true)
    @Mapping(target = "estado", ignore = true) // si luego quieres permitirlo, hacemos un mapper dedicado
    void updateFromDTO(@MappingTarget Usuario usuario, UsuarioDTO dto);
}
