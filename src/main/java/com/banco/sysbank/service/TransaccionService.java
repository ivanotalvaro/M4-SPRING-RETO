package com.banco.sysbank.service;

import com.banco.sysbank.domain.Transaccion;
import com.banco.sysbank.domain.dto.TransaccionDTO;
import com.banco.sysbank.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final CuentaService cuentaService;
    private final HistorialTransaccionService historialTransaccionService;

    @Autowired
    public TransaccionService(TransaccionRepository transaccionRepository,
                              CuentaService cuentaService,
                              @Lazy HistorialTransaccionService historialTransaccionService) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaService = cuentaService;
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
    public TransaccionDTO realizarTransaccion(Long idCuenta, String tipoTransaccion, BigDecimal monto) {
        TransaccionDTO transaccionDTO = findByTipoTransaccion(tipoTransaccion);
        if (transaccionDTO == null) {
            throw new RuntimeException("Tipo de transacción no válido");
        }

        BigDecimal costoTransaccion = transaccionDTO.getCosto();
        BigDecimal montoTotal = monto.add(costoTransaccion);

        // Actualizar saldo de la cuenta
        cuentaService.actualizarSaldo(idCuenta, montoTotal.negate());

        // Registrar en el historial de transacciones
        historialTransaccionService.registrarTransaccion(idCuenta, transaccionDTO.getCodigoTransaccion(), monto, LocalDateTime.now());

        return transaccionDTO;
    }

    private TransaccionDTO findByTipoTransaccion(String tipoTransaccion) {
        return transaccionRepository.findByTipoTransaccion(tipoTransaccion)
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