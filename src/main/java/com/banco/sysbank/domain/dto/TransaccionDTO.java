package com.banco.sysbank.domain.dto;

import java.math.BigDecimal;

public class TransaccionDTO {
    private Long codigoTransaccion;
    private String tipoTransaccion;
    private BigDecimal costo;

    // Constructor
    public TransaccionDTO(Long codigoTransaccion, String tipoTransaccion, BigDecimal costo) {
        this.codigoTransaccion = codigoTransaccion;
        this.tipoTransaccion = tipoTransaccion;
        this.costo = costo;
    }

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