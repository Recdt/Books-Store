package com.example.booksstore.service.impl;

import com.example.booksstore.dto.cartitem.CartItemRequestDto;
import com.example.booksstore.dto.cartitem.CartItemResponseDto;
import com.example.booksstore.dto.cartitem.UpdateCartItemRequestDto;
import com.example.booksstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.mappers.CartItemMapper;
import com.example.booksstore.mappers.ShoppingCartMapper;
import com.example.booksstore.models.CartItem;
import com.example.booksstore.models.ShoppingCart;
import com.example.booksstore.models.User;
import com.example.booksstore.repository.ShoppingCartRepository;
import com.example.booksstore.service.CartItemService;
import com.example.booksstore.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCartResponseDto getByUserId(Long id) {
        return shoppingCartMapper.toDto(getShoppingCart(id));
    }

    @Override
    @Transactional
    public CartItemResponseDto addToCart(CartItemRequestDto requestDto, Long id) {
        ShoppingCart shoppingCart = getShoppingCart(id);
        CartItem cartItem = cartItemService.addToCart(requestDto, shoppingCart);
        shoppingCart.getCartItems().add(cartItem);

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCart(
            UpdateCartItemRequestDto requestDto,
            Long cartItemId,
            Long id) {
        ShoppingCart shoppingCart = getShoppingCart(id);
        CartItem cartItem = cartItemService.update(cartItemId, requestDto);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId, Long id) {
        ShoppingCart shoppingCart = getShoppingCart(id);
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item with id: " + cartItemId));
        cartItemService.delete(cartItem.getId());
    }

    @Override
    public void createShoppingCartForUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getShoppingCart(Long id) {
        return shoppingCartRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find shopping cart. User id: " + id));
    }
}
