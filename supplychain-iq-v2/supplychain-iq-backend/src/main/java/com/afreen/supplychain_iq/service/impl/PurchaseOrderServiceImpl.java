package com.afreen.supplychain_iq.service.impl;

import com.afreen.supplychain_iq.dto.PurchaseOrderDTO;
import com.afreen.supplychain_iq.entity.Product;
import com.afreen.supplychain_iq.entity.PurchaseOrder;
import com.afreen.supplychain_iq.entity.Supplier;
import com.afreen.supplychain_iq.enums.OrderStatus;
import com.afreen.supplychain_iq.exception.ResourceNotFoundException;
import com.afreen.supplychain_iq.repository.ProductRepository;
import com.afreen.supplychain_iq.repository.PurchaseOrderRepository;
import com.afreen.supplychain_iq.repository.SupplierRepository;
import com.afreen.supplychain_iq.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public PurchaseOrderDTO create(PurchaseOrderDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));

        PurchaseOrder order = new PurchaseOrder();
        order.setOrderNumber("PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setProduct(product);
        order.setSupplier(supplier);
        order.setQuantity(dto.getQuantity());
        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : LocalDate.now());
        order.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        order.setStatus(OrderStatus.PENDING);

        return toDTO(purchaseOrderRepository.save(order));
    }

    @Override
    public PurchaseOrderDTO getById(Long id) {
        return toDTO(purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + id)));
    }

    @Override
    public List<PurchaseOrderDTO> getAll() {
        return purchaseOrderRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PurchaseOrderDTO updateStatus(Long id, String status) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with id: " + id));
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return toDTO(purchaseOrderRepository.save(order));
    }

    @Override
    public void delete(Long id) {
        if (!purchaseOrderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase order not found with id: " + id);
        }
        purchaseOrderRepository.deleteById(id);
    }

    private PurchaseOrderDTO toDTO(PurchaseOrder order) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setProductId(order.getProduct().getId());
        dto.setSupplierId(order.getSupplier().getId());
        dto.setQuantity(order.getQuantity());
        dto.setOrderDate(order.getOrderDate());
        dto.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        dto.setStatus(order.getStatus());
        return dto;
    }
}
