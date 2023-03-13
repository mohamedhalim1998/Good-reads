package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private ReviewService reviewService;
    @Mock
    private ProfileBookRepository profileBookRepository;
    @Mock
    private BookRepository bookRepository;
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

    @Test
    public void test_getBooksByUsername() {
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        List<ProfileBook> profileBooks = IntStream.range(0, 20).mapToObj(i -> ProfileBook.builder().bookId("9780345296061").userId("user1").build()).toList();
        Mockito.when(profileBookRepository.findByUserId(any(), any())).thenReturn(Flux.fromIterable(profileBooks));
        Mockito.when(bookRepository.findByISBN(anyString())).thenReturn(Mono.just(book));
        StepVerifier.create(bookService.getBooksByUsername("user1", 0))
                .expectNextMatches(bookDto -> BookDto.fromBook(book).equals(bookDto))
                .expectNextCount(19)
                .verifyComplete();
    }


}