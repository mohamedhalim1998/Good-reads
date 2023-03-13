package com.mohamed.halim.goodreads.model.dto;


import com.mohamed.halim.goodreads.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookDto {
    private String ISBN;
    private String name;
    private String avgRate;
    private String authors;
    private String reviews;
    private String lists;
    private String book;

    public static BookDto fromBook(Book book) {
        return BookDto.builder()
                .ISBN(book.getISBN())
                .name(book.getName())
                .authors(hostname + "books/" + book.getISBN() + "/authors")
                .lists(hostname + "books/" + book.getISBN() + "/lists")
                .reviews(hostname + "books/" + book.getISBN() + "/reviews")
                .book(hostname + "books/" + book.getISBN())
                .build();

    }
}
