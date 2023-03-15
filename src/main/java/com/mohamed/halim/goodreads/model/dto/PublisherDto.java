package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PublisherDto {
    private Long id;
    private String name;
    private String books;

    public static PublisherDto fromPublisher(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .books(hostname + "publishers/" + publisher.getId() + "/books")
                .build();
    }


}
