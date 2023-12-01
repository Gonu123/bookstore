package com.project.bookstore.repository;

import com.project.bookstore.entity.BooksEntity;
import com.project.bookstore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUsername(String username);

    UserEntity getByUsername(String username);
}
