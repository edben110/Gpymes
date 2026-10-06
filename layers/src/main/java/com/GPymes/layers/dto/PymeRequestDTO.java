package com.GPymes.layers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record PymeRequestDTO(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @PositiveOrZero(message = "Las ganancias no pueden ser negativas") Double ganancias
) {
}
