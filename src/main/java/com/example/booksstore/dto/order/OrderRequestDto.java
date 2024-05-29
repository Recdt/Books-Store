package com.example.booksstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank
    @Size(min = 5, max = 255)
    private String shippingAddress;
}
