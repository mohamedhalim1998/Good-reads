package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Profile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository extends R2dbcRepository<Profile, String> {
    Mono<Profile> findByUsername(String username);
    Mono<Void> deleteByUsername(String username);
}
