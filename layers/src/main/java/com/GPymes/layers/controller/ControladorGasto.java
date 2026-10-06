package com.GPymes.layers.controller;

import com.GPymes.layers.domain.CategoriasGasto;
import com.GPymes.layers.dto.GastoRequestDTO;
import com.GPymes.layers.dto.GastoResponseDTO;
import com.GPymes.layers.service.ServicioGasto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gastos")
public class ControladorGasto {

    private final ServicioGasto servicioGasto;

    public ControladorGasto(ServicioGasto servicioGasto) {
        this.servicioGasto = servicioGasto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GastoResponseDTO crear(@Valid @RequestBody GastoRequestDTO request) {
        return servicioGasto.crear(request);
    }

    @GetMapping
    public List<GastoResponseDTO> listarTodos() {
        return servicioGasto.listarTodos();
    }

    @GetMapping("/{id}")
    public GastoResponseDTO obtenerPorId(@PathVariable UUID id) {
        return servicioGasto.obtenerPorId(id);
    }

    @GetMapping("/pyme/{pymeId}")
    public List<GastoResponseDTO> listarPorPyme(@PathVariable UUID pymeId) {
        return servicioGasto.listarPorPyme(pymeId);
    }

    @GetMapping("/categoria/{categoria}")
    public List<GastoResponseDTO> listarPorCategoria(@PathVariable CategoriasGasto categoria) {
        return servicioGasto.listarPorCategoria(categoria);
    }
}
