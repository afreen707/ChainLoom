package com.afreen.supplychain_iq.dto;

import com.afreen.supplychain_iq.enums.ShipmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShipmentDTO {
    private Long id;

    @NotNull(message = "Purchase order ID is required")
    private Long purchaseOrderId;

    private String trackingNumber;
    private String carrier;
    private LocalDate shippedDate;
    private LocalDate actualDeliveryDate;
    private ShipmentStatus status;
    private BigDecimal riskScore;
}