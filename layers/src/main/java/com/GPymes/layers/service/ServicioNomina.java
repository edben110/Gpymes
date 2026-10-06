package com.GPymes.layers.service;

import com.GPymes.layers.domain.Empleado;
import com.GPymes.layers.domain.Nomina;
import com.GPymes.layers.dto.NominaRequestDTO;
import com.GPymes.layers.dto.NominaResponseDTO;
import com.GPymes.layers.exception.RecursoNoEncontradoException;
import com.GPymes.layers.mapper.NominaMapper;
import com.GPymes.layers.repository.RepositorioEmpleado;
import com.GPymes.layers.repository.RepositorioNomina;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicioNomina {

    private final RepositorioNomina repositorioNomina;
    private final RepositorioEmpleado repositorioEmpleado;
    private final NominaMapper nominaMapper;

    public ServicioNomina(RepositorioNomina repositorioNomina,
            RepositorioEmpleado repositorioEmpleado,
            NominaMapper nominaMapper) {
        this.repositorioNomina = repositorioNomina;
        this.repositorioEmpleado = repositorioEmpleado;
        this.nominaMapper = nominaMapper;
    }

    @Transactional
    public NominaResponseDTO crear(NominaRequestDTO request) {
        Empleado empleado = repositorioEmpleado.findById(request.empleadoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con ID: " + request.empleadoId()));

        // Invocacion a las invariantes del modelo de dominio rico:
        // el constructor valida el monto, fija la categoria NOMINA, enlaza el
        // empleado y hereda la pyme del empleado (Pyme.gastos queda poblado).
        Nomina nueva = new Nomina(request.montoTotal(), request.fechaPago(), empleado, request.deducciones());
        Nomina guardada = repositorioNomina.save(nueva);
        return nominaMapper.toResponse(guardada);
    }

    /**
     * Nomina.getEmpleado() es LAZY: el mapeo ocurre dentro de la transaccion
     * read-only para no saltar la LazyInitializationException.
     */
    @Transactional(readOnly = true)
    public List<NominaResponseDTO> listarTodas() {
        return nominaMapper.toResponseList(repositorioNomina.findAll());
    }

    @Transactional(readOnly = true)
    public NominaResponseDTO obtenerPorId(UUID id) {
        Nomina nomina = repositorioNomina.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Nomina no encontrada con ID: " + id));
        return nominaMapper.toResponse(nomina);
    }

    @Transactional(readOnly = true)
    public List<NominaResponseDTO> listarPorEmpleado(UUID empleadoId) {
        return nominaMapper.toResponseList(repositorioNomina.findByEmpleadoId(empleadoId));
    }
}
