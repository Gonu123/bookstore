package com.project.bookstore.repository;

import com.project.bookstore.entity.ItemsEntity;
import com.project.bookstore.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemsEntity, String> {
    List<ItemsEntity> findByIdOrderId(String userId);
}
