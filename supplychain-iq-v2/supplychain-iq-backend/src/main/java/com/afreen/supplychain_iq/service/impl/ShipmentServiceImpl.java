package com.afreen.supplychain_iq.service.impl;

import com.afreen.supplychain_iq.dto.ShipmentDTO;
import com.afreen.supplychain_iq.entity.PurchaseOrder;
import com.afreen.supplychain_iq.entity.Shipment;
import com.afreen.supplychain_iq.enums.OrderStatus;
import com.afreen.supplychain_iq.enums.ShipmentStatus;
import com.afreen.supplychain_iq.exception.ResourceNotFoundException;
import com.afreen.supplychain_iq.repository.PurchaseOrderRepository;
import com.afreen.supplychain_iq.repository.ShipmentRepository;
import com.afreen.supplychain_iq.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public ShipmentDTO create(ShipmentDTO dto) {
        PurchaseOrder order = purchaseOrderRepository.findById(dto.getPurchaseOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + dto.getPurchaseOrderId()));

        Shipment shipment = new Shipment();
        shipment.setPurchaseOrder(order);
        shipment.setTrackingNumber(dto.getTrackingNumber());
        shipment.setCarrier(dto.getCarrier());
        shipment.setShippedDate(dto.getShippedDate());
        shipment.setStatus(dto.getStatus() != null ? dto.getStatus() : ShipmentStatus.PREPARING);
        shipment.setRiskScore(dto.getRiskScore());

        order.setStatus(OrderStatus.SHIPPED);
        purchaseOrderRepository.save(order);

        return toDTO(shipmentRepository.save(shipment));
    }

    @Override
    public ShipmentDTO getById(Long id) {
        return toDTO(shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id)));
    }

    @Override
    public List<ShipmentDTO> getAll() {
        return shipmentRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ShipmentDTO update(Long id, ShipmentDTO dto) {
        Shipment existing = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        existing.setTrackingNumber(dto.getTrackingNumber());
        existing.setCarrier(dto.getCarrier());
        existing.setShippedDate(dto.getShippedDate());
        existing.setActualDeliveryDate(dto.getActualDeliveryDate());
        existing.setStatus(dto.getStatus());
        existing.setRiskScore(dto.getRiskScore());

        if (dto.getStatus() == ShipmentStatus.DELIVERED) {
            PurchaseOrder order = existing.getPurchaseOrder();
            order.setStatus(OrderStatus.DELIVERED);
            purchaseOrderRepository.save(order);
        }

        return toDTO(shipmentRepository.save(existing));
    }

    private ShipmentDTO toDTO(Shipment shipment) {
        ShipmentDTO dto = new ShipmentDTO();
        dto.setId(shipment.getId());
        dto.setPurchaseOrderId(shipment.getPurchaseOrder().getId());
        dto.setTrackingNumber(shipment.getTrackingNumber());
        dto.setCarrier(shipment.getCarrier());
        dto.setShippedDate(shipment.getShippedDate());
        dto.setActualDeliveryDate(shipment.getActualDeliveryDate());
        dto.setStatus(shipment.getStatus());
        dto.setRiskScore(shipment.getRiskScore());
        return dto;
    }
}
