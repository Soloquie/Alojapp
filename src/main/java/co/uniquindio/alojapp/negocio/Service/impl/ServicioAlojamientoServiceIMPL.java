package co.uniquindio.alojapp.negocio.Service.impl;

import co.uniquindio.alojapp.negocio.DTO.ServicioAlojamientoDTO;
import co.uniquindio.alojapp.negocio.Service.ServicioAlojamientoService;
import co.uniquindio.alojapp.negocio.excepciones.ReglaNegocioException;
import co.uniquindio.alojapp.negocio.excepciones.RecursoNoEncontradoException;
import co.uniquindio.alojapp.persistencia.DAO.ServicioAlojamientoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioAlojamientoServiceIMPL implements ServicioAlojamientoService {

    private final ServicioAlojamientoDAO servicioDAO;

    @Override
    @Transactional
    public ServicioAlojamientoDTO crear(String nombre, String descripcion, String iconoUrl) {
        try {
            log.info("Creando servicio de alojamiento: nombre='{}'", nombre);
            return servicioDAO.save(nombre, descripcion, iconoUrl);
        } catch (RuntimeException e) {
            // El DAO lanza RuntimeException cuando hay duplicado por nombre
            throw new ReglaNegocioException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ServicioAlojamientoDTO obtenerPorId(Integer id) {
        return servicioDAO.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioAlojamientoDTO> listar() {
        return servicioDAO.findAll();
    }

    @Override
    @Transactional
    public ServicioAlojamientoDTO actualizar(Integer id, String nombre, String descripcion, String iconoUrl) {
        return servicioDAO.actualizar(id, nombre, descripcion, iconoUrl)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado para actualizar"));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        boolean eliminado = servicioDAO.delete(id);
        if (!eliminado) {
            throw new RecursoNoEncontradoException("Servicio no encontrado para eliminar");
        }
        log.info("Servicio de alojamiento eliminado: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ServicioAlojamientoDTO buscarPorNombre(String nombre) {
        return servicioDAO.findByNombre(nombre)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con nombre: " + nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioUsoDTO> serviciosMasUtilizados() {
        // El DAO devuelve List<Object[]> con [ServicioAlojamiento, Long total]
        List<Object[]> filas = servicioDAO.findServiciosMasUtilizados();
        List<ServicioUsoDTO> out = new ArrayList<>();
        for (Object[] f : filas) {
            // f[0] ya llega mapeado como DTO desde el DAO? -> No: el DAO retorna Object[] de la query;
            // usamos count y el servicio lo reconstruimos consultando por id si lo necesitas como DTO.
            // Pero tu DAO expone mapper a DTO en findAll/ findById. Aquí convertimos de forma segura:
            // f[0] es la entidad ServicioAlojamiento; para evitar dependencia aquí, pedimos al DAO el DTO por id.
            // Simplificamos: el DAO podría darnos directamente DTO en el futuro. Por ahora:
            co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento entity =
                    (co.uniquindio.alojapp.persistencia.Entity.ServicioAlojamiento) f[0];
            Long total = (Long) f[1];
            ServicioAlojamientoDTO dto = new ServicioAlojamientoDTO(entity.getId(), entity.getNombre(), entity.getDescripcion());
            out.add(new ServicioUsoDTO(dto, total));
        }
        return out;
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarAlojamientosPorServicio(Integer servicioId) {
        return servicioDAO.countAlojamientosByServicio(servicioId);
    }
}
