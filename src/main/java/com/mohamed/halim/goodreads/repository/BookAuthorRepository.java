package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.dto.AuthorDto;
import com.mohamed.halim.goodreads.model.joins.BookAuthor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface BookAuthorRepository extends R2dbcRepository<BookAuthor, Long> {
    Flux<BookAuthor> findByAuthorId(Long authorId);
    Flux<BookAuthor> findByBookId(String bookId);

    Mono<Void> deleteByBookId(String bookId);
}
