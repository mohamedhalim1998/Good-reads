package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class BookService {
    private ReviewService reviewService;

    public Mono<ReviewDto> saveBookReview(ReviewDto reviewDto, String ISBN) {
        reviewDto.setBookId(ISBN);
        return reviewService.saveBookReview(reviewDto);
    }
}
