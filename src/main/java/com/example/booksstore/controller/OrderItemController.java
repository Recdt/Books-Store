package com.example.booksstore.controller;

import com.example.booksstore.dto.orderitem.OrderItemResponseDto;
import com.example.booksstore.models.User;
import com.example.booksstore.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Gets all items by orderId id",
            description = "Gets an items from order")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getAllItemsFromOrder(@PathVariable Long orderId,
                                                           Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getAllOrderItemsByOrderId(orderId, user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Gets item from order by ids",
            description = "Gets an item from order")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getItemFromOrderByIds(@PathVariable Long orderId,
                                                      @PathVariable Long itemId) {
        return orderItemService.getOrderItemById(orderId, itemId);
    }
}
