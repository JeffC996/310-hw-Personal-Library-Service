package com.oreilly.shopping.dao;

import com.oreilly.shopping.entities.Book;
import com.oreilly.shopping.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        List<Book> booksToSave = List.of(
                new Book("Book one", "Author One", "ISBN One", LocalDate.of(2023, Month.JANUARY, 1)),
                new Book("Book two", "Author Two", "ISBN Two", LocalDate.of(2023, Month.JANUARY, 2)),
                new Book("Book three", "Author Three", "ISBN Three", LocalDate.of(2023, Month.JANUARY, 3))
        );

        repository.saveAll(booksToSave);
    }

    @Test
    void countBooks() {
        assertEquals(3, repository.count());
    }

    /*
    @Test
    void findById() {
        assertTrue(repository.findById(1L)).isPresent();
    }
    */

    @Test
    void findAll() {
        List<Book> books = repository.findAll();
        assertEquals(3, books.size());
    }

    @Test
    void insertBook() {
        Book b1 = new Book();
        b1.setTitle("Some Title");
        b1.setAuthor("Some Author");
        b1.setIsbn("Some ISBN");
        b1.setPublicationDate(LocalDate.now());
        repository.save(b1);
        assertAll(
                () -> assertNotNull(b1.getId()),
                () -> assertEquals(4, repository.count())
        );
    }

    @Test
    void deleteAllInBatch() {
        repository.deleteAllInBatch();
        assertEquals(0, repository.count());
    }

    @Test
    void deleteBook() {
        repository.deleteById(1L);
        assertEquals(3, repository.count());
    }

    @Test
    public void testFindById() {
        Book book = new Book("Title", "Author", "ISBN", LocalDate.now());
        book = repository.save(book);

        Optional<Book> foundBook = repository.findById(book.getId());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get()).isEqualToComparingFieldByField(book);
    }

    @Test
    void deleteAllBooks() {
        repository.deleteAll();
        assertEquals(0, repository.count());
    }

}