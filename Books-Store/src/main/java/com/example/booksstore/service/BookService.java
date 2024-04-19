package com.example.booksstore.service;

import com.example.booksstore.models.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
