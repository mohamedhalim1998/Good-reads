package com.mohamed.halim.goodreads.model.joins;

import com.mohamed.halim.goodreads.model.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String bookId;
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
