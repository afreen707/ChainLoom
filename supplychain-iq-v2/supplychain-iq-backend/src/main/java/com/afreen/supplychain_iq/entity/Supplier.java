package com.afreen.supplychain_iq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String address;

    @Column(name = "reliability_score", precision = 3, scale = 2)
    private BigDecimal reliabilityScore = BigDecimal.valueOf(0.75);

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "document_key")
    private String documentKey;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<PurchaseOrder> purchaseOrders;
}