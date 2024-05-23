package com.example.booksstore.repository;

import com.example.booksstore.models.Order;
import com.example.booksstore.models.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(Pageable pageable, User user);
}
