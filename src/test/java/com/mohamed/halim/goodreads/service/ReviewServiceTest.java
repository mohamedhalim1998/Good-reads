package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ProfileDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.repository.ReviewRepository;
import com.mohamed.halim.goodreads.security.SecurityConfiguration;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest(classes = SecurityConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Order(1)
class ReviewServiceTest {
    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private Validator validator;
    @Autowired
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
        Mono<ReviewDto> mono = reviewService.saveBookReview(dto).doOnNext(reviewDto -> {
            dto.setId(reviewDto.getId());
        });
        StepVerifier.create(mono).expectNextMatches(dto::equals).verifyComplete();
    }

    @Test
    public void  test_saveReviewNotValidNoUser() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        dto.setUsername(null);
        Mockito.when(reviewRepository.save(any())).thenReturn(Mono.just(review));
        Mockito.when(bookRepository.findByISBN(any())).thenReturn(Mono.just(book));
        Mockito.when(profileRepository.findByUsername(any())).thenReturn(Mono.just(profile));
        Mono<ReviewDto> mono = reviewService.saveBookReview(dto).doOnNext(reviewDto -> {
            dto.setId(reviewDto.getId());
        });
        StepVerifier.create(mono).verifyError();

    }
    @Test
    public void  test_saveReviewNotValidNobookId() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(reviewRepository.save(any())).thenReturn(Mono.just(review));

        dto.setBookId(null);
        Mono<ReviewDto> mono = reviewService.saveBookReview(dto).doOnNext(reviewDto -> {
            dto.setId(reviewDto.getId());
        });
        StepVerifier.create(mono).verifyError();

    }

    @Test
    public void test_getUserReviews() {
        List<Review> reviews = IntStream.range(0, 20).mapToObj(i -> Review.builder().id((long) i).userId("user1").bookId("" + i).rate(4.0).comment("this a review from user1").build()).collect(Collectors.toList());
        List<Book> books = IntStream.range(0, 100).mapToObj(i -> Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build()).toList();
        List<ReviewDto> dtos = IntStream.range(0, 20).mapToObj(i -> {
            ReviewDto reviewDto = ReviewDto.fromReview(reviews.get(i));
            reviewDto.setBookDto(BookDto.fromBook(books.get(i)));
            return reviewDto;
        }).toList();
        Mockito.when(reviewRepository.findReviewsByUserId(anyString(), any())).thenReturn(Flux.fromIterable(reviews));
        Mockito.when(bookRepository.findByISBN(anyString())).thenReturn(Mono.just(books.get(0)));
        StepVerifier.create(reviewService.findUserReviews("user1", 1))
                .expectNextSequence(dtos)
                .verifyComplete();

    }
}