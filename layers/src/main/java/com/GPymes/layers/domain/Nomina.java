package com.GPymes.layers.domain;

import com.GPymes.layers.domain.EmpleadoState.EstadoDespedido;
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
        this.deducciones = validarDeducciones(deducciones);
        validarEmpleadoNoDespedido(empleado);
    }

    private static Pyme pymeDe(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado de la nomina no puede ser nulo");
        }
        return empleado.getPyme();
    }

    private static Double validarDeducciones(Double deducciones) {
        if (deducciones != null && deducciones < 0) {
            throw new IllegalArgumentException("Las deducciones no pueden ser negativas: " + deducciones);
        }
        return deducciones == null ? 0.0 : deducciones;
    }

    private static void validarEmpleadoNoDespedido(Empleado empleado) {
        if (empleado.getEstado() instanceof EstadoDespedido) {
            throw new IllegalArgumentException(
                    "No se puede crear una nomina para un empleado despedido (ID: " + empleado.getId() + ")");
        }
    }

    public Empleado getEmpleado() {return this.empleado;}

    public Double getDeducciones() {return this.deducciones;}

    public void setDeducciones(Double deducciones) {
        this.deducciones = validarDeducciones(deducciones);
    }
}
