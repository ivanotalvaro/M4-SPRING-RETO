package com.banco.sysbank.service;

import com.banco.sysbank.domain.*;
import com.banco.sysbank.domain.dto.ClienteDTO;
import com.banco.sysbank.domain.dto.CuentaDTO;
import com.banco.sysbank.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;

    public CuentaService(CuentaRepository cuentaRepository, ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
    }

    // Métodos findAll, findById, deleteById sin cambios
    public List<CuentaDTO> findAll() {
        return cuentaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CuentaDTO> findById(Long id) {
        return cuentaRepository.findById(id).map(this::convertToDTO);
    }

    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }

    public CuentaDTO save(CuentaDTO cuentaDTO) {
        ClienteDTO clienteDTO = clienteService.findById(cuentaDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        Cuenta cuenta = convertToEntity(cuentaDTO);
        cuenta.setCliente(convertClienteDTOToEntity(clienteDTO));

        Cuenta savedCuenta = cuentaRepository.save(cuenta);
        return convertToDTO(savedCuenta);
    }

    @Transactional
    public void actualizarSaldo(Long idCuenta, BigDecimal monto) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        BigDecimal nuevoSaldo = cuenta.getSaldo().add(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
    }

    private CuentaDTO convertToDTO(Cuenta cuenta) {
        return new CuentaDTO(
                cuenta.getId(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldo(),
                cuenta.getNumeroCuenta(),
                cuenta.getCliente().getIdCliente()
        );
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

    private Cliente convertClienteDTOToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombre(dto.getNombre());
        return cliente;
    }
}