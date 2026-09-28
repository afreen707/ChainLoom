package com.afreen.supplychain_iq.dto;

import com.afreen.supplychain_iq.enums.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PurchaseOrderDTO {
    private Long id;
    private String orderNumber;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private LocalDate orderDate;

    @NotNull(message = "Expected delivery date is required")
    @FutureOrPresent(message = "Expected delivery date cannot be in the past")
    private LocalDate expectedDeliveryDate;

    private OrderStatus status;
}