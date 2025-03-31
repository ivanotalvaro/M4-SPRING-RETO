package com.banco.sysbank.domain.dto;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long id;
    private String tipoCuenta;
    private BigDecimal saldo;
    private String numeroCuenta;
    private Long idCliente;

    // Constructor
    public CuentaDTO(Long id, String tipoCuenta, BigDecimal saldo, String numeroCuenta, Long idCliente) {
        this.id = id;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
        this.idCliente = idCliente;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}