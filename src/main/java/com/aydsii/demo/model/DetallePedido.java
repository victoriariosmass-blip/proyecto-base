package com.aydsii.demo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_producto")
    private String codigoProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    private int cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    // DetallePedido -> Producto (* a 1)
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    protected DetallePedido() {
    }

    /** RN-05: snapshot inmutable, copia código, nombre y precio del momento. */
    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.codigoProducto = producto.getCodigo();
        this.nombreProducto = producto.getNombre();
        this.precioUnitario = producto.getPrecio();
        this.cantidad = cantidad;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
}
