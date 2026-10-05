package com.GPymes.layers.dto;

import jakarta.validation.constraints.NotBlank;

public record PymeRequestDTO(
        @NotBlank(message = "El nombre es obligatorio") String nombre
) {
}
