package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.dto.AuthResponse;
import com.mohamed.halim.goodreads.model.dto.Login;
import com.mohamed.halim.goodreads.model.dto.Registration;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private ProfileService profileService;

    @Test
    public void test_register() {
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        Mockito.when(profileRepository.save(profile)).thenReturn(Mono.just(profile));
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("password");
        Mockito.when(jwtService.generateToken(any(Profile.class))).thenReturn("jwtToken");
        Mono<AuthResponse> responseMono = profileService.registerUser(new Registration("user1", "e@e.com", "password"));
        StepVerifier.create(responseMono).expectNextMatches(p -> Objects.equals(new AuthResponse(
                        "user1",
                        "e@e.com",
                        "jwtToken"
                ), p))
                .expectComplete()
                .verify();
        Mockito.verify(profileRepository).save(profile);
        Mockito.verify(passwordEncoder).encode(anyString());
        Mockito.verify(jwtService).generateToken(any(Profile.class));
    }

    @Test
    public void test_login() {
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        Mockito.when(profileRepository.findByUsername(anyString())).thenReturn(Mono.just(profile));
        Mockito.when(passwordEncoder.matches(profile.getPassword(), profile.getPassword())).thenReturn(true);
        Mockito.when(jwtService.generateToken(any(Profile.class))).thenReturn("jwtToken");
        Mono<AuthResponse> responseMono = profileService.login(new Login("user1", "password"));
        StepVerifier.create(responseMono).expectNextMatches(p -> Objects.equals(new AuthResponse(
                        "user1",
                        "e@e.com",
                        "jwtToken"
                ), p))
                .expectComplete()
                .verify();
        Mockito.verify(profileRepository).findByUsername(anyString());
        Mockito.verify(passwordEncoder).matches(anyString(), anyString());
        Mockito.verify(jwtService).generateToken(any(Profile.class));

    }

    @Test
    public void test_loginUserDoNotExist() {
        Mockito.when(profileRepository.findByUsername(anyString())).thenReturn(Mono.empty());
        Mono<AuthResponse> responseMono = profileService.login(new Login("user1", "password"));
        StepVerifier.create(responseMono).expectError()
                .verify();
        Mockito.verify(profileRepository).findByUsername(anyString());

    }

}