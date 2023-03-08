package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Shelf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShelfRepositoryTest {
    @Autowired
    private ShelfRepository repository;

    @Test
    public void test_add() {
        Shelf shelf = Shelf.builder().name("Ahmed").build();
        Flux<Shelf> flux = repository.deleteAll().thenMany(repository.save(shelf));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_find() {
        Shelf shelf = Shelf.builder().name("Ahmed").build();

        Flux<Shelf> save = repository.deleteAll().thenMany(repository.save(shelf)).doOnNext(a -> {
            shelf.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<Shelf> flux = repository.findById(shelf.getId());
        StepVerifier.create(flux).expectNextMatches(shelf::equals).verifyComplete();
    }
}