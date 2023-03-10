package com.mohamed.halim.goodreads.service;


import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ProfileDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private ProfileRepository profileRepository;

    public Mono<ReviewDto> saveBookReview(ReviewDto reviewDto, String username) {
        Review review = ReviewDto.toReview(reviewDto);
        review.setUserId(username);
        return reviewRepository.save(review).map(ReviewDto::fromReview)
                .zipWith(profileRepository.findByUsername(username))
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
}
