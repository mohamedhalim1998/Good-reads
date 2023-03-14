package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import com.mohamed.halim.goodreads.model.joins.ProfileBookList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProfileBookListRepository extends R2dbcRepository<ProfileBookList, Long> {
    Flux<ProfileBookList> findByUserId(String username, Pageable pageable);

}
