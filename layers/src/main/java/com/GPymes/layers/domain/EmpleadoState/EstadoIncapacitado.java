package main.java.com.GPymes.layers.domain.EmpleadoState; 
import main.java.com.GPymes.layers.domain.Empleado;

public class EstadoIncapacitado implements EstadoEmpleado {
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
        throw new IllegalArgumentException("el estado ya esta en Incapacitado");
    }
    @Override
    public void laborando(Empleado empleado){
        empleado.setEstado(new EstadoLaborando());
    }    
}
