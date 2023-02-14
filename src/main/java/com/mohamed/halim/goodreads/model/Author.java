package com.mohamed.halim.goodreads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private String name;
    private Date birthdate;
    private String website;
    private List<SocialMedia> socialMedia;
    private List<Genre> genres;
    private String bio;
    private List<Book> books;
}
