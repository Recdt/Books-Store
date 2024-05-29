package com.example.booksstore.service.impl;

import com.example.booksstore.dto.orderitem.OrderItemResponseDto;
import com.example.booksstore.exceptions.NoSuchOrderException;
import com.example.booksstore.exceptions.NoSuchOrderItemException;
import com.example.booksstore.mappers.OrderItemMapper;
import com.example.booksstore.models.Order;
import com.example.booksstore.models.OrderItem;
import com.example.booksstore.models.User;
import com.example.booksstore.repository.OrderItemRepository;
import com.example.booksstore.repository.OrderRepository;
import com.example.booksstore.service.OrderItemService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public List<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId, User user) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new NoSuchOrderException("Can't find order. Order id "
                        + orderId + "does not exist"));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new NoSuchOrderException("Can't find order, it does not belong to you.");
        }
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void saveOrderItems(Set<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }

    @Transactional
    @Override
    public OrderItemResponseDto getOrderItemById(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndId(orderId, itemId)
                .orElseThrow(() -> new NoSuchOrderItemException("Can't find item with id "
                        + itemId + " in order your order."));
        return orderItemMapper.toDto(orderItem);
    }
}
