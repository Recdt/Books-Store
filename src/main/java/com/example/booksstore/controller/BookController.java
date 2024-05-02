package com.example.booksstore.controller;

import com.example.booksstore.dto.BookDto;
import com.example.booksstore.dto.BookRequestDto;
import com.example.booksstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get all books in shop",
            description = "Get a list of all available books")
    @GetMapping
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Get book by id", description = "Gets a book from db")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @Operation(summary = "Create a book",
            description = "Create a book with auto generated ID")
    @PostMapping
    public BookDto createBook(@RequestBody @Valid BookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @Operation(summary = "Updates a book", description = "Updates a book by ID")
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid BookRequestDto bookDto) {
        return bookService.updateById(id, bookDto);
    }

    @Operation(summary = "Deletes a book", description = "Deletes a book by ID(soft delete)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
