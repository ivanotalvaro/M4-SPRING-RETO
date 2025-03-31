package com.banco.sysbank.controller;

import com.banco.sysbank.domain.dto.TransaccionDTO;
import com.banco.sysbank.exception.SaldoInsuficienteException;
import com.banco.sysbank.service.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<TransaccionDTO> getAllTransacciones() {
        return transaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> getTransaccionById(@PathVariable Long id) {
        return transaccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TransaccionDTO createTransaccion(@RequestBody TransaccionDTO transaccionDTO) {
        return transaccionService.save(transaccionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionDTO> updateTransaccion(@PathVariable Long id, @RequestBody TransaccionDTO transaccionDTO) {
        return transaccionService.findById(id)
                .map(existingTransaccion -> {
                    transaccionDTO.setCodigoTransaccion(id);
                    return ResponseEntity.ok(transaccionService.save(transaccionDTO));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccion(@PathVariable Long id) {
        return transaccionService.findById(id)
                .map(transaccion -> {
                    transaccionService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/realizar")
    public ResponseEntity<TransaccionDTO> realizarTransaccion(
            @RequestParam Long idCuenta,
            @RequestParam Long codigoTransaccion,
            @RequestParam BigDecimal monto) {
        TransaccionDTO resultado = transaccionService.realizarTransaccion(idCuenta, codigoTransaccion, monto);
        return ResponseEntity.ok(resultado);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}