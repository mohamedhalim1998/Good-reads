package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Shelf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ShelfDto {
    private Long id;
    private String name;
    private String books;
    private int bookCount;

    public static ShelfDto fromShelf(Shelf shelf) {
        return ShelfDto.builder()
                .id(shelf.getId())
                .name(shelf.getName())
                .books(hostname + shelf.getUserId() + "/shelves" + shelf.getId() + "/books")
                .build();
    }


    public static Shelf toShelf(ShelfDto shelf) {
        return Shelf.builder()
                .id(shelf.getId())
                .name(shelf.getName())
                .build();
    }
}
