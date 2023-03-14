package com.mohamed.halim.goodreads.service;


import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ProfileDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.repository.ReviewRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {
    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private ProfileRepository profileRepository;
    private Validator validator;

    public Mono<ReviewDto> saveBookReview(ReviewDto reviewDto) {
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(reviewDto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (var constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
           return  Mono.error(new ConstraintViolationException("Error occurred: " + sb, violations));
        }

        Review review = ReviewDto.toReview(reviewDto);
        return reviewRepository.save(review).map(ReviewDto::fromReview)
                .zipWith(profileRepository.findByUsername(reviewDto.getUsername()))
                .map(tuple -> {
                    tuple.getT1().setProfileDto(ProfileDto.fromProfile(tuple.getT2()));
                    return tuple.getT1();
                })
                .zipWith(bookRepository.findByISBN(review.getBookId()))
                .map(tuple -> {
                    tuple.getT1().setBookDto(BookDto.fromBook(tuple.getT2()));
                    return tuple.getT1();
                });

    }

    public Flux<ReviewDto> findUserReviews(String username, int page) {
        Flux<Review> reviewFlux = reviewRepository.findReviewsByUserId(username, PageRequest.of(1, 20)).doOnError(e -> log.error("error from review flux"));
        Flux<Book> bookFlux = reviewFlux.flatMap(review -> bookRepository.findByISBN(review.getBookId())).doOnError(e -> log.error(e.getMessage() + "error from book flux"));
        return Flux.zip(reviewFlux, bookFlux, (review, book) -> {
            ReviewDto reviewDto = ReviewDto.fromReview(review);
            reviewDto.setBookDto(BookDto.fromBook(book));
            return reviewDto;
        });
    }


    public Mono<Void> deleteProfileReviews(String username) {
        return reviewRepository.deleteAllByUserId(username);
    }
}
