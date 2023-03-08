package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.BookList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookListRepositoryTest {
    @Autowired
    private BookListRepository repository;

    @Test
    public void test_add() {
        BookList bookList = BookList.builder().name("Ahmed").build();
        Flux<BookList> flux = repository.deleteAll().thenMany(repository.save(bookList));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_find() {
        BookList bookList = BookList.builder().name("Ahmed").build();

        Flux<BookList> save = repository.deleteAll().thenMany(repository.save(bookList)).doOnNext(a -> {
            bookList.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<BookList> flux = repository.findById(bookList.getId());
        StepVerifier.create(flux).expectNextMatches(bookList::equals).verifyComplete();
    }
}