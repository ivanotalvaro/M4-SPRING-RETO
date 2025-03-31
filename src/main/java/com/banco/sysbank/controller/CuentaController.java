package com.banco.sysbank.controller;

import com.banco.sysbank.domain.dto.CuentaDTO;
import com.banco.sysbank.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:4200")
=======
>>>>>>> upstream/main
@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<CuentaDTO> getAllCuentas() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getCuentaById(@PathVariable Long id) {
        return cuentaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CuentaDTO createCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return cuentaService.save(cuentaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) {
        return cuentaService.findById(id)
                .map(existingCuenta -> {
                    cuentaDTO.setId(id);
                    return ResponseEntity.ok(cuentaService.save(cuentaDTO));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        return cuentaService.findById(id)
                .map(cuenta -> {
                    cuentaService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}