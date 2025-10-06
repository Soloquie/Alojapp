package co.uniquindio.alojapp.negocio.DTO.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response estándar para operaciones")
public class MensajeResponse {

    @Schema(description = "Indica si la operación fue exitosa", example = "true")
    private Boolean exito;

    @Schema(description = "Mensaje descriptivo", example = "Operación completada exitosamente")
    private String mensaje;

    @Schema(description = "Código de estado HTTP", example = "200")
    private Integer codigoHttp;

    @Schema(description = "Timestamp de la respuesta")
    private LocalDateTime timestamp;

    @Schema(description = "Datos adicionales de la respuesta")
    private Object datos;

    public static MensajeResponse exitoso(String mensaje) {
        return MensajeResponse.builder()
                .exito(true)
                .mensaje(mensaje)
                .codigoHttp(200)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static MensajeResponse exitoso(String mensaje, Object datos) {
        return MensajeResponse.builder()
                .exito(true)
                .mensaje(mensaje)
                .codigoHttp(200)
                .timestamp(LocalDateTime.now())
                .datos(datos)
                .build();
    }

    public static MensajeResponse error(String mensaje, Integer codigo) {
        return MensajeResponse.builder()
                .exito(false)
                .mensaje(mensaje)
                .codigoHttp(codigo)
                .timestamp(LocalDateTime.now())
                .build();
    }
}