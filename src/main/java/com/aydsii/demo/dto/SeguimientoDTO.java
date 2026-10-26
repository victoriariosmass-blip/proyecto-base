package com.verdegreen.dto;

import com.verdegreen.model.CambioEstado;
import com.verdegreen.model.Envio;
import com.verdegreen.model.EstadoPedido;
import com.verdegreen.model.Pedido;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/** Datos del seguimiento de una compra con entrega a domicilio. */
public class SeguimientoDTO {

    private Long envioId;
    private Long pedidoId;
    private EstadoPedido estadoActual;
    private String direccion;
    private LocalDate fechaProgramada;   // null = pendiente de programación (FA-01)
    private String observaciones;        // altercados del envío (FA-02)
    private List<CambioEstadoDTO> historial;

    public SeguimientoDTO(Envio envio, Pedido pedido, List<CambioEstado> historial) {
        this.envioId = envio.getId();
        this.pedidoId = pedido.getId();
        this.estadoActual = pedido.getEstado();
        this.direccion = envio.getDireccion();
        this.fechaProgramada = envio.getFechaProgramada();
        this.observaciones = envio.getObservaciones();
        this.historial = historial.stream()
                .sorted(Comparator.comparing(CambioEstado::getFechaHora))
                .map(CambioEstadoDTO::new)
                .toList();
    }

    public Long getEnvioId() {
        return envioId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public EstadoPedido getEstadoActual() {
        return estadoActual;
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

    public List<CambioEstadoDTO> getHistorial() {
        return historial;
    }
}
