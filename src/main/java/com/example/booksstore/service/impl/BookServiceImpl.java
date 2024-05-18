package com.example.booksstore.service.impl;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import com.example.booksstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.exceptions.IsbnAlreadyExistsException;
import com.example.booksstore.mappers.BookMapper;
import com.example.booksstore.models.Book;
import com.example.booksstore.repository.BookRepository;
import com.example.booksstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(BookRequestDto book) {
        checkIsbnUniqueness(book.getIsbn());
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
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto updateById(Long id, BookRequestDto book) {
        checkBookAvailability(id);
        checkUniqueIsbnForUpdate(book.getIsbn(), id);
        Book updatedBook = bookMapper.toModel(book);
        updatedBook.setId(id);
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public void deleteById(Long id) {
        checkBookAvailability(id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategoryId(id, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private void checkBookAvailability(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book by id " + id);
        }
    }

    private void checkIsbnUniqueness(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new IsbnAlreadyExistsException("The isbn: " + isbn + " must be uniq.");
        }
    }

    private void checkUniqueIsbnForUpdate(String isbn, Long id) {
        if (bookRepository.existsByIsbnAndIdNot(isbn, id)) {
            throw new IsbnAlreadyExistsException("The isbn: " + isbn + " must be uniq.");
        }
    }

}
