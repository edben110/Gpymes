package com.GPymes.layers.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pymes")
public class Pyme {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, unique = true, length = 120)
    private String nombre;

    @Column(name = "ganancias")
    private Double ganancias;

    @OneToMany(mappedBy = "pyme", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Empleado> empleados = new ArrayList<>();

    @OneToMany(mappedBy = "pyme", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Gasto> gastos = new ArrayList<>();

    protected Pyme() {
    }

    public Pyme(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no es valido ya que esta en blanco o es nulo");
        }
        this.id = UUID.randomUUID();
        this.nombre = nombre;
    }

    public UUID getId() {
        return this.id;
    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public List<Gasto> getGastos() {
        return this.gastos;
    }

    public Double getGanancias() {
        return this.ganancias;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGanancias(Double ganancias) {
        this.ganancias = ganancias;
    }
}
