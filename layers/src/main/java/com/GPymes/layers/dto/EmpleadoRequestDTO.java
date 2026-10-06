package com.GPymes.layers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record EmpleadoRequestDTO(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotNull(message = "El documento es obligatorio") Long documento,
        @NotNull(message = "El salario es obligatorio") @Positive(message = "El salario debe ser positivo") Double salarioBase,
        @NotNull(message = "La pyme es obligatoria") UUID pymeId
) {
}
