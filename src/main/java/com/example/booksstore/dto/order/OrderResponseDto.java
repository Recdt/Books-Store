package com.example.booksstore.dto.order;

import com.example.booksstore.dto.orderitem.OrderItemResponseDto;
import com.example.booksstore.models.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private Order.Status status;
}
