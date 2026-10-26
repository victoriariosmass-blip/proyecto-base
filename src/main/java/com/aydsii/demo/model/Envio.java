package com.aydsii.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "envio")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;

    @Column(name = "fecha_programada")
    private LocalDate fechaProgramada;

    private String observaciones;

    // Envio -> Pedido (0..1 a 1). Se navega del envío al pedido (CU-02).
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido;

    protected Envio() {
    }

    /** Se crea al comprar, solo si el pedido es a domicilio. La fecha la pone Logística después. */
    public Envio(Pedido pedido, String direccion) {
        this.pedido = pedido;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public String getDireccion() {
        return direccion;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
