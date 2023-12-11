package com.oreilly.shopping.services;

// Traditional three-layer architecture for Java apps:
// - Presentation layer (controllers and views)
// - Service layer (business logic and transaction boundaries)
// - Persistence layer (convert entities to table rows and back)
// DB

import com.oreilly.shopping.dao.BookRepository;
import com.oreilly.shopping.entities.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void initializeDatabase() {
        // Don't need this (initialization in src/main/resources/data.sql)
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }


    @Transactional(readOnly = true)
    public long countBooks() {
        return bookRepository.count();
    }
}
