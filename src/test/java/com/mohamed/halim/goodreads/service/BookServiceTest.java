package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)

class BookServiceTest {
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private BookService bookService;

    @Test
    public void test_saveUserBookReview() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(reviewService.saveBookReview(any())).thenReturn(Mono.just(dto));
        Mono<ReviewDto> mono = bookService.saveBookReview(dto, "user1").doOnNext(reviewDto -> {
            dto.setId(reviewDto.getId());
        });
        StepVerifier.create(mono).expectNextMatches(dto::equals).verifyComplete();
    }


}