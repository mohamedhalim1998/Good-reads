package com.mohamed.halim.goodreads.model.dto;


import com.mohamed.halim.goodreads.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookDto {
    private String ISBN;
    private String name;

    public static BookDto fromBook(Book book) {
        return BookDto.builder()
                .ISBN(book.getISBN())
                .name(book.getName())
                .build();

    }
}
