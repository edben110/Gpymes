package main.java.com.GPymes.layers.domain.EmpleadoState;

public interface EstadoEmpleado {
    void despedido(Empleado empleado);
    void incapacitado(Empleado empleado);
    void permiso(Empleado empleado);
    void laborando(Empleado empleado);

} 
