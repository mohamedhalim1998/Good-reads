package com.mohamed.halim.goodreads.security;

import com.mohamed.halim.goodreads.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private ProfileRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository.findByUsername(username).cast(UserDetails.class);
    }
}
