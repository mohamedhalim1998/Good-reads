package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("{isbn}/reviews")

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> postBook(@RequestBody BookDto dto) {
        return bookService.saveBook(dto);
    }

    @GetMapping("{isbn}")
    public Mono<BookDto> postBook(@PathVariable String isbn) {
        return bookService.getBook(isbn);
    }
    @GetMapping("{isbn}/reviews")
    public Flux<ReviewDto> getBookReviews(@PathVariable String isbn,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return bookService.getBookReviews(isbn, page);
    }

    @PostMapping("/{isbn}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReviewDto> postReview(@RequestBody ReviewDto reviewDto, @PathVariable("isbn") String ISBN) {
        return bookService.saveBookReview(reviewDto, ISBN);
    }


}
