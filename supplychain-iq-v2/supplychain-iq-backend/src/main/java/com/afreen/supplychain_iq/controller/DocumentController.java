package com.afreen.supplychain_iq.controller;

import com.afreen.supplychain_iq.entity.Supplier;
import com.afreen.supplychain_iq.repository.SupplierRepository;
import com.afreen.supplychain_iq.service.impl.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers/{supplierId}/document")
@RequiredArgsConstructor
public class DocumentController {

    private final S3Service s3Service;
    private final SupplierRepository supplierRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadDocument(
            @PathVariable Long supplierId,
            @RequestParam("file") MultipartFile file) throws IOException {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        String key = s3Service.uploadFile(file, "suppliers/" + supplierId);
        supplier.setDocumentKey(key);
        supplierRepository.save(supplier);

        return ResponseEntity.ok(Map.of("message", "Document uploaded", "key", key));
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getDocumentUrl(@PathVariable Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (supplier.getDocumentKey() == null) {
            return ResponseEntity.ok(Map.of("message", "No document uploaded yet"));
        }

        String url = s3Service.getPresignedUrl(supplier.getDocumentKey());
        return ResponseEntity.ok(Map.of("url", url));
    }
}
