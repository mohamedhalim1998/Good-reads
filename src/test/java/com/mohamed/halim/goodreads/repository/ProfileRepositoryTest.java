package com.mohamed.halim.goodreads.repository;

import com.mohamed.halim.goodreads.model.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProfileRepositoryTest {
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void test_saveProfile() {
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        Flux<Profile> publisher = profileRepository.deleteAll().thenMany(profileRepository.save(profile));
        StepVerifier.create(publisher)
                .expectNextMatches(p -> Objects.equals(profile, p))
                .verifyComplete();
    }

    @Test
    public void test_findProfileByUsername() {
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        profileRepository.deleteAll().thenMany(profileRepository.save(profile));
        StepVerifier.create(profileRepository.findByUsername("user1"))
                .expectNextMatches(p -> Objects.equals(profile, p))
                .verifyComplete()
        ;
    }

    @Test
    public void test_findNonExistUsername() {
        Flux<Profile> publisher = profileRepository.deleteAll().thenMany(profileRepository.findByUsername("user1"));
        StepVerifier.create(publisher)
                .expectNextCount(0)
                .verifyComplete();
    }

}