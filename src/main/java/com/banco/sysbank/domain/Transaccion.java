package com.banco.sysbank.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transacciones")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigotransaccion")
    private Long codigoTransaccion;

    @Column(name = "tipotransaccion", nullable = false)
    private String tipoTransaccion;

    @Column(name = "costo", nullable = false)
    private BigDecimal costo;

    // Getters y setters

    public Long getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(Long codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
}