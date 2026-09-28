package com.afreen.supplychain_iq.service;

import com.afreen.supplychain_iq.dto.ShipmentDTO;
import java.util.List;

public interface ShipmentService {
    ShipmentDTO create(ShipmentDTO dto);
    ShipmentDTO getById(Long id);
    List<ShipmentDTO> getAll();
    ShipmentDTO update(Long id, ShipmentDTO dto);
}
