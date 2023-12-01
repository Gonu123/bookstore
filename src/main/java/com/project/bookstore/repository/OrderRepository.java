package com.project.bookstore.repository;

import com.project.bookstore.entity.BooksEntity;
import com.project.bookstore.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByUserIdOrderByOrderDateDesc(String username);
}
