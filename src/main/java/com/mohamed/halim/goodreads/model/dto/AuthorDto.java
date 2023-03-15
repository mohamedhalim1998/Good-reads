package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class AuthorDto {
    private Long id;
    private String name;
    private String website;
    private String bio;
    private String books;

    public static AuthorDto fromAuthor(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .website(author.getWebsite())
                .bio(author.getBio())
                .books(hostname + "authors/" + author.getId() + "/books")
                .build();
    }
}
