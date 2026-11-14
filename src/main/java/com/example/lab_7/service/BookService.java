package com.example.lab_7.service;

import com.example.lab_7.dto.*;
import com.example.lab_7.entity.Book;
import com.example.lab_7.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private static final int BESTSELLER_THRESHOLD = 10000;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toDto).toList();
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книгу з id " + id + " не знайдено"));
        return toDto(book);
    }

    public BookDto addBook(BookDto dto) {
        Book book = new Book();
        updateEntityFromDto(book, dto);
        return toDto(bookRepository.save(book));
    }

    public BookDto updateBook(Long id, BookDto dto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книгу з id " + id + " не знайдено"));
        updateEntityFromDto(existingBook, dto);
        return toDto(bookRepository.save(existingBook));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id))
            throw new RuntimeException("Книгу з id " + id + " не знайдено");
        bookRepository.deleteById(id);
    }

    public List<BookDto> findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(this::toDto).toList();
    }

    public List<BookDto> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream().map(this::toDto).toList();
    }

    public List<BookDto> findByPages(Integer pages) {
        return bookRepository.findByPages(pages).stream().map(this::toDto).toList();
    }

    public BookTitleDto findFirstByAuthor(String author) {
        return bookRepository.findFirstByAuthorIgnoreCaseOrderByPublicationYearDesc(author)
                .map(book -> new BookTitleDto(book.getTitle(), book.getPublicationYear()))
                .orElseThrow(() -> new RuntimeException("Книг автора '" + author + "' не знайдено"));
    }

    public List<BookTitleAuthorDto> findByPublisher(String publisher) {
        int currentYear = Year.now().getValue();
        return bookRepository.findByPublisherIgnoreCaseAndPublicationYear(publisher, currentYear).stream()
                .map(book -> new BookTitleAuthorDto(book.getTitle(), book.getAuthor()))
                .toList();
    }

    public List<BookDto> findByPublicationYear(Integer year) {
        return bookRepository.findByPublicationYear(year).stream().map(this::toDto).toList();
    }

    public List<BookDto> findByGenre(String genre, String author, Integer year) {
        return bookRepository.findByGenreIgnoreCaseAndAuthorContainingIgnoreCaseAndPublicationYear(genre, author, year)
                .stream().map(this::toDto).toList();
    }

    public List<BestsellerBookDto> findBestsellers() {
        return bookRepository.findByCirculationGreaterThanEqual(BESTSELLER_THRESHOLD).stream()
                .map(book -> new BestsellerBookDto(book.getTitle(), book.getAuthor(), book.getGenre())).toList();
    }

    public List<BookDto> findByDescription(String keyword) {
        return bookRepository.findByDescriptionContainingIgnoreCase(keyword).stream().map(this::toDto).toList();
    }

    private BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getGenre(),
                book.getPages(),
                book.getDescription(),
                book.getCirculation()
        );
    }

    private void updateEntityFromDto(Book book, BookDto dto) {
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setPublicationYear(dto.publicationYear());
        book.setPublisher(dto.publisher());
        book.setGenre(dto.genre());
        book.setPages(dto.pages());
        book.setDescription(dto.description());
        book.setCirculation(dto.circulation());
    }
}