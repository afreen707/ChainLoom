package com.afreen.supplychain_iq.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String description;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @NotNull
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer currentStock;

    @NotNull
    @Min(value = 0)
    private Integer reorderThreshold;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;
}