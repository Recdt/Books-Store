package com.example.booksstore.service.impl;

import com.example.booksstore.dto.order.OrderRequestDto;
import com.example.booksstore.dto.order.OrderResponseDto;
import com.example.booksstore.dto.order.UpdateOrderRequestDto;
import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.mappers.OrderMapper;
import com.example.booksstore.models.CartItem;
import com.example.booksstore.models.Order;
import com.example.booksstore.models.ShoppingCart;
import com.example.booksstore.models.User;
import com.example.booksstore.repository.OrderRepository;
import com.example.booksstore.repository.ShoppingCartRepository;
import com.example.booksstore.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponseDto save(OrderRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user " + user.getId()));

        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Can't find cart items for user " + user.getId());
        }
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
        Order order = orderMapper.toModel(requestDto);
        order.setUser(user);
        order.setStatus(Order.Status.COMPLETED);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(calculateTotal(order));
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    @Override
    public List<OrderResponseDto> getAllOrders(Pageable pageable, User user) {
        return orderRepository.findAllByUser(pageable, user).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void updateOrder(UpdateOrderRequestDto requestDto, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with id " + id));
        order.setStatus(requestDto.getStatus());
        orderRepository.save(order);
    }

    private BigDecimal calculateTotal(Order order) {
        return order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
