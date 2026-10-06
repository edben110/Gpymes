package com.GPymes.layers.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NominaResponseDTO(
        UUID id,
        UUID empleadoId,
        String nombreEmpleado,
        Double montoTotal,
        Double deducciones,
        LocalDateTime fechaPago,
        String categoria
) {
}
