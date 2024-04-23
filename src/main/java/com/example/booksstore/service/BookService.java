package com.example.booksstore.service;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto getById(Long id);

    List<BookDto> findAll();
}
