package co.uniquindio.alojapp.negocio.DTO.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para buscar alojamientos con filtros")
public class BuscarAlojamientosRequest {

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Schema(description = "Ciudad donde buscar", example = "Cartagena")
    private String ciudad;

    @FutureOrPresent(message = "La fecha de check-in debe ser presente o futura")
    @Schema(description = "Fecha de entrada deseada", example = "2025-10-15")
    private LocalDate fechaCheckin;

    @Future(message = "La fecha de check-out debe ser futura")
    @Schema(description = "Fecha de salida deseada", example = "2025-10-20")
    private LocalDate fechaCheckout;

    @DecimalMin(value = "0.0", message = "El precio mínimo debe ser mayor o igual a 0")
    @Schema(description = "Precio mínimo por noche", example = "100000.00")
    private BigDecimal precioMin;

    @DecimalMin(value = "0.0", message = "El precio máximo debe ser mayor o igual a 0")
    @Schema(description = "Precio máximo por noche", example = "500000.00")
    private BigDecimal precioMax;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Schema(description = "Capacidad mínima requerida", example = "4")
    private Integer capacidadMinima;

    @Schema(description = "Lista de IDs de servicios requeridos", example = "[1, 3, 7]")
    private List<Long> serviciosIds;

    @Min(value = 0, message = "La página no puede ser negativa")
    @Schema(description = "Número de página (para paginación)", example = "0")
    private Integer pagina = 0;

    @Min(value = 1, message = "El tamaño debe ser al menos 1")
    @Max(value = 50, message = "El tamaño máximo es 50")
    @Schema(description = "Cantidad de resultados por página", example = "10")
    private Integer tamanoPagina = 10;

    @Schema(description = "Campo por el cual ordenar", example = "precio_noche")
    private String ordenarPor;

    @Schema(description = "Dirección del ordenamiento", example = "ASC")
    @Pattern(regexp = "ASC|DESC", message = "La dirección debe ser ASC o DESC")
    private String direccionOrden = "ASC";
}