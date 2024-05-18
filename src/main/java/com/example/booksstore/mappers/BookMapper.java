package com.example.booksstore.mappers;

import com.example.booksstore.config.MapperConfig;
import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import com.example.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.booksstore.models.Book;
import com.example.booksstore.models.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(BookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.setCategoryIds(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }
}
