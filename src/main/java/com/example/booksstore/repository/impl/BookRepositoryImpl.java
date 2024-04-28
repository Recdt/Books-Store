package com.example.booksstore.repository.impl;

import com.example.booksstore.exceptions.EntityNotFoundException;
import com.example.booksstore.models.Book;
import com.example.booksstore.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new EntityNotFoundException("Can't save book " + book.getTitle(), e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            Book book = entityManager.find(Book.class, id);
            transaction.commit();
            return Optional.of(book);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new EntityNotFoundException("Can't get book by id:" + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            String ql = "FROM Book";
            TypedQuery<Book> query = entityManager.createQuery(ql, Book.class);
            List<Book> resultList = query.getResultList();
            transaction.commit();
            return resultList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new EntityNotFoundException("Can't get all books from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
