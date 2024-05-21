package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.cartitem.CartItemRequestDto;
import com.example.booksstore.dto.cartitem.CartItemResponseDto;
import com.example.booksstore.models.Book;
import com.example.booksstore.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book", qualifiedByName = "bookIdByBook")
    @Mapping(target = "bookTitle", source = "book", qualifiedByName = "getTitleByBook")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookByBookId")
    CartItem toModel(CartItemRequestDto cartItemRequestDto);

    @Named(value = "bookByBookId")
    default Book bookByBookId(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        return book;
    }

    @Named(value = "bookIdByBook")
    default Long bookIdByBook(Book book) {
        return book.getId();
    }

    @Named(value = "getTitleByBook")
    default String getTitleByBook(Book book) {
        return book.getTitle();
    }
}
