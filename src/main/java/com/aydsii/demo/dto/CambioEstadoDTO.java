package com.verdegreen.dto;

import com.verdegreen.model.CambioEstado;
import com.verdegreen.model.EstadoPedido;

import java.time.LocalDateTime;

public class CambioEstadoDTO {

    private EstadoPedido estado;
    private LocalDateTime fechaHora;

    public CambioEstadoDTO(CambioEstado cambio) {
        this.estado = cambio.getEstado();
        this.fechaHora = cambio.getFechaHora();
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
