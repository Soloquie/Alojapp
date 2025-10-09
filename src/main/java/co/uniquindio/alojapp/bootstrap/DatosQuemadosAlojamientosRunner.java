package co.uniquindio.alojapp.bootstrap;

import co.uniquindio.alojapp.persistencia.Entity.Alojamiento;
import co.uniquindio.alojapp.persistencia.Entity.Anfitrion;
import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.AlojamientoRepository;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import co.uniquindio.alojapp.persistencia.DAO.UsuarioDAO;
import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatosQuemadosAlojamientosRunner implements CommandLineRunner {

    private final AlojamientoRepository alojamientoRepository;
    private final AnfitrionRepository anfitrionRepository;
    private final UsuarioDAO usuarioDAO;

    @Override
    public void run(String... args) {
        log.info("===== INICIO: DATOS QUEMADOS ALOJAMIENTOS =====");

        try {
            // Verificar si ya existen alojamientos ACTIVOS
            List<Alojamiento> alojamientosExistentes = alojamientoRepository.findByEstado(EstadoAlojamiento.ACTIVO);

            if (alojamientosExistentes.isEmpty()) {
                log.info("No hay alojamientos activos, creando datos de prueba...");

                // Buscar el usuario
                Optional<Usuario> usuarioOpt = usuarioDAO.findEntityByEmail("ana.gomez@ejemplo.com");

                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();

                    // Verificar si ya existe un anfitrión para este usuario
                    Optional<Anfitrion> anfitrionExistente = anfitrionRepository.findByUsuarioId(usuario.getId());
                    Anfitrion anfitrion;

                    if (anfitrionExistente.isPresent()) {
                        anfitrion = anfitrionExistente.get();
                        log.info("Usando anfitrión existente -> ID: {}", anfitrion.getId());
                    } else {
                        // Crear nuevo anfitrión
                        anfitrion = Anfitrion.builder()
                                .usuario(usuario)
                                .descripcionPersonal("Anfitrión amable y responsable con experiencia en hospitalidad")
                                .documentosLegalesUrl("https://ejemplo.com/documentos/ana_gomez.pdf")
                                .fechaRegistroAnfitrion(LocalDateTime.now())
                                .verificado(true)
                                .build();

                        anfitrion = anfitrionRepository.save(anfitrion);
                        log.info("Anfitrión creado -> ID: {}", anfitrion.getId());
                    }

                    // Crear alojamiento 1
                    Alojamiento alojamiento1 = Alojamiento.builder()
                            .titulo("Hermoso apartamento en el centro")
                            .descripcion("Apartamento amplio y moderno en el corazón de la ciudad, cerca de todos los servicios y atracciones turísticas. Ideal para parejas o familias pequeñas.")
                            .direccion("Carrera 15 #45-20")
                            .ciudad("Armenia")
                            .precioNoche(BigDecimal.valueOf(120000))
                            .capacidadMaxima(4)
                            .estado(EstadoAlojamiento.ACTIVO)
                            .anfitrion(anfitrion)
                            .latitud(BigDecimal.valueOf(4.5310))
                            .longitud(BigDecimal.valueOf(-75.6810))
                            .fechaCreacion(LocalDateTime.now())
                            .fechaActualizacion(LocalDateTime.now())
                            .build();

                    alojamientoRepository.save(alojamiento1);
                    log.info("Alojamiento 1 creado -> ID: {}", alojamiento1.getId());

                    // Crear alojamiento 2
                    Alojamiento alojamiento2 = Alojamiento.builder()
                            .titulo("Casa campestre con piscina")
                            .descripcion("Casa campestre ideal para descansar, con piscina privada y jardín amplio. Perfecta para familias grandes o grupos de amigos. Incluye parqueadero privado y zona de BBQ.")
                            .direccion("Km 8 vía a Montenegro")
                            .ciudad("Armenia")
                            .precioNoche(BigDecimal.valueOf(250000))
                            .capacidadMaxima(8)
                            .estado(EstadoAlojamiento.ACTIVO)
                            .anfitrion(anfitrion)
                            .latitud(BigDecimal.valueOf(4.5330))
                            .longitud(BigDecimal.valueOf(-75.6830))
                            .fechaCreacion(LocalDateTime.now())
                            .fechaActualizacion(LocalDateTime.now())
                            .build();

                    alojamientoRepository.save(alojamiento2);
                    log.info("Alojamiento 2 creado -> ID: {}", alojamiento2.getId());

                    log.info("Total alojamientos creados: 2");
                } else {
                    log.warn("No se encontró el usuario ana.gomez@ejemplo.com, no se pueden crear alojamientos");
                }
            } else {
                log.info("Ya existen {} alojamientos activos en la base de datos", alojamientosExistentes.size());
            }

        } catch (Exception e) {
            log.error("Error en la carga de datos de alojamientos", e);
        }

        log.info("===== FIN: DATOS QUEMADOS ALOJAMIENTOS =====");
    }
}