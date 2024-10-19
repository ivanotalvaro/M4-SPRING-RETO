package com.banco.sysbank.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BASICA")
public class CuentaBasica extends Cuenta {
    @Override
    public String getTipoCuenta() {
        return "BASICA";
    }
}