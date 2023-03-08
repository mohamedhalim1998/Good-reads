package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Publisher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PublisherRepository extends R2dbcRepository<Publisher, Long> {

}
