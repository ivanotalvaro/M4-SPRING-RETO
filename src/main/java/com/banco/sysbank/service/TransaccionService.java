package com.banco.sysbank.service;

import com.banco.sysbank.domain.Cuenta;
import com.banco.sysbank.domain.Transaccion;
import com.banco.sysbank.domain.dto.TransaccionDTO;
<<<<<<< HEAD
import com.banco.sysbank.exception.SaldoInsuficienteException;
=======
>>>>>>> upstream/main
import com.banco.sysbank.repository.TransaccionRepository;
import com.banco.sysbank.repository.CuentaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final HistorialTransaccionService historialTransaccionService;

    public TransaccionService(TransaccionRepository transaccionRepository,
                              CuentaRepository cuentaRepository,
                              @Lazy HistorialTransaccionService historialTransaccionService) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
        this.historialTransaccionService = historialTransaccionService;
    }

    public List<TransaccionDTO> findAll() {
        return transaccionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransaccionDTO> findById(Long id) {
        return transaccionRepository.findById(id).map(this::convertToDTO);
    }

    public TransaccionDTO save(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = convertToEntity(transaccionDTO);
        return convertToDTO(transaccionRepository.save(transaccion));
    }

    public void deleteById(Long id) {
        transaccionRepository.deleteById(id);
    }

    @Transactional
    public TransaccionDTO realizarTransaccion(Long idCuenta, Long codigoTransaccion, BigDecimal monto) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

<<<<<<< HEAD
        BigDecimal saldoActual = obtenerSaldoCuenta(idCuenta);
        if (saldoActual.compareTo(monto) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta.");
        }

        return cuenta.realizarTransaccion(codigoTransaccion, monto, this, historialTransaccionService);
    }

    private BigDecimal obtenerSaldoCuenta(Long idCuenta) {
        // Implementación para obtener el saldo de la cuenta
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return cuenta.getSaldo();
    }

=======
        return cuenta.realizarTransaccion(codigoTransaccion, monto, this, historialTransaccionService);
    }

>>>>>>> upstream/main
    public TransaccionDTO findByCodigoTransaccion(Long codigoTransaccion) {
        return transaccionRepository.findByCodigoTransaccion(codigoTransaccion)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private TransaccionDTO convertToDTO(Transaccion transaccion) {
        return new TransaccionDTO(
                transaccion.getCodigoTransaccion(),
                transaccion.getTipoTransaccion(),
                transaccion.getCosto()
        );
    }

    private Transaccion convertToEntity(TransaccionDTO dto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setCodigoTransaccion(dto.getCodigoTransaccion());
        transaccion.setTipoTransaccion(dto.getTipoTransaccion());
        transaccion.setCosto(dto.getCosto());
        return transaccion;
    }
}