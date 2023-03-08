package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Series;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SeriesRepository extends R2dbcRepository<Series, Long> {
}
