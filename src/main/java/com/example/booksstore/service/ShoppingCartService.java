package com.example.booksstore.service;

import com.example.booksstore.dto.cartitem.CartItemRequestDto;
import com.example.booksstore.dto.cartitem.CartItemResponseDto;
import com.example.booksstore.dto.cartitem.UpdateCartItemRequestDto;
import com.example.booksstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.booksstore.models.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(Long userId);

    CartItemResponseDto addToCart(CartItemRequestDto requestDto, Long cartId);

    CartItemResponseDto updateCart(UpdateCartItemRequestDto requestDto,
                                   Long cartItemId, Long cartId);

    void deleteCartItem(Long cartItemId, Long cartId);

    void createShoppingCartForUser(User user);
}
