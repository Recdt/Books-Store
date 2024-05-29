package com.example.booksstore.service;

import com.example.booksstore.dto.order.OrderRequestDto;
import com.example.booksstore.dto.order.OrderResponseDto;
import com.example.booksstore.dto.order.UpdateOrderRequestDto;
import com.example.booksstore.models.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto save(OrderRequestDto requestDto, User user);

    List<OrderResponseDto> getAllOrders(Pageable pageable, User user);

    void updateOrder(UpdateOrderRequestDto requestDto, Long id);
}
