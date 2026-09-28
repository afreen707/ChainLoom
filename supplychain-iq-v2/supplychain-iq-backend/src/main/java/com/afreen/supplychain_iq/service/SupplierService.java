package com.afreen.supplychain_iq.service;

import com.afreen.supplychain_iq.dto.SupplierDTO;
import java.util.List;

public interface SupplierService {
    SupplierDTO create(SupplierDTO dto);
    SupplierDTO getById(Long id);
    List<SupplierDTO> getAll();
    SupplierDTO update(Long id, SupplierDTO dto);
    void delete(Long id);
}