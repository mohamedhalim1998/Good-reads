package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Shelf;
import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ShelfDto;
import com.mohamed.halim.goodreads.repository.ShelfBookRepository;
import com.mohamed.halim.goodreads.repository.ShelfRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.mohamed.halim.goodreads.config.ConfigProperties.PAGE_SIZE;

@Service
@AllArgsConstructor
public class ShelfService {
    private ShelfRepository shelfRepository;
    private ShelfBookRepository shelfBookRepository;
    private BookService bookService;

    public Flux<ShelfDto> getProfileShelves(String username, int page) {
        Flux<Shelf> shelfFlux = shelfRepository.findByUserId(username, PageRequest.of(page, PAGE_SIZE));
        Flux<Long> bookCount = shelfFlux.flatMap(shelf -> shelfBookRepository.findByShelfId(shelf.getId()).count());
        return shelfFlux.map(ShelfDto::fromShelf).zipWith(bookCount).map(
                tuple -> {
                    tuple.getT1().setBookCount(tuple.getT2().intValue());
                    return tuple.getT1();
                }
        );
    }

    public Mono<ShelfDto> saveProfileShelf(String username, ShelfDto profileShelf) {
        Shelf shelf = ShelfDto.toShelf(profileShelf);
        shelf.setUserId(username);
        return shelfRepository.save(shelf).map(ShelfDto::fromShelf);
    }

    public Mono<Void> deleteProfileShelves(String username) {
        return shelfRepository.deleteAllByUserId(username);
    }

    public Mono<Void> deleteProfileShelf(ShelfDto shelfDto) {
        return shelfRepository.deleteById(shelfDto.getId());
    }

    public Flux<BookDto> getShelfBooks(Long shelfId, int page) {
        return shelfBookRepository.findByShelfId(shelfId, PageRequest.of(page, PAGE_SIZE)).flatMap(
                shelfBook -> bookService.getBook(shelfBook.getBookId())
        );
    }
}
