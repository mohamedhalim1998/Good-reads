package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Review;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReviewRepository extends R2dbcRepository<Review, Long> {
}
