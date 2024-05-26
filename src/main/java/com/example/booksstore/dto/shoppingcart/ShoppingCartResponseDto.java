package com.example.booksstore.dto.shoppingcart;

import com.example.booksstore.models.CartItem;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
