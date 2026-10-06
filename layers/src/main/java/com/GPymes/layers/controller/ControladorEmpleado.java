package com.GPymes.layers.controller;

import com.GPymes.layers.dto.EmpleadoRequestDTO;
import com.GPymes.layers.dto.EmpleadoResponseDTO;
import com.GPymes.layers.service.ServicioEmpleado;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empleados")
public class ControladorEmpleado {

    private final ServicioEmpleado servicioEmpleado;

    public ControladorEmpleado(ServicioEmpleado servicioEmpleado) {
        this.servicioEmpleado = servicioEmpleado;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoResponseDTO crear(@Valid @RequestBody EmpleadoRequestDTO request) {
        return servicioEmpleado.crear(request);
    }

    @GetMapping
    public List<EmpleadoResponseDTO> listarTodos() {
        return servicioEmpleado.listarTodos();
    }

    @GetMapping("/{id}")
    public EmpleadoResponseDTO obtenerPorId(@PathVariable UUID id) {
        return servicioEmpleado.obtenerPorId(id);
    }

    @GetMapping("/pyme/{pymeId}")
    public List<EmpleadoResponseDTO> listarPorPyme(@PathVariable UUID pymeId) {
        return servicioEmpleado.listarPorPyme(pymeId);
    }

    @PutMapping("/{id}/despedir")
    public EmpleadoResponseDTO despedir(@PathVariable UUID id) {
        return servicioEmpleado.despedir(id);
    }

    @PutMapping("/{id}/permiso")
    public EmpleadoResponseDTO ponerEnPermiso(@PathVariable UUID id) {
        return servicioEmpleado.ponerEnPermiso(id);
    }

    @PutMapping("/{id}/incapacitar")
    public EmpleadoResponseDTO incapacitar(@PathVariable UUID id) {
        return servicioEmpleado.incapacitar(id);
    }

    @PutMapping("/{id}/reactivar")
    public EmpleadoResponseDTO reactivar(@PathVariable UUID id) {
        return servicioEmpleado.reactivar(id);
    }
}
