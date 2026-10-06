package com.GPymes.layers.service;

import com.GPymes.layers.domain.Pyme;
import com.GPymes.layers.dto.PymeRequestDTO;
import com.GPymes.layers.dto.PymeResponseDTO;
import com.GPymes.layers.mapper.PymeMapper;
import com.GPymes.layers.repository.RepositorioPyme;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicioPyme {

    private final RepositorioPyme repositorioPyme;
    private final PymeMapper pymeMapper;

    public ServicioPyme(RepositorioPyme repositorioPyme, PymeMapper pymeMapper) {
        this.repositorioPyme = repositorioPyme;
        this.pymeMapper = pymeMapper;
    }

    @Transactional
    public PymeResponseDTO crear(PymeRequestDTO request) {
        Pyme pyme = pymeMapper.toEntity(request);
        Pyme guardada = repositorioPyme.save(pyme);
        return pymeMapper.toResponse(guardada);
    }

    /**
     * Pyme.getEmpleados() es LAZY: el mapeo ocurre dentro de la transaccion
     * read-only para no saltar la LazyInitializationException.
     */
    @Transactional(readOnly = true)
    public List<PymeResponseDTO> listarTodos() {
        return pymeMapper.toResponseList(repositorioPyme.findAll());
    }

    @Transactional(readOnly = true)
    public PymeResponseDTO obtenerPorId(UUID id) {
        Pyme pyme = repositorioPyme.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pyme no encontrada con ID: " + id));
        return pymeMapper.toResponse(pyme);
    }

    @Transactional(readOnly = true)
    public PymeResponseDTO obtenerPorNombre(String nombre) {
        Pyme pyme = repositorioPyme.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Pyme no encontrada con nombre: " + nombre));
        return pymeMapper.toResponse(pyme);
    }

    @Transactional
    public PymeResponseDTO actualizar(UUID id, PymeRequestDTO request) {
        if (request.nombre() == null || request.nombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no es valido ya que esta en blanco o es nulo");
        }
        Pyme pyme = repositorioPyme.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pyme no encontrada con ID: " + id));
        pyme.setNombre(request.nombre());
        return pymeMapper.toResponse(repositorioPyme.save(pyme));
    }
}
