package com.afreen.supplychain_iq.repository;

import com.afreen.supplychain_iq.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByPurchaseOrderId(Long purchaseOrderId);
}
