package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.BookList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ListDto {
    private Long id;
    private String name;
    private int bookCount;
    private String books;

    public static BookList toList(ListDto listDto) {
        return BookList.builder().id(listDto.id).name(listDto.name).build();
    }

    public static ListDto fromList(BookList bookList) {
        return ListDto.builder().id(bookList.getId()).name(bookList.getName()).books(
                hostname + "lists/" + bookList.getId() + "/books"
        ).build();
    }
}
