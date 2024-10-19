package com.banco.sysbank.domain.dto;

public class ClienteDTO {
    private Long idCliente;
    private String nombre;

    // Constructor
    public ClienteDTO(Long idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}