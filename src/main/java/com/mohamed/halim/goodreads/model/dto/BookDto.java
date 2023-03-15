package com.mohamed.halim.goodreads.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Publisher;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookDto {
    private String ISBN;
    private String name;
    private String avgRating;
    private String subName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> authorIds;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long publisherId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AuthorDto> authors;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PublisherDto publisherDto;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bookCover;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String reviews;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lists;

    public static BookDto fromBook(Book book) {
        return BookDto.builder()
                .ISBN(book.getISBN())
                .name(book.getName())
                .subName(book.getSubName())
                .bookCover(hostname + "img/" + book.getBookCover())
                .lists(hostname + "books/" + book.getISBN() + "/lists")
                .reviews(hostname + "books/" + book.getISBN() + "/reviews")
                .build();

    }

    public static Book toBook(BookDto book) {
        return Book.builder()
                .ISBN(book.getISBN())
                .name(book.getName())
                .subName(book.getSubName())
                .publisherId(book.publisherId)
                .bookCover(hostname + "img/" + book.getBookCover())
                .build();
    }
}
