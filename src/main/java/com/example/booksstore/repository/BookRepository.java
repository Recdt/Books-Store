package com.example.booksstore.repository;

import com.example.booksstore.models.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE "
            + "c.id = :categoryId AND b.isDeleted = false")
    List<Book> findAllByCategoryId(@Param("categoryId") Long categoryId,
                                   Pageable pageable);
}
