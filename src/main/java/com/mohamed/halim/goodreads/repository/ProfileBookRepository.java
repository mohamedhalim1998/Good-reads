package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProfileBookRepository extends R2dbcRepository<ProfileBook, Long> {
    Flux<ProfileBook> findByUserId(String username, Pageable pageable);
}
