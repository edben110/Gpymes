package com.GPymes.layers.controller;

import com.GPymes.layers.dto.NominaRequestDTO;
import com.GPymes.layers.dto.NominaResponseDTO;
import com.GPymes.layers.service.ServicioNomina;
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
@RequestMapping("/api/nominas")
public class ControladorNomina {

    private final ServicioNomina servicioNomina;

    public ControladorNomina(ServicioNomina servicioNomina) {
        this.servicioNomina = servicioNomina;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NominaResponseDTO crear(@Valid @RequestBody NominaRequestDTO request) {
        return servicioNomina.crear(request);
    }

    @GetMapping
    public List<NominaResponseDTO> listarTodas() {
        return servicioNomina.listarTodas();
    }

    @GetMapping("/{id}")
    public NominaResponseDTO obtenerPorId(@PathVariable UUID id) {
        return servicioNomina.obtenerPorId(id);
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<NominaResponseDTO> listarPorEmpleado(@PathVariable UUID empleadoId) {
        return servicioNomina.listarPorEmpleado(empleadoId);
    }
}
