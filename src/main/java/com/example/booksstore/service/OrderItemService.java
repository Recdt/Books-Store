package com.example.booksstore.service;

import com.example.booksstore.dto.orderitem.OrderItemResponseDto;
import com.example.booksstore.models.OrderItem;
import com.example.booksstore.models.User;
import java.util.List;
import java.util.Set;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId, User user);

    OrderItemResponseDto getOrderItemById(Long orderId, Long itemId);

    void saveOrderItems(Set<OrderItem> orderItems);
}
