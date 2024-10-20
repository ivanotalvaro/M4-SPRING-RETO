package com.banco.sysbank.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import com.banco.sysbank.domain.dto.TransaccionDTO;
import com.banco.sysbank.service.TransaccionService;
import com.banco.sysbank.service.HistorialTransaccionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("BASICA")
public class CuentaBasica extends Cuenta {
    @Override
    public String getTipoCuenta() {
        return "BASICA";
    }

    @Override
    public TransaccionDTO realizarTransaccion(Long codigoTransaccion, BigDecimal monto, TransaccionService transaccionService, HistorialTransaccionService historialTransaccionService) {
        TransaccionDTO transaccionDTO = transaccionService.findByCodigoTransaccion(codigoTransaccion);
        if (transaccionDTO == null) {
            throw new RuntimeException("Tipo de transacción no válido");
        }

        BigDecimal costoTransaccion = transaccionDTO.getCosto();

        switch(codigoTransaccion.intValue()) {
            case 1,2,3:
                this.actualizarSaldo(monto.subtract(costoTransaccion));
                break;
            case 4,5,6:
                monto.subtract(costoTransaccion);
                this.actualizarSaldo(monto.negate());
                break;
        }

        // Registrar en el historial de transacciones
        historialTransaccionService.registrarTransaccion(this.getId(), transaccionDTO.getCodigoTransaccion(), monto, LocalDateTime.now());

        return transaccionDTO;
    }
}