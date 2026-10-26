package com.aydsii.demo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private int stock;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    private boolean personalizable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    protected Producto() {
    }

    /**
     * RN-02: techo de la cantidad que se puede elegir de este producto.
     * Es el menor entre el máximo de su categoría y el stock actual.
     * Se usa al agregar productos al carrito (ABM carrito), no durante la compra.
     */
    public int cantidadMaximaSeleccionable() {
        return Math.min(categoria.getLimitePorPedido(), stock);
    }

    /** Congela (descuenta) el stock al registrar el pedido. */
    public void reservarStock(int cantidad) {
        this.stock -= cantidad;
    }

    public boolean bajoStockMinimo() {
        return stock < stockMinimo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public boolean isPersonalizable() {
        return personalizable;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
