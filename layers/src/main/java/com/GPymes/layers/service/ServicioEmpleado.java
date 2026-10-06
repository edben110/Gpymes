package com.GPymes.layers.service;

import com.GPymes.layers.domain.Empleado;
import com.GPymes.layers.domain.Pyme;
import com.GPymes.layers.dto.EmpleadoRequestDTO;
import com.GPymes.layers.dto.EmpleadoResponseDTO;
import com.GPymes.layers.exception.RecursoNoEncontradoException;
import com.GPymes.layers.mapper.EmpleadoMapper;
import com.GPymes.layers.repository.RepositorioEmpleado;
import com.GPymes.layers.repository.RepositorioPyme;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicioEmpleado {

    private final RepositorioEmpleado repositorioEmpleado;
    private final RepositorioPyme repositorioPyme;
    private final EmpleadoMapper empleadoMapper;

    public ServicioEmpleado(RepositorioEmpleado repositorioEmpleado,
            RepositorioPyme repositorioPyme,
            EmpleadoMapper empleadoMapper) {
        this.repositorioEmpleado = repositorioEmpleado;
        this.repositorioPyme = repositorioPyme;
        this.empleadoMapper = empleadoMapper;
    }

    @Transactional
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO request) {
        Pyme pyme = repositorioPyme.findById(request.pymeId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pyme no encontrada con ID: " + request.pymeId()));

        Empleado empleado = empleadoMapper.toEntity(request);
        empleado.setPyme(pyme);
        Empleado guardado = repositorioEmpleado.save(empleado);
        return empleadoMapper.toResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponseDTO> listarTodos() {
        return empleadoMapper.toResponseList(repositorioEmpleado.findAll());
    }

    @Transactional(readOnly = true)
    public List<EmpleadoResponseDTO> listarPorPyme(UUID pymeId) {
        return empleadoMapper.toResponseList(repositorioEmpleado.findByPymeId(pymeId));
    }

    @Transactional(readOnly = true)
    public EmpleadoResponseDTO obtenerPorId(UUID id) {
        Empleado empleado = buscarPorId(id);
        return empleadoMapper.toResponse(empleado);
    }

    @Transactional
    public EmpleadoResponseDTO despedir(UUID id) {
        Empleado empleado = buscarPorId(id);
        empleado.getEstado().despedido(empleado);
        return empleadoMapper.toResponse(repositorioEmpleado.save(empleado));
    }

    @Transactional
    public EmpleadoResponseDTO ponerEnPermiso(UUID id) {
        Empleado empleado = buscarPorId(id);
        empleado.getEstado().permiso(empleado);
        return empleadoMapper.toResponse(repositorioEmpleado.save(empleado));
    }

    @Transactional
    public EmpleadoResponseDTO incapacitar(UUID id) {
        Empleado empleado = buscarPorId(id);
        empleado.getEstado().incapacitado(empleado);
        return empleadoMapper.toResponse(repositorioEmpleado.save(empleado));
    }

    @Transactional
    public EmpleadoResponseDTO reactivar(UUID id) {
        Empleado empleado = buscarPorId(id);
        empleado.getEstado().laborando(empleado);
        return empleadoMapper.toResponse(repositorioEmpleado.save(empleado));
    }

    private Empleado buscarPorId(UUID id) {
        return repositorioEmpleado.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con ID: " + id));
    }
}
