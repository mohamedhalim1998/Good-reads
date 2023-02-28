package com.mohamed.halim.goodreads.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity



public class Profile implements UserDetails {
    @Id
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
