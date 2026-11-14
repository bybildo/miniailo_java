package com.example.lab_7.repository;

import com.example.lab_7.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Пошук за назвою книги без урахування регістру
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Пошук за ПІБ автора
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Пошук за кількістю сторінок
    List<Book> findByPages(Integer pages);

    // Остання видана книга заданого автора
    Optional<Book> findFirstByAuthorIgnoreCaseOrderByPublicationYearDesc(String author);

    // Книги вказаного видавництва за поточний рік
    List<Book> findByPublisherIgnoreCaseAndPublicationYear(String publisher, Integer publicationYear);

    // Пошук за роком випуску
    List<Book> findByPublicationYear(Integer publicationYear);

    // Пошук одночасно за жанром, автором та роком видання
    List<Book> findByGenreIgnoreCaseAndAuthorContainingIgnoreCaseAndPublicationYear(
            String genre, String author, Integer publicationYear
    );

    // Книги, у яких тираж більший або дорівнює заданому порогу
    List<Book> findByCirculationGreaterThanEqual(Integer minCirculation);

    // Пошук слова в короткому описі книги
    List<Book> findByDescriptionContainingIgnoreCase(String keyword);
}