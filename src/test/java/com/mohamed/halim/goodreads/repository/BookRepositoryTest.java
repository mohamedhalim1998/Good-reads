package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Test
    public void test_add() {
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Flux<Book> flux = repository.deleteAll().thenMany(repository.save(book));
        StepVerifier.create(flux).expectNextMatches(book::equals).verifyComplete();
    }

    @Test
    public void test_find() {
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Flux<Book> flux = repository.deleteAll().thenMany(repository.save(book)).thenMany(repository.findByISBN(book.getISBN()));
        StepVerifier.create(flux).expectNextMatches(book::equals).verifyComplete();
    }

}