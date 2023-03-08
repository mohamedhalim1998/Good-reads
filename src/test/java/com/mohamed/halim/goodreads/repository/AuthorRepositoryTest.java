package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void test_addAuthor() {
        Author author = Author.builder().name("Ahmed").build();
        Flux<Author> flux = authorRepository.deleteAll().thenMany(authorRepository.save(author));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_findAuthor() {
        Author author = Author.builder().name("Ahmed").build();

        Flux<Author> save = authorRepository.deleteAll().thenMany(authorRepository.save(author)).doOnNext(a -> {
            author.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<Author> flux = authorRepository.findById(author.getId());
        StepVerifier.create(flux).expectNextMatches(author::equals).verifyComplete();
    }

}