package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.SocialMedia;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaRepository extends R2dbcRepository<SocialMedia, Long> {
}
