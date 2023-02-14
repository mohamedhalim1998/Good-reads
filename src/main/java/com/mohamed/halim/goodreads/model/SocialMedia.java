package com.mohamed.halim.goodreads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialMedia {
    private SocialMediaType type;
    private String url;
}
