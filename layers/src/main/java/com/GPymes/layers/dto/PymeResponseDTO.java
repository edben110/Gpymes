package com.GPymes.layers.dto;

import java.util.List;
import java.util.UUID;

public record PymeResponseDTO(
        UUID id,
        String nombre,
        Double ganancias,
        List<EmpleadoResponseDTO> empleados
) {
}
