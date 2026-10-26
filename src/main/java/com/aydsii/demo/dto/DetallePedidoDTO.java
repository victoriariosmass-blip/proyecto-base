package com.aydsii.demo.dto;

import com.aydsii.demo.model.DetallePedido;

import java.math.BigDecimal;

public class DetallePedidoDTO {

    private String codigo;
    private String nombre;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetallePedidoDTO(DetallePedido detalle) {
        this.codigo = detalle.getCodigoProducto();
        this.nombre = detalle.getNombreProducto();
        this.cantidad = detalle.getCantidad();
        this.precioUnitario = detalle.getPrecioUnitario();
        this.subtotal = detalle.getSubtotal();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
