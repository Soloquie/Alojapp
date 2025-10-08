package co.uniquindio.alojapp.negocio.DTO.request;

import co.uniquindio.alojapp.persistencia.Entity.Enum.EstadoAlojamiento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para actualizar un alojamiento (parcial)")
public class ActualizarAlojamientoRequest {

    @Size(min = 10, max = 200, message = "El título debe tener entre 10 y 200 caracteres")
    @Schema(description = "Título del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @Size(min = 50, max = 5000, message = "La descripción debe tener entre 50 y 5000 caracteres")
    @Schema(description = "Descripción detallada", example = "Hermosa casa frente al mar...")
    private String descripcion;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Schema(description = "Ciudad", example = "Cartagena")
    private String ciudad;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Schema(description = "Dirección completa", example = "Calle 10 #5-20, Bocagrande")
    private String direccion;

    @DecimalMin(value = "-90.0", message = "Latitud inválida")
    @DecimalMax(value = "90.0",  message = "Latitud inválida")
    @Schema(description = "Latitud", example = "10.3910485")
    private BigDecimal latitud;

    @DecimalMin(value = "-180.0", message = "Longitud inválida")
    @DecimalMax(value = "180.0",  message = "Longitud inválida")
    @Schema(description = "Longitud", example = "-75.4794257")
    private BigDecimal longitud;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio por noche", example = "350000.00")
    private BigDecimal precioNoche;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(description = "Capacidad máxima de huéspedes", example = "8")
    private Integer capacidadMaxima;

    @Schema(description = "URL de la imagen principal", example = "https://ejemplo.com/img1.jpg")
    private String imagenPrincipalUrl;

    @Schema(description = "Nuevo estado del alojamiento", example = "ACTIVO")
    private EstadoAlojamiento estado;

    @Schema(description = "IDs de servicios a reemplazar", example = "[1,3,5]")
    private List<Integer> serviciosIds;
}
