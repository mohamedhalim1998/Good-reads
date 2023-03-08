package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository

public interface BookRepository extends R2dbcRepository<Book, String> {
    Mono<Book> findByISBN(String isbn);
}
