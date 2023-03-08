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
public class SocialMedia {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private SocialMediaType type;
    private String url;
    private Long authorId;
}
