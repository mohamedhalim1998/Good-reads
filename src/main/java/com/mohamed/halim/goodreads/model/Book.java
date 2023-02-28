package com.mohamed.halim.goodreads.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    private String ISBN;
    private String name;
    private String subName;
    private double avgRating;
    private Long seriesId;
    private String language;
    private Long publisherId;
}
