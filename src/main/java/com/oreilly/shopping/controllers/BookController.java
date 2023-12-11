package com.oreilly.shopping.controllers;

import com.oreilly.shopping.entities.Book;
import com.oreilly.shopping.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    //get all book
    @GetMapping
    public List<Book> getAllBooks() {
        return service.findAllBooks();
    }

    //add a new book
    @PostMapping
    public ResponseEntity<Book>  createBook(@RequestBody Book book1) {
        return ResponseEntity.of(Optional.of(service.createBook(book1)));
    }

    //delete a book by ID
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return service.findBookById(id)
                .map(b -> {
                    service.deleteBook(b);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }




    //get book by id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return ResponseEntity.of(service.findBookById(id));
    }

    //count books
    @GetMapping("count")
    public long getBookCount() {
        return service.countBooks();
    }

}
