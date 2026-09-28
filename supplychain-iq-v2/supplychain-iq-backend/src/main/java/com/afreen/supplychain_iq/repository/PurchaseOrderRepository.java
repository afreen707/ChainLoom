package com.afreen.supplychain_iq.repository;

import com.afreen.supplychain_iq.entity.PurchaseOrder;
import com.afreen.supplychain_iq.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findBySupplierId(Long supplierId);
    List<PurchaseOrder> findByStatus(OrderStatus status);
}
