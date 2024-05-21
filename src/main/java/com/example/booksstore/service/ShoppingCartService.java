package com.example.booksstore.service;

import com.example.booksstore.dto.cartitem.CartItemRequestDto;
import com.example.booksstore.dto.cartitem.CartItemResponseDto;
import com.example.booksstore.dto.cartitem.UpdateCartItemRequestDto;
import com.example.booksstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.booksstore.models.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(Long id);

    CartItemResponseDto addToCart(CartItemRequestDto requestDto, Long id);

    CartItemResponseDto updateCart(UpdateCartItemRequestDto requestDto,
                                   Long cartItemId, Long id);

    void deleteCartItem(Long cartItemId, Long id);

    void createShoppingCartForUser(User user);
}
