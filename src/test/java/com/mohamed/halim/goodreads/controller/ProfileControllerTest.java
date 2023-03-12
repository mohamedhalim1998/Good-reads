package com.mohamed.halim.goodreads.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import com.mohamed.halim.goodreads.model.dto.BookDto;
import com.mohamed.halim.goodreads.model.dto.ProfileDto;
import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.security.SecurityConfiguration;
import com.mohamed.halim.goodreads.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(classes = SecurityConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProfileControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ProfileService profileService;

    @Test
    public void test_postReviewToUser() {
        Review review = Review.builder().id(1L).userId("user1").bookId("9780345296061").rate(4.0).comment("this a review from user1").build();
        Book book = Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ReviewDto dto = ReviewDto.fromReview(review, profile, book);
        Mockito.when(profileService.saveBookReview(any(), anyString())).thenReturn(Mono.just(dto));
        webTestClient.post().uri("/api/v1/profiles/user1/reviews")
                .body(Mono.just(dto), Review.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.username").doesNotExist()
                .jsonPath("$.bookId").doesNotExist()
                .jsonPath("$.profile.username").isEqualTo(review.getUserId())
                .jsonPath("$.book.name").isEqualTo(book.getName())
                .jsonPath("$.comment").isEqualTo(review.getComment())
                .jsonPath("$.rate").isEqualTo(review.getRate());
    }

    @Test
    public void test_getUserReviews() throws JsonProcessingException {
        List<Review> reviews = IntStream.range(0, 20).mapToObj(i -> Review.builder().id((long) i).userId("user1").bookId("" + i).rate(4.0).comment("this a review from user1").build()).collect(Collectors.toList());
        List<Book> books = IntStream.range(0, 100).mapToObj(i -> Book.builder().name("THe Lord Of The Rings").ISBN("9780345296061").build()).toList();
        List<ReviewDto> dtos = IntStream.range(0, 20).mapToObj(i -> {
            ReviewDto reviewDto = ReviewDto.fromReview(reviews.get(i));
            reviewDto.setBookDto(BookDto.fromBook(books.get(i)));
            return reviewDto;
        }).toList();
        Mockito.when(profileService.getReviews(any(), anyInt())).thenReturn(Flux.fromIterable(dtos));
        webTestClient.get().uri("/api/v1/profiles/user1/reviews")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(new ObjectMapper().writeValueAsString(dtos));
    }


    @Test
    public void test_getUserInfo() throws JsonProcessingException {
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ProfileDto profileDto = ProfileDto.fromProfile(profile);
        Mockito.when(profileService.getProfile(anyString())).thenReturn(Mono.just(profileDto));
        System.out.println(profileDto);
        webTestClient.get().uri("/api/v1/profiles/user1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(new ObjectMapper().writeValueAsString(profileDto));
    }

    @Test
    public void test_postUserWithImage() throws IOException {
        String uuid = UUID.randomUUID().toString();
        Profile profile = Profile.builder().username("user1").password("password").email("e@e.com").build();
        ProfileDto profileDto = ProfileDto.fromProfile(profile);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        File file = new File("image.jpg");
        file.createNewFile();
        String header1 = String.format("form-data; name=%s; filename=%s", "attach", file.getName());
        multipartBodyBuilder.part("profilePic", new ByteArrayResource(Files.readAllBytes(file.toPath()))).header("Content-Disposition", header1);
        multipartBodyBuilder.part("profile", new ObjectMapper().writeValueAsString(profileDto)).contentType(MediaType.APPLICATION_JSON);
        profile.setProfilePic(uuid);
        profileDto = ProfileDto.fromProfile(profile);
        Mockito.when(profileService.saveProfileInfo(anyString(), any(), any())).thenReturn(Mono.just(ProfileDto.fromProfile(profile)));
        webTestClient.post().uri("/api/v1/profiles/user1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(new ObjectMapper().writeValueAsString(profileDto));

    }


}