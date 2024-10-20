package com.banco.sysbank.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.banco.sysbank.domain.dto.TransaccionDTO;
import com.banco.sysbank.service.TransaccionService;
import com.banco.sysbank.service.HistorialTransaccionService;

@Entity
@Table(name = "cuentas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipocuenta", discriminatorType = DiscriminatorType.STRING)
public abstract class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "saldo", nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    @Column(name = "numerocuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    private Cliente cliente;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Método abstracto para obtener el tipo de cuenta
    public abstract String getTipoCuenta();

    // Método abstracto para realizar transacción
    public abstract TransaccionDTO realizarTransaccion(Long codigoTransaccion, BigDecimal monto, TransaccionService transaccionService, HistorialTransaccionService historialTransaccionService);

    // Método para actualizar saldo
    public void actualizarSaldo(BigDecimal monto) {
        BigDecimal nuevoSaldo = this.saldo.add(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        this.saldo = nuevoSaldo;
    }
}