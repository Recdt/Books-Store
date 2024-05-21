package com.example.booksstore.service;

import com.example.booksstore.dto.cartitem.CartItemRequestDto;
import com.example.booksstore.dto.cartitem.UpdateCartItemRequestDto;
import com.example.booksstore.models.CartItem;
import com.example.booksstore.models.ShoppingCart;

public interface CartItemService {
    CartItem addToCart(CartItemRequestDto requestDto, ShoppingCart shoppingCart);

    CartItem update(Long id, UpdateCartItemRequestDto requestDto);

    void delete(Long id);
}
