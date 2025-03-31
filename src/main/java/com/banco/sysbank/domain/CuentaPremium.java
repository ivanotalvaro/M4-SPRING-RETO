package com.banco.sysbank.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import com.banco.sysbank.domain.dto.TransaccionDTO;
import com.banco.sysbank.service.TransaccionService;
import com.banco.sysbank.service.HistorialTransaccionService;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PREMIUM")
public class CuentaPremium extends Cuenta {
    @Override
    public String getTipoCuenta() {
        return "PREMIUM";
    }

    @Override
    public TransaccionDTO realizarTransaccion(Long codigoTransaccion, BigDecimal monto, TransaccionService transaccionService, HistorialTransaccionService historialTransaccionService) {
        TransaccionDTO transaccionDTO = transaccionService.findByCodigoTransaccion(codigoTransaccion);
        BigDecimal costoTransaccion =null;
        if (transaccionDTO == null) {
            throw new RuntimeException("Tipo de transacción no válido");
        }


        if (codigoTransaccion.equals(2) || codigoTransaccion.equals(6)) {
            // No se cobra por la transacción si es tipo 2 o 6
            costoTransaccion = BigDecimal.ZERO;
        }else{
            costoTransaccion = transaccionDTO.getCosto();
        }

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