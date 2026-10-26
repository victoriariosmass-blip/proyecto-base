package com.verdegreen.dto;

import com.verdegreen.model.TipoEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Datos que manda el cliente al presionar "Comprar". */
@Schema(description = "Datos para registrar la compra del carrito")
public class CompraRequestDTO {

    @Schema(description = "Id del cliente logueado", example = "1")
    @NotNull(message = "es obligatorio")
    @Positive(message = "debe ser un número positivo")
    private Long clienteId;

    @Schema(description = "Referencia al comprobante de transferencia (el pago no se implementa)",
            example = "transferencia_ana_20261023.jpg")
    @NotBlank(message = "el comprobante de transferencia es obligatorio")
    private String comprobante;

    @Schema(description = "Si el cliente quiere personalizar productos", example = "false")
    private boolean personalizado;

    @Schema(description = "DOMICILIO o RETIRO", example = "DOMICILIO")
    @NotNull(message = "es obligatorio (DOMICILIO o RETIRO)")
    private TipoEntrega tipoEntrega;

    public CompraRequestDTO() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public boolean isPersonalizado() {
        return personalizado;
    }

    public void setPersonalizado(boolean personalizado) {
        this.personalizado = personalizado;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }
}
