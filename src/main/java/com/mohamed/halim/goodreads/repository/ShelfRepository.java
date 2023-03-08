package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Shelf;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ShelfRepository extends R2dbcRepository<Shelf, Long> {
}
