package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.IntStream;

import static com.mohamed.halim.goodreads.config.ConfigProperties.PAGE_SIZE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileBookRepositoryTest {
    @Autowired
    private ProfileBookRepository repository;

    @Test
    public void test_saveProfileBook() {
        ProfileBook profileBook = ProfileBook.builder().bookId("123456").userId("user1").build();
        Mono<ProfileBook> mono = repository.deleteAll().then(repository.save(profileBook));
        profileBook.setId(1L);
        StepVerifier.create(mono).expectNextMatches(profileBook::equals).verifyComplete();
    }

    @Test
    public void test_findBuUsername() {
        List<ProfileBook> profileBooks = IntStream.range(0, 100).mapToObj(i -> ProfileBook.builder().bookId("123456").userId("user1").build()).toList();
        StepVerifier.create(repository.deleteAll()
                        .thenMany(repository.saveAll(profileBooks))
                        .thenMany(repository.findByUserId("user1", PageRequest.of(0, PAGE_SIZE))))
                .expectNextCount(PAGE_SIZE)
                .verifyComplete();
    }

}