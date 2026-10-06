package com.GPymes.layers.controller;

import com.GPymes.layers.dto.PymeRequestDTO;
import com.GPymes.layers.dto.PymeResponseDTO;
import com.GPymes.layers.dto.TotalGastosResponseDTO;
import com.GPymes.layers.service.ServicioPyme;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pymes")
public class ControladorPyme {

    private final ServicioPyme servicioPyme;

    public ControladorPyme(ServicioPyme servicioPyme) {
        this.servicioPyme = servicioPyme;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PymeResponseDTO crear(@Valid @RequestBody PymeRequestDTO request) {
        return servicioPyme.crear(request);
    }

    @GetMapping
    public List<PymeResponseDTO> listarTodos() {
        return servicioPyme.listarTodos();
    }

    @GetMapping("/{id}")
    public PymeResponseDTO obtenerPorId(@PathVariable UUID id) {
        return servicioPyme.obtenerPorId(id);
    }

    @GetMapping("/buscar")
    public PymeResponseDTO obtenerPorNombre(@RequestParam String nombre) {
        return servicioPyme.obtenerPorNombre(nombre);
    }

    @PutMapping("/{id}")
    public PymeResponseDTO actualizar(@PathVariable UUID id, @Valid @RequestBody PymeRequestDTO request) {
        return servicioPyme.actualizar(id, request);
    }

    @GetMapping("/{id}/gastos/total")
    public TotalGastosResponseDTO obtenerTotalGastos(@PathVariable UUID id) {
        return servicioPyme.obtenerTotalGastos(id);
    }
}
