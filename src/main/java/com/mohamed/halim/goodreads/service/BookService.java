package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.mohamed.halim.goodreads.config.ConfigProperties.PAGE_SIZE;

@AllArgsConstructor
@Slf4j
@Service
public class BookService {
    private ReviewService reviewService;
    private BookRepository bookRepository;
    private ProfileBookRepository profileBookRepository;

    public Mono<ReviewDto> saveBookReview(ReviewDto reviewDto, String ISBN) {
        reviewDto.setBookId(ISBN);
        return reviewService.saveBookReview(reviewDto);
    }

    public Flux<BookDto> getBooksByUsername(String username, int page) {
        return profileBookRepository.findByUserId(username, PageRequest.of(page, PAGE_SIZE))
                .flatMap(profileBook -> bookRepository.findByISBN(profileBook.getBookId()))
                .map(BookDto::fromBook);

    }

    public Mono<ProfileBook> saveBookToUser(String username, ProfileBook profileBook) {
        profileBook.setUserId(username);
        return profileBookRepository.save(profileBook);
    }

    public Mono<Void> deleteProfileLists(String username) {
        return profileBookRepository.deleteAllByUserId(username);
    }
}
