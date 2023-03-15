package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.dto.AuthorDto;
import com.mohamed.halim.goodreads.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository authorRepository;

    public Mono<AuthorDto> getAuthor(Long authorId) {
        return authorRepository.findById(authorId).map(AuthorDto::fromAuthor).switchIfEmpty(Mono.error(new IllegalArgumentException()));
    }
}
