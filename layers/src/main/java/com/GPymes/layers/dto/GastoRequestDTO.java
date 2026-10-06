package com.GPymes.layers.dto;

import com.GPymes.layers.domain.CategoriasGasto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record GastoRequestDTO(
        @NotNull(message = "La pyme es obligatoria") UUID pymeId,
        @NotNull(message = "El monto total es obligatorio") @Positive(message = "El monto total debe ser positivo") Double montoTotal,
        LocalDateTime fechaPago,
        @NotNull(message = "La categoria es obligatoria") CategoriasGasto categoria
) {
}
