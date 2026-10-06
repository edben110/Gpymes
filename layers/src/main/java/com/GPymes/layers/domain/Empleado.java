package com.GPymes.layers.domain;

import com.GPymes.layers.domain.EmpleadoState.EstadoDespedido;
import com.GPymes.layers.domain.EmpleadoState.EstadoEmpleado;
import com.GPymes.layers.domain.EmpleadoState.EstadoIncapacitado;
import com.GPymes.layers.domain.EmpleadoState.EstadoLaborando;
import com.GPymes.layers.domain.EmpleadoState.EstadoPermiso;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "documento", nullable = false, unique = true)
    private Long documento;

    @Column(name = "salario_base", nullable = false)
    private Double salarioBase;

    @Column(name = "pago_hora")
    private Double pagoHora;

    @Column(name = "horas_extra", nullable = false)
    private Integer horasExtra;

    @Column(name = "estado", nullable = false, length = 30)
    private String estadoNombre;

    @Transient
    private EstadoEmpleado estado;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Nomina> historialNominas = new ArrayList<>();

    protected Empleado() {
    }

    public Empleado(String nombre, Long documento, Double salarioBase) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no es valido, esta en blanco o es nulo");
        } else if (documento == null) {
            throw new IllegalArgumentException("El documento no es valido, es nulo");
        } else if (salarioBase == null) {
            throw new IllegalArgumentException("El salario no es valido, es nulo");
        }
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.documento = documento;
        this.salarioBase = salarioBase;
        this.horasExtra = 0;
        setEstado(new EstadoLaborando());
    }

    public UUID getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Long getDocumento() {
        return this.documento;
    }

    public Double getSalario() {
        return this.salarioBase;
    }

    public Double getPagoHora() {
        return this.pagoHora;
    }

    public Integer getHorasExtra() {
        return this.horasExtra;
    }

    public List<Nomina> getNominas() {
        return this.historialNominas;
    }

    public EstadoEmpleado getEstado() {
        if (this.estado == null) {
            this.estado = crearEstado(this.estadoNombre);
        }
        return this.estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalario(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public void setPagoHora(Double pagoHora) {
        this.pagoHora = pagoHora;
    }

    public void setHorasExtra(Integer horasExtra) {
        this.horasExtra = horasExtra;
    }

    public void setEstado(EstadoEmpleado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
        this.estadoNombre = estado.getClass().getSimpleName();
    }

    private static EstadoEmpleado crearEstado(String nombreEstado) {
        if (nombreEstado == null) {
            return new EstadoLaborando();
        }
        return switch (nombreEstado) {
            case "EstadoDespedido" -> new EstadoDespedido();
            case "EstadoPermiso" -> new EstadoPermiso();
            case "EstadoIncapacitado" -> new EstadoIncapacitado();
            default -> new EstadoLaborando();
        };
    }
}
