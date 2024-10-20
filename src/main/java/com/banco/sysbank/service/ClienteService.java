package com.banco.sysbank.service;

import com.banco.sysbank.domain.Cliente;
import com.banco.sysbank.domain.dto.ClienteDTO;
import com.banco.sysbank.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepository.findById(id).map(this::convertToDTO);
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        return convertToDTO(clienteRepository.save(cliente));
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getIdCliente(), cliente.getNombre());
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombre(dto.getNombre());
        return cliente;
    }
}