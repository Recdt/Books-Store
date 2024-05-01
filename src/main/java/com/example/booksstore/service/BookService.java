package com.example.booksstore.service;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(BookRequestDto book);

    BookDto getById(Long id);

    List<BookDto> findAll();

    BookDto updateById(Long id, BookRequestDto book);

    void deleteById(Long id);
}
