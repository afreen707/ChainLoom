package com.afreen.supplychain_iq.controller;

import com.afreen.supplychain_iq.dto.ShipmentDTO;
import com.afreen.supplychain_iq.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ShipmentDTO> create(@Valid @RequestBody ShipmentDTO dto) {
        return new ResponseEntity<>(shipmentService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentDTO>> getAll() {
        return ResponseEntity.ok(shipmentService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipmentDTO> update(@PathVariable Long id, @Valid @RequestBody ShipmentDTO dto) {
        return ResponseEntity.ok(shipmentService.update(id, dto));
    }
}
