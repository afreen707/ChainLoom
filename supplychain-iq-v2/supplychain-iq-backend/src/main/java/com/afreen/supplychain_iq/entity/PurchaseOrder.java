package com.afreen.supplychain_iq.entity;

import com.afreen.supplychain_iq.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();

    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToOne(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Shipment shipment;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}