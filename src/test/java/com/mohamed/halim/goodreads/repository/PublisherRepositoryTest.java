package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Author;
import com.mohamed.halim.goodreads.model.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublisherRepositoryTest {
    @Autowired
    private PublisherRepository repository;

    @Test
    public void test_addAuthor() {
        Publisher publisher = Publisher.builder().name("Ahmed").build();
        Flux<Publisher> flux = repository.deleteAll().thenMany(repository.save(publisher));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_findAuthor() {
        Publisher publisher = Publisher.builder().name("Ahmed").build();

        Flux<Publisher> save = repository.deleteAll().thenMany(repository.save(publisher)).doOnNext(a -> {
            publisher.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<Publisher> flux = repository.findById(publisher.getId());
        StepVerifier.create(flux).expectNextMatches(publisher::equals).verifyComplete();
    }
}