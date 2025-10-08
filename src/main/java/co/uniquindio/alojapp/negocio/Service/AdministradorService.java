package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.AdministradorDTO;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAdministradorRequest;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAdministradorRequest;

import java.util.List;
import java.util.Optional;

public interface AdministradorService {

    AdministradorDTO asignar(CrearAdministradorRequest request);

    void revocarPorUsuario(Integer usuarioId);

    AdministradorDTO actualizar(Integer administradorId, ActualizarAdministradorRequest request);

    AdministradorDTO obtenerPorId(Integer administradorId);

    Optional<AdministradorDTO> obtenerPorUsuarioId(Integer usuarioId);

    List<AdministradorDTO> listar();

    List<AdministradorDTO> listarPorNivel(String nivelAcceso);

    boolean esAdministrador(Integer usuarioId);

    long contar();

    long contarPorNivel(String nivelAcceso);
}
