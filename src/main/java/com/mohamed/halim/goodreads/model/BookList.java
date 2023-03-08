package com.mohamed.halim.goodreads.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BookList {
    @Id
    private Long id;
    private String name;
}
