package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.joins.ShelfBook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ShelfBookRepository extends R2dbcRepository<ShelfBook, Long> {

    Flux<ShelfBook> findByShelfId(Long id);

    Flux<ShelfBook> findByShelfId(Long shelfId, PageRequest of);
}
