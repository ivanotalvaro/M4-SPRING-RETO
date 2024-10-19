package com.banco.sysbank.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PREMIUM")
public class CuentaPremium extends Cuenta {
    @Override
    public String getTipoCuenta() {
        return "PREMIUM";
    }
}