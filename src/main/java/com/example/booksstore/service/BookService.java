package com.example.booksstore.service;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(BookRequestDto book);

    BookDto getById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto updateById(Long id, BookRequestDto book);

    void deleteById(Long id);
}
