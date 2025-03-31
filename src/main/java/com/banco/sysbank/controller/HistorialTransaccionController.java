package com.banco.sysbank.controller;

import com.banco.sysbank.domain.dto.HistorialTransaccionDTO;
import com.banco.sysbank.service.HistorialTransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:4200")
=======
>>>>>>> upstream/main
@RestController
@RequestMapping("/api/historial-transacciones")
public class HistorialTransaccionController {
    private final HistorialTransaccionService historialTransaccionService;

    public HistorialTransaccionController(HistorialTransaccionService historialTransaccionService) {
        this.historialTransaccionService = historialTransaccionService;
    }

    @GetMapping
    public List<HistorialTransaccionDTO> getAllHistorialTransacciones() {
        return historialTransaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialTransaccionDTO> getHistorialTransaccionById(@PathVariable Long id) {
        return historialTransaccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialTransaccionDTO createHistorialTransaccion(@RequestBody HistorialTransaccionDTO historialTransaccionDTO) {
        return historialTransaccionService.save(historialTransaccionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialTransaccionDTO> updateHistorialTransaccion(@PathVariable Long id, @RequestBody HistorialTransaccionDTO historialTransaccionDTO) {
        return historialTransaccionService.findById(id)
                .map(existingHistorialTransaccion -> {
                    historialTransaccionDTO.setId(id);
                    return ResponseEntity.ok(historialTransaccionService.save(historialTransaccionDTO));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialTransaccion(@PathVariable Long id) {
        return historialTransaccionService.findById(id)
                .map(historialTransaccion -> {
                    historialTransaccionService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ultimas5/{idCuenta}")
    public ResponseEntity<List<HistorialTransaccionDTO>> getUltimas5TransaccionesPorCuenta(@PathVariable Long idCuenta) {
        List<HistorialTransaccionDTO> ultimas5Transacciones = historialTransaccionService.getUltimas5TransaccionesPorCuenta(idCuenta);
        return ResponseEntity.ok(ultimas5Transacciones);
    }
}