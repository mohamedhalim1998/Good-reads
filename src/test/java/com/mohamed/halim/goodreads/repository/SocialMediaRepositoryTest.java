package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.SocialMedia;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class SocialMediaRepositoryTest {
    @Autowired
    private SocialMediaRepository repository;

    @Test
    public void test_add() {
        SocialMedia socialMedia = SocialMedia.builder().url("Ahmed").build();
        Flux<SocialMedia> flux = repository.deleteAll().thenMany(repository.save(socialMedia));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_find() {
        SocialMedia socialMedia = SocialMedia.builder().url("Ahmed").build();

        Flux<SocialMedia> save = repository.deleteAll().thenMany(repository.save(socialMedia)).doOnNext(a -> {
            socialMedia.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<SocialMedia> flux = repository.findById(socialMedia.getId());
        StepVerifier.create(flux).expectNextMatches(socialMedia::equals).verifyComplete();
    }
}