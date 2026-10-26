package com.verdegreen.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cambio_estado")
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    private String observacion;

    protected CambioEstado() {
    }

    /** RN-07: cada cambio de estado se registra con su fecha y hora. */
    public CambioEstado(EstadoPedido estado) {
        this.estado = estado;
        this.fechaHora = LocalDateTime.now();
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getObservacion() {
        return observacion;
    }
}
