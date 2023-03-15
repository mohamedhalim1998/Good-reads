package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.dto.*;
import com.mohamed.halim.goodreads.model.joins.BookAuthor;
import com.mohamed.halim.goodreads.model.joins.BookListBook;
import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import com.mohamed.halim.goodreads.repository.BookAuthorRepository;
import com.mohamed.halim.goodreads.repository.BookRepository;
import com.mohamed.halim.goodreads.repository.ProfileBookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.mohamed.halim.goodreads.config.ConfigProperties.PAGE_SIZE;

@AllArgsConstructor
@Slf4j
@Service
public class BookService {
    private ReviewService reviewService;
    private BookRepository bookRepository;
    private ProfileBookRepository profileBookRepository;
    private BookAuthorRepository bookAuthorRepository;
    private PublisherService publisherService;
    private AuthorService authorService;
    private BookListService bookListService;

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

    public Mono<Void> deleteProfileBook(ProfileBook profileBook) {
        return profileBookRepository.deleteById(profileBook.getId());
    }

    public Mono<BookDto> getBook(String bookId) {
        Mono<Book> bookMono = bookRepository.findByISBN(bookId);
        Mono<List<AuthorDto>> authorsDtoFlux = bookAuthorRepository.findByBookId(bookId)
                .flatMap(bookAuthor -> authorService.getAuthor(bookAuthor.getAuthorId()))
                .collectList();
        Mono<PublisherDto> publisherDtoMono = bookMono.flatMap(book -> publisherService.getPublisher(book.getPublisherId()));
        Mono<Double> avgRate = reviewService.findBookAvgRate(bookId);
        return bookMono.map(BookDto::fromBook)
                .zipWith(authorsDtoFlux)
                .map(tuple -> {
                    tuple.getT1().setAuthors(tuple.getT2());
                    return tuple.getT1();
                }).zipWith(publisherDtoMono)
                .map(tuple -> {
                    tuple.getT1().setPublisherDto(tuple.getT2());
                    return tuple.getT1();
                }).zipWith(avgRate)
                .map(tuple -> {
                    tuple.getT1().setAvgRating(tuple.getT2());
                    return tuple.getT1();
                })
                ;
    }

    public Mono<BookDto> saveBook(BookDto dto) {

        return publisherService.getPublisher(dto.getPublisherId())
                .thenMany(Flux.fromIterable(dto.getAuthorIds()))
                .flatMap(id -> authorService.getAuthor(id))
                .map(author -> BookAuthor.builder().authorId(author.getId()).bookId(dto.getISBN()).build())
                .flatMap(authorBook -> bookAuthorRepository.save(authorBook))
                .then(bookRepository.save(BookDto.toBook(dto)))
                .flatMap(book -> getBook(book.getISBN()));
    }

    public Flux<ReviewDto> getBookReviews(String isbn, int page) {
        return reviewService.findBookReviews(isbn, page);
    }

    public Flux<ListDto> getBookLists(String isbn, int page) {
        return bookListService.getBookLists(isbn, page);
    }

    public Mono<BookListBook> saveBookList(BookListBook listBook) {
        return bookListService.addBookList(listBook);
    }

    public Mono<Void> deleteBook(String bookId) {


        Mono<Double> avgRate = reviewService.findBookAvgRate(bookId);
        return bookRepository.deleteById(bookId)
                .then(bookAuthorRepository.deleteByBookId(bookId))
                .then(reviewService.deleteBookReviews(bookId))
                .then(bookListService.deleteBookLists(bookId));
    }
}
