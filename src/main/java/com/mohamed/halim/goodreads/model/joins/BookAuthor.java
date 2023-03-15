package com.mohamed.halim.goodreads.model.joins;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class BookAuthor {
    @Id
    private Long id;
    private Long authorId;
    private String bookId;
}
