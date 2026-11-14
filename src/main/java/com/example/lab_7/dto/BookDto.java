package com.example.lab_7.dto;

public record BookDto(
        Long id,
        String title,
        String author,
        Integer publicationYear,
        String publisher,
        String genre,
        Integer pages,
        String description,
        Integer circulation
) {
}