package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.security.SecurityConfiguration;
import com.mohamed.halim.goodreads.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = SecurityConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BookControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ReviewService reviewService;
    @Test
    public void test_postReviewToBook() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(reviewService.saveBookReview(any())).thenReturn(Mono.just(dto));
        webTestClient.post().uri("/api/v1/books/9780345296061/reviews")
                .body(Mono.just(dto), ReviewDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.username").doesNotExist()
                .jsonPath("$.bookId").doesNotExist()
                .jsonPath("$.profile.username").isEqualTo(review.getUserId())
                .jsonPath("$.book.name").isEqualTo(book.getName())
                .jsonPath("$.comment").isEqualTo(review.getComment())
                .jsonPath("$.rate").isEqualTo(review.getRate());
    }

    @Test
    public void test_postReviewToBookNoUser() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(reviewService.saveBookReview(any())).thenReturn(Mono.just(dto));
        webTestClient.post().uri("/api/v1/reviews")
                .body(Mono.just(dto), ReviewDto.class)
                .exchange()
                .expectStatus().is4xxClientError();

    }
}