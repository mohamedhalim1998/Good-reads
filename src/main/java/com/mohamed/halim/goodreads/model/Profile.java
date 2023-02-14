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
public class Profile {
    private String username;
    private String fullName;
    private String password;
    private Date birthdate;
    private String profilePic;
    @ToString.Exclude
    private List<Review> reviews;
    @ToString.Exclude
    private List<Book> bookRead;
    @ToString.Exclude
    private List<Shelf> shelves;
}
