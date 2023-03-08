package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Author;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long> {

}
