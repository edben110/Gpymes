package main.java.com.GPymes.layers.domain.EmpleadoState;
import main.java.com.GPymes.layers.domain.Empleado;

public class EstadoPermiso implements EstadoEmpleado {
    @Override 
    public void despedido(Empleado empleado){
        empleado.setEstado(new EstadoDespedido());
    }
    @Override
    public void permiso(Empleado empleado){
        throw new IllegalArgumentException("el estado ya esta en Permiso");
    }
    @Override
    public void incapacitado(Empleado empleado){
        empleado.setEstado(new EstadoIncapacitado());
    }
    @Override
    public void laborando(Empleado empleado){
        empleado.setEstado(new EstadoLaborando());
    }
}
