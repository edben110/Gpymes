package com.GPymes.layers.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GastoResponseDTO(
        UUID id,
        UUID pymeId,
        String nombrePyme,
        Double montoTotal,
        LocalDateTime fechaPago,
        String categoria,
        UUID empleadoId
) {
}
