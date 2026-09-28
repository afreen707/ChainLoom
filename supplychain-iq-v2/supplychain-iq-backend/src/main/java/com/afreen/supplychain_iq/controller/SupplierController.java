package com.afreen.supplychain_iq.controller;

import com.afreen.supplychain_iq.dto.SupplierDTO;
import com.afreen.supplychain_iq.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierDTO> create(@Valid @RequestBody SupplierDTO dto) {
        return new ResponseEntity<>(supplierService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAll() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> update(@PathVariable Long id, @Valid @RequestBody SupplierDTO dto) {
        return ResponseEntity.ok(supplierService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
