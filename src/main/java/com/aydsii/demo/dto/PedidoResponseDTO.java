package com.verdegreen.dto;

import com.verdegreen.model.EstadoPedido;
import com.verdegreen.model.Envio;
import com.verdegreen.model.Pedido;
import com.verdegreen.model.TipoEntrega;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Resumen del pedido que se le devuelve al cliente después de comprar. */
public class PedidoResponseDTO {

    private Long pedidoId;
    private EstadoPedido estado;
    private TipoEntrega tipoEntrega;
    private BigDecimal subtotalProductos;
    private BigDecimal costoEnvio;
    private BigDecimal costoPersonalizacion;
    private BigDecimal total;
    private LocalDateTime fechaVencimiento;
    private List<DetallePedidoDTO> detalles;
    private Long envioId;          // null si el pedido es con retiro
    private String enlaceWhatsApp;

    public PedidoResponseDTO(Pedido pedido, Envio envio, String enlace) {
        this.pedidoId = pedido.getId();
        this.estado = pedido.getEstado();
        this.tipoEntrega = pedido.getTipoEntrega();
        this.subtotalProductos = pedido.getSubtotalProductos();
        this.costoEnvio = pedido.getCostoEnvio();
        this.costoPersonalizacion = pedido.getCostoPersonalizacion();
        this.total = pedido.getTotal();
        this.fechaVencimiento = pedido.getFechaVencimiento();
        this.detalles = pedido.getDetalles().stream().map(DetallePedidoDTO::new).toList();
        this.envioId = (envio != null) ? envio.getId() : null;
        this.enlaceWhatsApp = enlace;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public BigDecimal getSubtotalProductos() {
        return subtotalProductos;
    }

    public BigDecimal getCostoEnvio() {
        return costoEnvio;
    }

    public BigDecimal getCostoPersonalizacion() {
        return costoPersonalizacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public Long getEnvioId() {
        return envioId;
    }

    public String getEnlaceWhatsApp() {
        return enlaceWhatsApp;
    }
}
