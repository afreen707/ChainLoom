package com.afreen.supplychain_iq.entity;

import com.afreen.supplychain_iq.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false, unique = true)
    private PurchaseOrder purchaseOrder;

    @Column(name = "tracking_number")
    private String trackingNumber;

    private String carrier;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status = ShipmentStatus.PREPARING;

    @Column(name = "risk_score", precision = 4, scale = 3)
    private BigDecimal riskScore;
}