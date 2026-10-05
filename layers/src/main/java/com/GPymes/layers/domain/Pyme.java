package main.java.com.GPymes.layers.domain;

import java.util.ArrayList;
import java.util.UUID;

import com.hotel.Hotel.domain.empleado;

public class Pyme {
    private final UUID id;
    private String nombre;
    private ArrayList<Empleado> empleados;
    private Double ganancias;

    public Pyme(String nombre){
        if(nombre.isBlank() || nombre == null){
            throw new IllegalArgumentException("El nombre no es valido ya que esta en blanco o es nulo") ;
        }
        this.id= UUID.randomUUID();
        this.nombre = nombre;
    }

    public UUID getId() {
        return this.id;
    }
    public ArrayList<Empleado> getEmpleados() {
        return this.empleados;
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
}
