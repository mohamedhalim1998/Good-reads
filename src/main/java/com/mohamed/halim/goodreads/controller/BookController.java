package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.service.BookService;
import com.mohamed.halim.goodreads.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/{isbn}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReviewDto> postReview(@RequestBody ReviewDto reviewDto, @PathVariable("isbn") String ISBN) {
        return bookService.saveBookReview(reviewDto, ISBN);
    }
}
