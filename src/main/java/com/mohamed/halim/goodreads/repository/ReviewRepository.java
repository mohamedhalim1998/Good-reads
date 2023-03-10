package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository

public interface ReviewRepository extends R2dbcRepository<Review, Long> {

    Flux<Review> findReviewsByUserId(@Param("username") String username, Pageable pageable);
}
