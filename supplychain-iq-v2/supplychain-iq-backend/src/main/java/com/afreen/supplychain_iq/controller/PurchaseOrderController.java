package com.afreen.supplychain_iq.controller;

import com.afreen.supplychain_iq.dto.PurchaseOrderDTO;
import com.afreen.supplychain_iq.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> create(@Valid @RequestBody PurchaseOrderDTO dto) {
        return new ResponseEntity<>(purchaseOrderService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAll() {
        return ResponseEntity.ok(purchaseOrderService.getAll());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderDTO> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(purchaseOrderService.updateStatus(id, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
