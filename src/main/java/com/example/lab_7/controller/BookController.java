package com.example.lab_7.controller;

import com.example.lab_7.dto.*;
import com.example.lab_7.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/search/title")
    public List<BookDto> findByTitle(@RequestParam String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping("/search/author")
    public List<BookDto> findByAuthor(@RequestParam String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/search/pages")
    public List<BookDto> findByPages(@RequestParam Integer pages) {
        return bookService.findByPages(pages);
    }

    @GetMapping("/search/latest-by-author")
    public BookTitleDto findFirstByAuthor(@RequestParam String author) {
        return bookService.findFirstByAuthor(author);
    }

    @GetMapping("/search/publisher-current-year")
    public List<BookTitleAuthorDto> findByPublisher(@RequestParam String publisher) {
        return bookService.findByPublisher(publisher);
    }

    @GetMapping("/search/year")
    public List<BookDto> findByPublicationYear(@RequestParam Integer year) {
        return bookService.findByPublicationYear(year);
    }

    @GetMapping("/search/filter")
    public List<BookDto> findByGenre(@RequestParam String genre, @RequestParam String author, @RequestParam Integer year)
    {
        return bookService.findByGenre(genre, author, year);
    }

    @GetMapping("/search/bestsellers")
    public List<BestsellerBookDto> findBestsellers() {
        return bookService.findBestsellers();
    }

    @GetMapping("/search/description")
    public List<BookDto> findByDescription(@RequestParam String keyword) {
        return bookService.findByDescription(keyword);
    }
}