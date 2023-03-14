package com.mohamed.halim.goodreads.model.joins;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfBook {
    @Id
    private Long id;
    private String bookId;
    private Long shelfId;
}
