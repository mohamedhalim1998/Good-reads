package com.mohamed.halim.goodreads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    private String name;
    private List<Book> books;
}
