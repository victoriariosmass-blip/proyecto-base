package com.verdegreen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/** Reglas configurables del sistema, por ejemplo HORAS_VENCIMIENTO = 72. */
@Entity
@Table(name = "parametro_sistema")
public class ParametroSistema {

    @Id
    private String clave;

    private String valor;

    protected ParametroSistema() {
    }

    public int getValorEntero() {
        return Integer.parseInt(valor.trim());
    }

    public BigDecimal getValorDecimal() {
        return new BigDecimal(valor.trim());
    }

    public String getClave() {
        return clave;
    }

    public String getValor() {
        return valor;
    }
}
