package main.java.com.GPymes.layers.domain;

import java.sql.Date;
import java.util.ArrayList;

public class Empleado {

    private final UUID id;
    private String nombre;
    private final Int documento;
    private Double salarioBase;
    private Double pagoHora;
    private Int horasExtra;
    private EstadoEmpleado estado;
    private ArrayList<Nomina> historialNominas;
     
    public Empleado(String nombre, Int documento, Double salarioBase){
        if(nombre.isBlank() || nombre== null){
            throw new IllegalArgumentException("El nombre no es valido, esta en blanco o es nulo");
        }else if(documento.isBlank() || documento== null){
            throw new IllegalArgumentException("El documento no es valido, esta en blanco o es nulo");
        }else if(salario.isBlank() || salario== null){
            throw new IllegalArgumentException("El salario no es valido, esta en blanco o es nulo");
        }
        this.id = UUID.randomUUID();
        this.nombre=nombre;
        this.documento=documento;
        this.salarioBase = salarioBase;
        this.horasExtra = 0;
        this.estado = new EstadoLaborando();
    }
    public UUID getId() {return this.id;}
    public String getNombre() {return this.nombre;}
    public Int getDocumento() {return this.documento;}
    public Double getSalario() {return this.salarioBase;}
    public Int getHorasExtra() {return this.horasExtra;}
    public EstadoEmpleado getEstado() {return this.estado;}
    public ArrayList<Nomina> getNominas() {return this.historialNominas;}

    public String setNombre(String nombre){this.nombre=nombre;}
    public Double setSalario(Double Salario){this.salarioBase=salario;}
    public EstadoEmpleado setEstado(EstadoEmpleado estado){this.estado=estado;}
}
    