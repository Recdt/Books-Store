package com.example.booksstore.dto.order;

import com.example.booksstore.models.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderRequestDto {
    @NotNull
    private Order.Status status;
}
