package com.GPymes.layers.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String mensaje,
        LocalDateTime fecha
) {
}
