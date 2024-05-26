package com.example.booksstore.service.impl;

import com.example.booksstore.dto.cartitem.CartItemRequestDto;
import com.example.booksstore.dto.cartitem.UpdateCartItemRequestDto;
import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.mappers.CartItemMapper;
import com.example.booksstore.models.Book;
import com.example.booksstore.models.CartItem;
import com.example.booksstore.models.ShoppingCart;
import com.example.booksstore.repository.BookRepository;
import com.example.booksstore.repository.CartItemRepository;
import com.example.booksstore.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public CartItem addToCart(CartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book with id: " + requestDto.getBookId()));
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);

        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public CartItem update(Long id, UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = getById(id);
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getById(id);
        cartItemRepository.deleteById(id);
    }

    private CartItem getById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find a item with id " + id));
    }
}
