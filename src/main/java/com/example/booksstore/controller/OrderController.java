package com.example.booksstore.controller;

import com.example.booksstore.dto.order.OrderRequestDto;
import com.example.booksstore.dto.order.OrderResponseDto;
import com.example.booksstore.dto.order.UpdateOrderRequestDto;
import com.example.booksstore.models.User;
import com.example.booksstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all orders",
            description = "Get all orders of the user")
    @GetMapping
    public List<OrderResponseDto> getAllOrders(Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrders(pageable, user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Place an order",
            description = "Place an order of all the books in users shopping cart")
    @PostMapping
    public OrderResponseDto createOrder(
            @RequestBody @Valid OrderRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.save(requestDto, user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Updates order status",
            description = "Updates order status of the user")
    public void updateOrder(
            @RequestBody @Valid UpdateOrderRequestDto requestDto,
            @PathVariable Long id) {
        orderService.updateOrder(requestDto, id);
    }
}
