package com.GPymes.layers.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Nomina extends Gasto {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @Column(name = "deducciones")
    private Double deducciones;

    protected Nomina() {
    }

    public Nomina(Double montoTotal, LocalDateTime fechaPago, Empleado empleado, Double deducciones) {
        super(montoTotal, fechaPago, CategoriasGasto.NOMINA, pymeDe(empleado));
        this.empleado = empleado;
        this.deducciones = deducciones == null ? 0.0 : deducciones;
    }

    private static Pyme pymeDe(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado de la nomina no puede ser nulo");
        }
        return empleado.getPyme();
    }

    public Empleado getEmpleado() {return this.empleado;}

    public Double getDeducciones() {return this.deducciones;}

    public void setDeducciones(Double deducciones) {this.deducciones = deducciones;}
}
