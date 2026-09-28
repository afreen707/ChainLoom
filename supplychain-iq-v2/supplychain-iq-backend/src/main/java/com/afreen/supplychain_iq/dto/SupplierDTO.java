package com.afreen.supplychain_iq.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SupplierDTO {
    private Long id;

    @NotBlank(message = "Supplier name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    private String phone;
    private String address;
    private BigDecimal reliabilityScore;
}