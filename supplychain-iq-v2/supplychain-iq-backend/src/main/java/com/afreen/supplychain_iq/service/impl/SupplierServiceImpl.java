package com.afreen.supplychain_iq.service.impl;

import com.afreen.supplychain_iq.dto.SupplierDTO;
import com.afreen.supplychain_iq.entity.Supplier;
import com.afreen.supplychain_iq.exception.ResourceNotFoundException;
import com.afreen.supplychain_iq.repository.SupplierRepository;
import com.afreen.supplychain_iq.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierDTO create(SupplierDTO dto) {
        Supplier supplier = toEntity(dto);
        Supplier saved = supplierRepository.save(supplier);
        return toDTO(saved);
    }

    @Override
    public SupplierDTO getById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return toDTO(supplier);
    }

    @Override
    public List<SupplierDTO> getAll() {
        return supplierRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO update(Long id, SupplierDTO dto) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());
        if (dto.getReliabilityScore() != null) {
            existing.setReliabilityScore(dto.getReliabilityScore());
        }
        return toDTO(supplierRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }

    private Supplier toEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setReliabilityScore(dto.getReliabilityScore() != null ? dto.getReliabilityScore() : BigDecimal.valueOf(0.75));
        return supplier;
    }

    private SupplierDTO toDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        dto.setReliabilityScore(supplier.getReliabilityScore());
        return dto;
    }
}
