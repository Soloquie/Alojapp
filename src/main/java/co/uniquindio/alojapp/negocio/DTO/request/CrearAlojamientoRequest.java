package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para crear un nuevo alojamiento")
public class CrearAlojamientoRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 10, max = 200, message = "El título debe tener entre 10 y 200 caracteres")
    @Schema(description = "Título del alojamiento", example = "Casa de playa en Cartagena")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 50, max = 5000, message = "La descripción debe tener entre 50 y 5000 caracteres")
    @Schema(description = "Descripción detallada", example = "Hermosa casa frente al mar...")
    private String descripcion;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Schema(description = "Ciudad", example = "Cartagena")
    private String ciudad;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Schema(description = "Dirección completa", example = "Calle 10 #5-20, Bocagrande")
    private String direccion;

    @NotNull(message = "La latitud es obligatoria")
    @DecimalMin(value = "-90.0", message = "Latitud inválida")
    @DecimalMax(value = "90.0", message = "Latitud inválida")
    @Schema(description = "Latitud", example = "10.3910485")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria")
    @DecimalMin(value = "-180.0", message = "Longitud inválida")
    @DecimalMax(value = "180.0", message = "Longitud inválida")
    @Schema(description = "Longitud", example = "-75.4794257")
    private BigDecimal longitud;

    @NotNull(message = "El precio por noche es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio por noche", example = "350000.00")
    private BigDecimal precioNoche;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(description = "Capacidad máxima de huéspedes", example = "8")
    private Integer capacidadMaxima;

    @Schema(description = "URL de la imagen principal", example = "https://ejemplo.com/img1.jpg")
    private String imagenPrincipalUrl;

    @Size(min = 1, max = 10, message = "Debe haber entre 1 y 10 imágenes")
    @Schema(description = "Lista de URLs de imágenes adicionales")
    private List<String> imagenesUrls;

    @Schema(description = "Lista de IDs de servicios ofrecidos", example = "[1, 3, 5, 7]")
    private List<Integer> serviciosIds;
}