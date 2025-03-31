package com.banco.sysbank.service;

import com.banco.sysbank.domain.*;
import com.banco.sysbank.domain.dto.HistorialTransaccionDTO;
import com.banco.sysbank.domain.dto.CuentaDTO;
import com.banco.sysbank.domain.dto.TransaccionDTO;
import com.banco.sysbank.repository.HistorialTransaccionRepository;
import com.banco.sysbank.repository.CuentaRepository;
import com.banco.sysbank.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class HistorialTransaccionService {
    private final HistorialTransaccionRepository historialTransaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;
    private final CuentaService cuentaService;
    private final TransaccionService transaccionService;

    public HistorialTransaccionService(HistorialTransaccionRepository historialTransaccionRepository,
                                       CuentaRepository cuentaRepository,
                                       TransaccionRepository transaccionRepository,
                                       CuentaService cuentaService,
                                       TransaccionService transaccionService) {
        this.historialTransaccionRepository = historialTransaccionRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
        this.cuentaService = cuentaService;
        this.transaccionService = transaccionService;
    }

    public List<HistorialTransaccionDTO> findAll() {
        return historialTransaccionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HistorialTransaccionDTO> findById(Long id) {
        return historialTransaccionRepository.findById(id).map(this::convertToDTO);
    }

    public HistorialTransaccionDTO save(HistorialTransaccionDTO historialTransaccionDTO) {
        HistorialTransaccion historialTransaccion = convertToEntity(historialTransaccionDTO);
        return convertToDTO(historialTransaccionRepository.save(historialTransaccion));
    }

    public void deleteById(Long id) {
        historialTransaccionRepository.deleteById(id);
    }

    @Transactional
    public HistorialTransaccionDTO registrarTransaccion(Long idCuenta, Long codigoTransaccion, BigDecimal monto, LocalDateTime fechaHora) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Transaccion transaccion = transaccionRepository.findById(codigoTransaccion)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        HistorialTransaccion historialTransaccion = new HistorialTransaccion();
        historialTransaccion.setMonto(monto);
        historialTransaccion.setFechaHora(fechaHora);
        historialTransaccion.setCodigoTransaccion(transaccion.getTipoTransaccion() + "-" + System.currentTimeMillis());
        historialTransaccion.setCuenta(cuenta);
        historialTransaccion.setTransaccion(transaccion);

        HistorialTransaccion savedHistorial = historialTransaccionRepository.save(historialTransaccion);
        return convertToDTO(savedHistorial);
    }

    private HistorialTransaccionDTO convertToDTO(HistorialTransaccion historialTransaccion) {
        return new HistorialTransaccionDTO(
                historialTransaccion.getId(),
                historialTransaccion.getMonto(),
                historialTransaccion.getFechaHora(),
                historialTransaccion.getCodigoTransaccion(),
                historialTransaccion.getCuenta().getId(),
                historialTransaccion.getTransaccion().getCodigoTransaccion()
        );
    }

    private HistorialTransaccion convertToEntity(HistorialTransaccionDTO dto) {
        HistorialTransaccion historialTransaccion = new HistorialTransaccion();
        historialTransaccion.setId(dto.getId());
        historialTransaccion.setMonto(dto.getMonto());
        historialTransaccion.setFechaHora(dto.getFechaHora());
        historialTransaccion.setCodigoTransaccion(dto.getCodigoTransaccion());

        // Obtener la cuenta y la transacción usando los servicios correspondientes
        Cuenta cuenta = cuentaService.findById(dto.getIdCuenta())
                .map(this::convertToEntity)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        historialTransaccion.setCuenta(cuenta);

        Transaccion transaccion = transaccionService.findById(dto.getCodigoTransaccionRef())
                .map(this::convertTransaccionDTOToEntity)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        historialTransaccion.setTransaccion(transaccion);

        return historialTransaccion;
    }

    public List<HistorialTransaccionDTO> getUltimas5TransaccionesPorCuenta(Long idCuenta) {
        return historialTransaccionRepository.findTop5ByCuentaIdOrderByFechaHoraDesc(idCuenta).stream()
                .limit(5)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Cuenta convertToEntity(CuentaDTO dto) {
        Cuenta cuenta;
        if ("BASICA".equals(dto.getTipoCuenta())) {
            cuenta = new CuentaBasica();
        } else if ("PREMIUM".equals(dto.getTipoCuenta())) {
            cuenta = new CuentaPremium();
        } else {
            throw new IllegalArgumentException("Tipo de cuenta no válido");
        }

        if (dto.getId() != null) {
            cuenta.setId(dto.getId());
        }
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setSaldo(dto.getSaldo());
        return cuenta;
    }

    private Transaccion convertTransaccionDTOToEntity(TransaccionDTO dto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setCodigoTransaccion(dto.getCodigoTransaccion());
        transaccion.setTipoTransaccion(dto.getTipoTransaccion());
        transaccion.setCosto(dto.getCosto());
        return transaccion;
    }
}