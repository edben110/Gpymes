package com.GPymes.layers.mapper;

import com.GPymes.layers.domain.Empleado;
import com.GPymes.layers.domain.Nomina;
import com.GPymes.layers.dto.NominaResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NominaMapper {

    public NominaResponseDTO toResponse(Nomina nomina) {
        Empleado empleado = nomina.getEmpleado();
        return new NominaResponseDTO(
                nomina.getId(),
                empleado == null ? null : empleado.getId(),
                empleado == null ? null : empleado.getNombre(),
                nomina.getMontoTotal(),
                nomina.getDeducciones(),
                nomina.getFechaPago(),
                nomina.getCategoria() == null ? null : nomina.getCategoria().name());
    }

    public List<NominaResponseDTO> toResponseList(List<Nomina> nominas) {
        return nominas.stream().map(this::toResponse).toList();
    }
}
