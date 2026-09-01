package com.techlab.spring.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estructura estandar de respuesta de error")
public record ErrorResponseDTO(
        @Schema(description = "Fecha y hora del error")
        LocalDateTime timeStamp,
        @Schema(description = "Codigo HTTP asociado al error", example = "404")
        int status,
        @Schema(description = "Tipo corto del error", example = "Not Found")
        String error,
        @Schema(description = "Mensaje detallado del error")
        String message,
        @Schema(description = "Ruta solicitada que provoco el error")
        String path
) {
}
