package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para actualizar un alojamiento existente")
public class ActualizarAlojamientoRequest {

    @Size(min = 10, max = 200, message = "El título debe tener entre 10 y 200 caracteres")
    @Schema(description = "Título del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @Size(min = 50, max = 5000, message = "La descripción debe tener entre 50 y 5000 caracteres")
    @Schema(description = "Descripción detallada")
    private String descripcion;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Schema(description = "Ciudad", example = "Cartagena")
    private String ciudad;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Schema(description = "Dirección completa")
    private String direccion;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio por noche", example = "350000.00")
    private BigDecimal precioNoche;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(description = "Capacidad máxima de huéspedes", example = "8")
    private Integer capacidadMaxima;

    @Schema(description = "Estado del alojamiento", example = "ACTIVO")
    private String estado;

    @Schema(description = "Lista de IDs de servicios ofrecidos")
    private List<Long> serviciosIds;
}