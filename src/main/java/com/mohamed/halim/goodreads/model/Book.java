package com.mohamed.halim.goodreads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private String subName;
    private List<Author> authors;
    private double avgRating;
    private List<Review> reviews;
    private List<Genre> genres;
    private Series series;
    private String language;
    private String ISBN;
    private Publisher publisher;
}
