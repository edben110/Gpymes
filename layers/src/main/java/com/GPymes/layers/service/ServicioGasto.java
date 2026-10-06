package com.GPymes.layers.service;

import com.GPymes.layers.domain.CategoriasGasto;
import com.GPymes.layers.domain.Gasto;
import com.GPymes.layers.domain.Pyme;
import com.GPymes.layers.dto.GastoRequestDTO;
import com.GPymes.layers.dto.GastoResponseDTO;
import com.GPymes.layers.exception.RecursoNoEncontradoException;
import com.GPymes.layers.mapper.GastoMapper;
import com.GPymes.layers.repository.RepositorioGasto;
import com.GPymes.layers.repository.RepositorioPyme;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicioGasto {

    private final RepositorioGasto repositorioGasto;
    private final RepositorioPyme repositorioPyme;
    private final GastoMapper gastoMapper;

    public ServicioGasto(RepositorioGasto repositorioGasto,
            RepositorioPyme repositorioPyme,
            GastoMapper gastoMapper) {
        this.repositorioGasto = repositorioGasto;
        this.repositorioPyme = repositorioPyme;
        this.gastoMapper = gastoMapper;
    }

    @Transactional
    public GastoResponseDTO crear(GastoRequestDTO request) {
        if (request.categoria() == CategoriasGasto.NOMINA) {
            throw new IllegalArgumentException(
                    "La categoria NOMINA solo se genera desde ServicioNomina, que ademas exige el empleado");
        }

        Pyme pyme = repositorioPyme.findById(request.pymeId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pyme no encontrada con ID: " + request.pymeId()));

        Gasto gasto = new Gasto(request.montoTotal(), request.fechaPago(), request.categoria(), pyme);
        Gasto guardado = repositorioGasto.save(gasto);
        return gastoMapper.toResponse(guardado);
    }

    /**
     * La tabla es unica (SINGLE_TABLE): el listado trae gastos comunes y nominas.
     * getPyme() es LAZY, por eso el mapeo ocurre dentro de la transaccion read-only.
     */
    @Transactional(readOnly = true)
    public List<GastoResponseDTO> listarTodos() {
        return gastoMapper.toResponseList(repositorioGasto.findAll());
    }

    @Transactional(readOnly = true)
    public GastoResponseDTO obtenerPorId(UUID id) {
        Gasto gasto = repositorioGasto.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Gasto no encontrado con ID: " + id));
        return gastoMapper.toResponse(gasto);
    }

    @Transactional(readOnly = true)
    public List<GastoResponseDTO> listarPorPyme(UUID pymeId) {
        return gastoMapper.toResponseList(repositorioGasto.findByPymeId(pymeId));
    }

    @Transactional(readOnly = true)
    public List<GastoResponseDTO> listarPorCategoria(CategoriasGasto categoria) {
        return gastoMapper.toResponseList(repositorioGasto.findByCategoria(categoria));
    }
}
