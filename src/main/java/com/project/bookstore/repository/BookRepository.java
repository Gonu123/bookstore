package com.project.bookstore.repository;

import com.project.bookstore.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BooksEntity, String> {
    List<BooksEntity> findByNameContainingOrAuthorContaining(String nameQuery, String authorQuery);

    BooksEntity getByIsbn(String isbn);
}
