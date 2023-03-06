package com.mohamed.halim.goodreads.config;

import com.mohamed.halim.goodreads.model.dto.Login;
import com.mohamed.halim.goodreads.model.dto.Registration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SecurityConfigurationTest {
    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void test_allowRegister() {
        webTestClient.post().uri("/api/v1/auth/register").body(
                Mono.just(new Registration("user1", "A@e.com","password")), Registration.class
        ).exchange().expectStatus().isOk();
    }

    @Test
    public void test_allowLogin() {
        webTestClient.post().uri("/api/v1/auth/login").body(
                Mono.just(new Login("user1", "password")), Login.class
        ).exchange().expectStatus().isOk();
    }

    @Test
    public void test_checkJwtForAnyOther() {
        webTestClient.get().uri("/api/v1/test").exchange().expectStatus().isUnauthorized();
    }

}