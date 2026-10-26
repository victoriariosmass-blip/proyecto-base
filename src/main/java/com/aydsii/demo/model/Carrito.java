package com.aydsii.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Carrito -> Cliente (0..1 a 1)
    @OneToOne(optional = false)
    @JoinColumn(name = "cliente_id", unique = true)
    private Cliente cliente;

    // Composición Carrito ◆-> ItemCarrito (1 a 0..*)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "carrito_id", nullable = false)
    private List<ItemCarrito> items = new ArrayList<>();

    protected Carrito() {
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    /** Borra los ítems del carrito (orphanRemoval los elimina de la base). */
    public void vaciar() {
        items.clear();
    }

    public Long getId() {
        return id;
    }
}
