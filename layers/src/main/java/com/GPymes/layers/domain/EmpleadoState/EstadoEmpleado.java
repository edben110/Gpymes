package com.GPymes.layers.domain.EmpleadoState;

import com.GPymes.layers.domain.Empleado;

public interface EstadoEmpleado {
    void despedido(Empleado empleado);

    void incapacitado(Empleado empleado);

    void permiso(Empleado empleado);

    void laborando(Empleado empleado);
}
