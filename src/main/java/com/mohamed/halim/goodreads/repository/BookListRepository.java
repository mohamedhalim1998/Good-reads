package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.BookList;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListRepository extends R2dbcRepository<BookList, Long> {
}
