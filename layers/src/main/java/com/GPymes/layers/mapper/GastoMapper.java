package com.GPymes.layers.mapper;

import com.GPymes.layers.domain.Empleado;
import com.GPymes.layers.domain.Gasto;
import com.GPymes.layers.domain.Nomina;
import com.GPymes.layers.dto.GastoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GastoMapper {

    /**
     * Proyeccion polimorfica: {@code findAll()} sobre la tabla unica de gastos
     * devuelve tambien las Nominas (SINGLE_TABLE), por lo que se despacha con
     * pattern matching para exponer el empleado solo cuando el gasto lo tiene.
     */
    public GastoResponseDTO toResponse(Gasto gasto) {
        Empleado empleado = gasto instanceof Nomina nomina ? nomina.getEmpleado() : null;
        return new GastoResponseDTO(
                gasto.getId(),
                gasto.getPyme() == null ? null : gasto.getPyme().getId(),
                gasto.getPyme() == null ? null : gasto.getPyme().getNombre(),
                gasto.getMontoTotal(),
                gasto.getFechaPago(),
                gasto.getCategoria() == null ? null : gasto.getCategoria().name(),
                empleado == null ? null : empleado.getId());
    }

    public List<GastoResponseDTO> toResponseList(List<Gasto> gastos) {
        return gastos.stream().map(this::toResponse).toList();
    }
}
