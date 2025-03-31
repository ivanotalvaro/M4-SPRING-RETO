package com.banco.sysbank.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialTransaccionDTO {
    private Long id;
    private BigDecimal monto;
    private LocalDateTime fechaHora;
    private String codigoTransaccion;
    private Long idCuenta;
    private Long codigoTransaccionRef;

    // Constructor
    public HistorialTransaccionDTO(Long id, BigDecimal monto, LocalDateTime fechaHora,
                                   String codigoTransaccion, Long idCuenta, Long codigoTransaccionRef) {
        this.id = id;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.codigoTransaccion = codigoTransaccion;
        this.idCuenta = idCuenta;
        this.codigoTransaccionRef = codigoTransaccionRef;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Long getCodigoTransaccionRef() {
        return codigoTransaccionRef;
    }

    public void setCodigoTransaccionRef(Long codigoTransaccionRef) {
        this.codigoTransaccionRef = codigoTransaccionRef;
    }
}