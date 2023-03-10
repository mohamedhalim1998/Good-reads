package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.AuthResponse;
import com.mohamed.halim.goodreads.model.dto.Login;
import com.mohamed.halim.goodreads.model.dto.Registration;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private ReviewService reviewService;
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

    @Test
    public void test_saveUserBookReview() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(reviewService.saveBookReview(any())).thenReturn(Mono.just(dto));
        Mono<ReviewDto> mono = profileService.saveBookReview(dto, "user1").doOnNext(reviewDto -> {
            dto.setId(reviewDto.getId());
        });
        StepVerifier.create(mono).expectNextMatches(dto::equals).verifyComplete();
    }

    @Test
    public void test_getUserReviews() {
        List<Review> reviews = IntStream.range(0, 20).mapToObj(i -> Review.builder().id((long) i).userId("user1").bookId("" + i).rate(4.0).comment("this a review from user1").build()).collect(Collectors.toList());
        List<Book> books = IntStream.range(0, 100).mapToObj(i -> Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build()).collect(Collectors.toList());
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        List<ReviewDto> dtos = IntStream.range(0, 20).mapToObj(i -> ReviewDto.fromReview(reviews.get(i), profile, books.get(i))).collect(Collectors.toList());
        Mockito.when(reviewService.findUserReviews(any(), anyInt())).thenReturn(Flux.fromIterable(dtos));
        StepVerifier.create(profileService.findUserReviews("user1", 0));

    }

}