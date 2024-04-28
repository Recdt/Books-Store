package com.example.booksstore.service.impl;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.CreateBookRequestDto;
import com.example.booksstore.exceptions.EntityNotFoundException;
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
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't get book by ID"));
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto book) {
        if (bookRepository.existsById(id)) {
            Book updatedBook = bookMapper.toModel(book);
            updatedBook.setId(id);
            return bookMapper.toDto(bookRepository.save(updatedBook));
        }
        throw new EntityNotFoundException("Can't find book by id " + id);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
