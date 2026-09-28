package com.afreen.supplychain_iq.controller;

import com.afreen.supplychain_iq.dto.ProductDTO;
import com.afreen.supplychain_iq.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto) {
        return new ResponseEntity<>(productService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStock() {
        return ResponseEntity.ok(productService.getLowStock());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
