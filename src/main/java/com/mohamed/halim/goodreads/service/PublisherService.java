package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.dto.PublisherDto;
import com.mohamed.halim.goodreads.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PublisherService {
    private PublisherRepository publisherRepository;
    public Mono<PublisherDto> getPublisher(Long publisherId) {
        return publisherRepository.findById(publisherId).map(PublisherDto::fromPublisher);
    }
}
