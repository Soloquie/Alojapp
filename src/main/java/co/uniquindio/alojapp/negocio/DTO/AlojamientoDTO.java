package co.uniquindio.alojapp.negocio.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de alojamiento sincronizado con BD")
public class AlojamientoDTO {

    @Schema(description = "ID del alojamiento", example = "10")
    private Integer id;

    @Schema(description = "Título del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @Schema(description = "Descripción detallada")
    private String descripcion;

    @Schema(description = "Ciudad", example = "Cartagena")
    private String ciudad;

    @Schema(description = "Dirección completa", example = "Calle 10 #5-20")
    private String direccion;

    @Schema(description = "Latitud", example = "10.3910485")
    private BigDecimal latitud;

    @Schema(description = "Longitud", example = "-75.4794257")
    private BigDecimal longitud;

    @Schema(description = "Precio por noche", example = "350000.00")
    private BigDecimal precioNoche;

    @Schema(description = "Capacidad máxima", example = "8")
    private Integer capacidadMaxima;

    @Schema(description = "URL imagen principal")
    private String imagenPrincipalUrl;

    @Schema(description = "Estado del alojamiento", example = "ACTIVO")
    private String estado;

    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de actualización")
    private LocalDateTime fechaActualizacion;

    @Schema(description = "ID del anfitrión", example = "5")
    private Long anfitrionId;

    @Schema(description = "Nombre del anfitrión", example = "María López")
    private String anfitrionNombre;

    @Schema(description = "Lista de URLs de imágenes")
    private List<String> imagenes;

    @Schema(description = "Lista de servicios ofrecidos")
    private List<ServicioAlojamientoDTO> servicios;

    @Schema(description = "Calificación promedio", example = "4.7")
    private Double calificacionPromedio;

    @Schema(description = "Cantidad de comentarios", example = "23")
    private Integer cantidadComentarios;
}