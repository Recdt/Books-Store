package com.example.booksstore.service.impl;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.CreateBookRequestDto;
import com.example.booksstore.mappers.BookMapper;
import com.example.booksstore.models.Book;
import com.example.booksstore.repository.BookRepository;
import com.example.booksstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        Book model = bookMapper.toModel(book);
        return bookMapper.toDto(bookRepository.save(model));
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.toDto(bookRepository.getById(id));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
