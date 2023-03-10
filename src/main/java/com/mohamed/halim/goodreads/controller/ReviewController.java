package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReviewDto> postReview(@RequestBody  ReviewDto reviewDto) {
        return reviewService.saveBookReview(reviewDto);
    }

}
