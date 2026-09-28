package com.afreen.supplychain_iq.service;

import com.afreen.supplychain_iq.dto.PurchaseOrderDTO;
import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrderDTO create(PurchaseOrderDTO dto);
    PurchaseOrderDTO getById(Long id);
    List<PurchaseOrderDTO> getAll();
    PurchaseOrderDTO updateStatus(Long id, String status);
    void delete(Long id);
}
