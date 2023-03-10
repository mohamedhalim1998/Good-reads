package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping({"/{username}/reviews", "/{book}/reviews", "reviews"})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReviewDto> postReviewToUser(@RequestBody ReviewDto reviewDto) {
        return reviewService.saveBookReview(reviewDto);
    }


}
