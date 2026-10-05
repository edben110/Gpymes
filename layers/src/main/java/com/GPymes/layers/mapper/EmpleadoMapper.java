package com.GPymes.layers.mapper;

import com.GPymes.layers.domain.Empleado;
import com.GPymes.layers.dto.EmpleadoRequestDTO;
import com.GPymes.layers.dto.EmpleadoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public Empleado toEntity(EmpleadoRequestDTO dto) {
        return new Empleado(dto.nombre(), dto.documento(), dto.salarioBase());
    }

    public EmpleadoResponseDTO toResponse(Empleado empleado) {
        return new EmpleadoResponseDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getDocumento(),
                empleado.getSalario(),
                empleado.getHorasExtra(),
                empleado.getEstado() == null ? null : empleado.getEstado().getClass().getSimpleName()
        );
    }
}
