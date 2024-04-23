package com.example.booksstore.repository;

import com.example.booksstore.models.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    Book getById(Long id);

    List<Book> findAll();
}
