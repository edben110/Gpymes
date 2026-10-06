package com.GPymes.layers.dto;

import java.util.UUID;

public record TotalGastosResponseDTO(
        UUID pymeId,
        String nombre,
        Double totalGastos,
        String resumen
) {
}
