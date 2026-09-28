package com.afreen.supplychain_iq.repository;

import com.afreen.supplychain_iq.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByEmail(String email);
}
