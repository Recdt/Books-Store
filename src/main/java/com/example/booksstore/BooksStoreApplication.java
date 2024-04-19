package com.example.booksstore;

import com.example.booksstore.models.Book;
import com.example.booksstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooksStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BooksStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("a");
                book.setIsbn("111");
                book.setPrice(BigDecimal.ONE);
                book.setDescription("");
                book.setCoverImage("kkk");
                book.setTitle("Title");

                System.out.println(bookService.save(book));
            }
        };
    }
}
