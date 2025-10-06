package co.uniquindio.alojapp.negocio.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response paginado para listados")
public class PaginacionResponse<T> {

    @Schema(description = "Lista de elementos de la página actual")
    private List<T> contenido;

    @Schema(description = "Número de página actual", example = "0")
    private Integer paginaActual;

    @Schema(description = "Tamaño de la página", example = "10")
    private Integer tamanoPagina;

    @Schema(description = "Total de elementos", example = "150")
    private Long totalElementos;

    @Schema(description = "Total de páginas", example = "15")
    private Integer totalPaginas;

    @Schema(description = "Indica si es la primera página", example = "true")
    private Boolean esPrimera;

    @Schema(description = "Indica si es la última página", example = "false")
    private Boolean esUltima;

    @Schema(description = "Indica si tiene página siguiente", example = "true")
    private Boolean tieneSiguiente;

    @Schema(description = "Indica si tiene página anterior", example = "false")
    private Boolean tieneAnterior;
}