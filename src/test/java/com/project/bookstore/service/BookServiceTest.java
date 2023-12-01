package com.project.bookstore.service;

import com.project.bookstore.dto.Book;
import com.project.bookstore.dto.User;
import com.project.bookstore.entity.BooksEntity;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.response.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(BookService.class)
@AutoConfigureMockMvc
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @Test
    void addBooks() {
        List<BooksEntity> booksEntities = new ArrayList<>();
        booksEntities.add(new BooksEntity("123", "Abc", "Abc", "Some author", "2023", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));
        Mockito.when(bookRepository.saveAll(booksEntities)).thenReturn(booksEntities);
        assertDoesNotThrow(()->bookService.addBooks(booksEntities));
    }

    @Test
    void addBooksThrowsError() {
        List<BooksEntity> booksEntities = new ArrayList<>();
        booksEntities.add(new BooksEntity("123", "Abc", "Abc", "Some author", "2023", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));
        Mockito.when(bookRepository.saveAll(booksEntities)).thenThrow(RuntimeException.class);
        assertThrows(Exception.class, ()->bookService.addBooks(booksEntities));
    }

    @Test
    void getBooksBySearchText() {
        List<BooksEntity> booksEntities = new ArrayList<>();
        booksEntities.add(new BooksEntity("123", "test", "Abc", "Some author", "2023", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));
        booksEntities.add(new BooksEntity("456", "Vineeth Bio", "Abc", "Some test", "2023", "MImageUrl", "LImageUrl", 2.22, 1, 2.20));
        Mockito.when(bookRepository.findByNameContainingOrAuthorContaining("test", "test"))
                .thenReturn(booksEntities);

        List<Book> serviceResponse = bookService.searchBooks("test");
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book("123","Some author","test","MImageUrl","LImageUrl",2.22,1,"2023","Abc",2.20));
        expectedBooks.add(new Book("456","Some test","Vineeth Bio","MImageUrl","LImageUrl",2.22,1,"2023","Abc",2.20));

        Assertions.assertThat(serviceResponse).isEqualTo(expectedBooks);
    }
}