package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository repository;

    @Test
    public void test_add() {
        Review review = Review.builder().comment("Ahmed").build();
        Flux<Review> flux = repository.deleteAll().thenMany(repository.save(review));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_find() {
        Review review = Review.builder().comment("Ahmed").build();

        Flux<Review> save = repository.deleteAll().thenMany(repository.save(review)).doOnNext(a -> {
            review.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<Review> flux = repository.findById(review.getId());
        StepVerifier.create(flux).expectNextMatches(review::equals).verifyComplete();
    }
}