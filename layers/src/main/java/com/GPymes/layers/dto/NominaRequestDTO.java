package com.GPymes.layers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record NominaRequestDTO(
        @NotNull(message = "El empleado es obligatorio") UUID empleadoId,
        @NotNull(message = "El monto total es obligatorio") @Positive(message = "El monto total debe ser positivo") Double montoTotal,
        LocalDateTime fechaPago,
        Double deducciones
) {
}
