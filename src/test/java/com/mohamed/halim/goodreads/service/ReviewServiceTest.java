package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.ProfileDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private ReviewService reviewService;


    @Test
    public void test_saveUserBookWithReview() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(reviewRepository.save(any())).thenReturn(Mono.just(review));
        Mockito.when(bookRepository.findByISBN(any())).thenReturn(Mono.just(book));
        Mockito.when(profileRepository.findByUsername(any())).thenReturn(Mono.just(profile));
        Mono<ReviewDto> mono = reviewService.saveBookReview(dto, "user1").doOnNext(reviewDto -> {
            dto.setId(reviewDto.getId());
        });
        StepVerifier.create(mono).expectNextMatches(dto::equals).verifyComplete();
    }
}