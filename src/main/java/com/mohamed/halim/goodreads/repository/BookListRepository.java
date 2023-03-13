package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.BookList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Repository
public interface BookListRepository extends R2dbcRepository<BookList, Long> {
//    @Query("SELECT * FROM BookList l WHERE l.id IN (SELECT pl.listId from ProfileBookList pl WHERE pl.userId = :username ")
//    Flux<BookList> findByUsername(@Param("username") String username, Pageable pageable);
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO profile_book_list (user_id, list_id) VALUES (:username, :listId)", nativeQuery = true)
//    void saveProfileList(@Param("username") String username,@Param("listId")  Long listId);
}
