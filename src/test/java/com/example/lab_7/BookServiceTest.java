package com.example.lab_7;

import com.example.lab_7.dto.*;
import com.example.lab_7.entity.Book;
import com.example.lab_7.repository.BookRepository;
import com.example.lab_7.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository; // Фейковий репозиторій

    @InjectMocks
    private BookService bookService;

    private Book sampleBook;
    private BookDto sampleDto;

    @BeforeEach
    void setUp() {
        int currentYear = Year.now().getValue();
        sampleBook = new Book(
                1L,
                "Колонія",
                "Макс Кідрук",
                currentYear,
                "Бородатий Тамарин",
                "Наукова фантастика",
                904,
                "Роман про колонізацію Марса",
                35000
        );

        sampleDto = new BookDto(
                1L,
                "Колонія",
                "Макс Кідрук",
                currentYear,
                "Бородатий Тамарин",
                "Наукова фантастика",
                904,
                "Роман про колонізацію Марса",
                35000
        );
    }

    @Test
    void getAllBooks_ShouldReturnListOfDtos() {
        when(bookRepository.findAll()).thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Колонія", result.get(0).title());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_WhenExists_ShouldReturnDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        BookDto result = bookService.getBookById(1L);

        assertEquals("Макс Кідрук", result.author());
    }

    @Test
    void getBookById_WhenNotFound_ShouldThrowException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookById(99L));
    }

    @Test
    void addBook_ShouldSaveAndReturnDto() {
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

        BookDto saved = bookService.addBook(sampleDto);

        assertNotNull(saved);
        assertEquals("Колонія", saved.title());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBook_WhenExists_ShouldUpdateAndReturnDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

        BookDto updated = bookService.updateBook(1L, sampleDto);

        assertEquals("Колонія", updated.title());
        verify(bookRepository).save(sampleBook);
    }

    @Test
    void deleteBook_WhenExists_ShouldCallDelete() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    // --- Тести 9 пошукових запитів ---

    @Test
    void findByTitle_ShouldReturnMatchingBooks() {
        when(bookRepository.findByTitleContainingIgnoreCase("Колон")).thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.findByTitle("Колон");

        assertEquals(1, result.size());
        assertEquals("Колонія", result.get(0).title());
    }

    @Test
    void findByAuthor_ShouldReturnMatchingBooks() {
        when(bookRepository.findByAuthorContainingIgnoreCase("Кідрук")).thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.findByAuthor("Кідрук");

        assertEquals(1, result.size());
    }

    @Test
    void findByPages_ShouldReturnMatchingBooks() {
        when(bookRepository.findByPages(904)).thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.findByPages(904);

        assertEquals(1, result.size());
        assertEquals(904, result.get(0).pages());
    }

    @Test
    void findFirstByAuthor_ShouldReturnBookTitleDto() {
        when(bookRepository.findFirstByAuthorIgnoreCaseOrderByPublicationYearDesc("Макс Кідрук"))
                .thenReturn(Optional.of(sampleBook));

        BookTitleDto result = bookService.findFirstByAuthor("Макс Кідрук");

        assertEquals("Колонія", result.title());
        assertEquals(sampleBook.getPublicationYear(), result.publicationYear());
    }

    @Test
    void findByPublisher_ShouldReturnBookTitleAuthorDtoForCurrentYear() {
        int currentYear = Year.now().getValue();
        when(bookRepository.findByPublisherIgnoreCaseAndPublicationYear("Бородатий Тамарин", currentYear))
                .thenReturn(List.of(sampleBook));

        List<BookTitleAuthorDto> result = bookService.findByPublisher("Бородатий Тамарин");

        assertEquals(1, result.size());
        assertEquals("Колонія", result.get(0).title());
        assertEquals("Макс Кідрук", result.get(0).author());
    }

    @Test
    void findByPublicationYear_ShouldReturnMatchingBooks() {
        int currentYear = Year.now().getValue();
        when(bookRepository.findByPublicationYear(currentYear)).thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.findByPublicationYear(currentYear);

        assertEquals(1, result.size());
    }

    @Test
    void findByGenre_ShouldReturnMatchingBooks() {
        int currentYear = Year.now().getValue();
        when(bookRepository.findByGenreIgnoreCaseAndAuthorContainingIgnoreCaseAndPublicationYear(
                "Наукова фантастика", "Кідрук", currentYear))
                .thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.findByGenre("Наукова фантастика", "Кідрук", currentYear);

        assertEquals(1, result.size());
    }

    @Test
    void findBestsellers_ShouldReturnBestsellerBookDtos() {
        when(bookRepository.findByCirculationGreaterThanEqual(10000)).thenReturn(List.of(sampleBook));

        List<BestsellerBookDto> result = bookService.findBestsellers();

        assertEquals(1, result.size());
        assertEquals("Колонія", result.get(0).title());
        assertEquals("Наукова фантастика", result.get(0).genre());
    }

    @Test
    void findByDescription_ShouldReturnMatchingBooks() {
        when(bookRepository.findByDescriptionContainingIgnoreCase("Марса")).thenReturn(List.of(sampleBook));

        List<BookDto> result = bookService.findByDescription("Марса");

        assertEquals(1, result.size());
    }
}