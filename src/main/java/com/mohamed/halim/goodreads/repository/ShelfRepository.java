package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Shelf;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository

public interface ShelfRepository extends R2dbcRepository<Shelf, Long> {
    Flux<Shelf> findByUserId(String username, Pageable page);
}
