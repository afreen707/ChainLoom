package com.afreen.supplychain_iq.repository;

import com.afreen.supplychain_iq.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySupplierId(Long supplierId);
    List<Product> findByCurrentStockLessThanEqual(Integer threshold);
    boolean existsBySku(String sku);
}
