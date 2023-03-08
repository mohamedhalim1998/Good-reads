package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Series;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SeriesRepositoryTest {
    @Autowired
    private SeriesRepository repository;

    @Test
    public void test_add() {
        Series series = Series.builder().name("Ahmed").build();
        Flux<Series> flux = repository.deleteAll().thenMany(repository.save(series));
        StepVerifier.create(flux).expectNextCount(1).verifyComplete();
    }

    @Test
    public void test_find() {
        Series series = Series.builder().name("Ahmed").build();

        Flux<Series> save = repository.deleteAll().thenMany(repository.save(series)).doOnNext(a -> {
            series.setId(a.getId());
        });
        StepVerifier.create(save).expectNextCount(1).verifyComplete();
        Mono<Series> flux = repository.findById(series.getId());
        StepVerifier.create(flux).expectNextMatches(series::equals).verifyComplete();
    }
}