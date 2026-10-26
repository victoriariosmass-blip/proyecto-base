package com.verdegreen.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "limite_por_pedido")
    private int limitePorPedido;

    protected Categoria() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLimitePorPedido() {
        return limitePorPedido;
    }
}
