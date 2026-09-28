package com.afreen.supplychain_iq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, nullable = false)
    private String sku;

    private String description;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock = 0;

    @Column(name = "reorder_threshold", nullable = false)
    private Integer reorderThreshold = 10;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
}