package main.java.com.GPymes.layers.domain.EmpleadoState;

import com.hotel.Hotel.domain.EmpleadoState.EstadoDespedido;

import main.java.com.GPymes.layers.domain.Empleado;
import main.java.com.GPymes.layers.domain.EstadoEmpleado;

public class EstadoLaborando implements EstadoEmpleado {
    @Override 
    public void despedido(Empleado empleado){
        empleado.setEstado(new EstadoDespedido());
    }
    @Override
    public void permiso(Empleado empleado){
        empleado.setEstado(new EstadoPermiso());
    }
    @Override
    public void incapacitado(Empleado empleado){
        empleado.setEstado(new EstadoIncapacitado());
    }
    @Override
    public void laborando(Empleado empleado){
        throw new IllegalArgumentException("el estado ya esta en laborando");
    }
}
