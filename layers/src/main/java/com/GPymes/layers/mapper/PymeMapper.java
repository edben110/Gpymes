package com.GPymes.layers.mapper;

import com.GPymes.layers.domain.Pyme;
import com.GPymes.layers.dto.EmpleadoResponseDTO;
import com.GPymes.layers.dto.PymeRequestDTO;
import com.GPymes.layers.dto.PymeResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PymeMapper {

    private final EmpleadoMapper empleadoMapper;

    public PymeMapper(EmpleadoMapper empleadoMapper) {
        this.empleadoMapper = empleadoMapper;
    }

    public Pyme toEntity(PymeRequestDTO dto) {
        return new Pyme(dto.nombre());
    }

    public PymeResponseDTO toResponse(Pyme pyme) {
        List<EmpleadoResponseDTO> empleados = pyme.getEmpleados() == null
                ? List.of()
                : pyme.getEmpleados().stream().map(empleadoMapper::toResponse).toList();
        return new PymeResponseDTO(pyme.getId(), pyme.getNombre(), pyme.getGanancias(), empleados);
    }

    public List<PymeResponseDTO> toResponseList(List<Pyme> pymes) {
        return pymes.stream().map(this::toResponse).toList();
    }
}
