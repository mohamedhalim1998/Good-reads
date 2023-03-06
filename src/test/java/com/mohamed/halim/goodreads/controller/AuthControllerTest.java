package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.Exception.UserNotFoundException;
import com.mohamed.halim.goodreads.model.dto.AuthResponse;
import com.mohamed.halim.goodreads.model.dto.Login;
import com.mohamed.halim.goodreads.model.dto.Registration;
import com.mohamed.halim.goodreads.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthControllerTest {

    @MockBean
    private ProfileService profileService;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_register_valid() {
        AuthResponse response = new AuthResponse(
                "user1",
                "e@e.com",
                "jwtToken"
        );
        Mockito.when(profileService.registerUser(any(Registration.class))).thenReturn(Mono.just(response));
        webTestClient.post().uri("/api/v1/auth/register").body(
                        Mono.just(new Registration("user1", "e@e.com", "password")), Registration.class
                ).exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isNotEmpty()
                .jsonPath("$.email").isNotEmpty()
                .jsonPath("$.token").isNotEmpty()
        ;
    }

    @Test
    public void test_register_invalid_missing_required() {

        webTestClient.post().uri("/api/v1/auth/register").body(
                Mono.just(Registration.builder().email("e@e").build()), Registration.class
        ).exchange().expectStatus().is4xxClientError()

        ;
        Mockito.verify(profileService, Mockito.never()).registerUser(any());
    }
    @Test
    public void test_register_invalid_email() {

        webTestClient.post().uri("/api/v1/auth/register").body(
                Mono.just(Registration.builder().email("e@e").username("user").password("password").build()), Registration.class

        ).exchange().expectStatus().is4xxClientError()

        ;
        Mockito.verify(profileService, Mockito.never()).registerUser(any());
    }

    @Test
    public void test_login_valid() {
        AuthResponse response = new AuthResponse(
                "user1",
                "e@e.com",
                "jwtToken"
        );
        Mockito.when(profileService.login(any(Login.class))).thenReturn(Mono.just(response));
        webTestClient.post().uri("/api/v1/auth/login").body(
                        Mono.just(new Login("user1", "password")), Login.class
                ).exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isNotEmpty()
                .jsonPath("$.email").isNotEmpty()
                .jsonPath("$.token").isNotEmpty()
        ;
    }

    @Test
    public void test_login_invalid() {
        webTestClient.post().uri("/api/v1/auth/login").body(
                Mono.just(Login.builder().username("username").build()), Login.class
        ).exchange().expectStatus().is4xxClientError()
        ;
        Mockito.verify(profileService, Mockito.never()).login(any());
    }

    @Test
    public void test_loginError() {
        AuthResponse response = new AuthResponse(
                "user1",
                "e@e.com",
                "jwtToken"
        );
        Mockito.when(profileService.login(any(Login.class))).thenReturn(Mono.error(new UserNotFoundException()));
        webTestClient.post().uri("/api/v1/auth/login").body(
                Mono.just(new Login("user1", "password")), Login.class
        ).exchange().expectStatus().is4xxClientError()

        ;
    }

}