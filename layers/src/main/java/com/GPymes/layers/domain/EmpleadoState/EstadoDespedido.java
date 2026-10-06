package com.GPymes.layers.domain.EmpleadoState;

import com.GPymes.layers.domain.Empleado;

public class EstadoDespedido implements EstadoEmpleado {
    @Override
    public void despedido(Empleado empleado) {
        throw new IllegalArgumentException("el estado ya esta en Despedido");
    }

    @Override
    public void permiso(Empleado empleado) {
        empleado.setEstado(new EstadoPermiso());
    }

    @Override
    public void incapacitado(Empleado empleado) {
        empleado.setEstado(new EstadoIncapacitado());
    }

    @Override
    public void laborando(Empleado empleado) {
        empleado.setEstado(new EstadoLaborando());
    }
}
