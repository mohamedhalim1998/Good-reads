package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.joins.BookListBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ListBookRepository extends R2dbcRepository<BookListBook, Long> {
    Flux<BookListBook> findByListId(long listId);
    Flux<BookListBook> findByBookId(String isbn, Pageable pageable);
}
