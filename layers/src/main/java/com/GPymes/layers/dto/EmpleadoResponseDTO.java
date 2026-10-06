package com.GPymes.layers.dto;

import java.util.UUID;

public record EmpleadoResponseDTO(
        UUID id,
        String nombre,
        Long documento,
        Double salarioBase,
        Integer horasExtra,
        String estado
) {
}
