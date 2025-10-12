package co.uniquindio.alojapp.negocio.Service;

import co.uniquindio.alojapp.negocio.DTO.AlojamientoDTO;
import co.uniquindio.alojapp.negocio.DTO.request.ActualizarAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.request.BuscarAlojamientosRequest;
import co.uniquindio.alojapp.negocio.DTO.request.CrearAlojamientoRequest;
import co.uniquindio.alojapp.negocio.DTO.response.PaginacionResponse;

import java.time.LocalDate;
import java.util.List;

public interface AlojamientoService {

    AlojamientoDTO crear(Integer usuarioIdAnfitrion, CrearAlojamientoRequest request);

    AlojamientoDTO actualizar(Integer usuarioIdAnfitrion, Integer alojamientoId, ActualizarAlojamientoRequest request);

    AlojamientoDTO activar(Integer usuarioIdAnfitrion, Integer alojamientoId);

    AlojamientoDTO desactivar(Integer usuarioIdAnfitrion, Integer alojamientoId);

    void eliminarFisico(Integer usuarioIdAnfitrion, Integer alojamientoId);

    AlojamientoDTO obtenerPorId(Integer alojamientoId);

    List<AlojamientoDTO> listarPorAnfitrion(Integer usuarioIdAnfitrion);

    PaginacionResponse<AlojamientoDTO> listarPorAnfitrion(Integer anfitrionId, int pagina, int tamano);

    PaginacionResponse<AlojamientoDTO> buscar(BuscarAlojamientosRequest filtro);

    AlojamientoDTO agregarImagenes(Integer usuarioIdAnfitrion, Integer alojamientoId, List<String> urls);

    AlojamientoDTO eliminarImagen(Integer usuarioIdAnfitrion, Integer alojamientoId, Integer imagenId);

    boolean eliminar(Integer usuarioIdAnfitrion, Integer alojamientoId);

    Boolean estaDisponible(Integer id, LocalDate checkin, LocalDate checkout);

    PaginacionResponse<AlojamientoDTO> listarActivos(int pagina, int tamano);

    AlojamientoDTO actualizarDeUsuario(Integer usuarioId, Integer alojamientoId, ActualizarAlojamientoRequest request);

    boolean eliminarDeUsuario(Integer userId, Integer alojamientoId);
}
