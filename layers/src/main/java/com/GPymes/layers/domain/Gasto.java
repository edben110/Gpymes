package com.GPymes.layers.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gastos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_gasto", discriminatorType = DiscriminatorType.STRING)
public class Gasto {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pyme_id", nullable = false)
    private Pyme pyme;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 30)
    private CategoriasGasto categoria;

    protected Gasto() {
    }

    public Gasto(Double montoTotal, LocalDateTime fechaPago, CategoriasGasto categoria, Pyme pyme) {
        if (montoTotal == null || montoTotal < 0) {
            throw new IllegalArgumentException("El monto total no es valido, es nulo o negativo");
        } else if (categoria == null) {
            throw new IllegalArgumentException("La categoria no es valida, es nula");
        } else if (pyme == null) {
            throw new IllegalArgumentException("El gasto debe pertenecer a una pyme");
        }
        this.id = UUID.randomUUID();
        this.montoTotal = montoTotal;
        this.fechaPago = fechaPago == null ? LocalDateTime.now() : fechaPago;
        this.categoria = categoria;
        this.pyme = pyme;
    }

    public UUID getId() {return this.id;}
    public Pyme getPyme() {return this.pyme;}
    public Double getMontoTotal() {return this.montoTotal;}
    public LocalDateTime getFechaPago() {return this.fechaPago;}
    public CategoriasGasto getCategoria() {return this.categoria;}


    public void setMontoTotal(Double montoTotal) {this.montoTotal = montoTotal;}
    public void setFechaPago(LocalDateTime fechaPago) {this.fechaPago = fechaPago;}
}
