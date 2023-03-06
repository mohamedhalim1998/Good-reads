package com.mohamed.halim.goodreads.controller;

import com.mohamed.halim.goodreads.model.dto.AuthResponse;
import com.mohamed.halim.goodreads.model.dto.Login;
import com.mohamed.halim.goodreads.model.dto.Registration;
import com.mohamed.halim.goodreads.service.ProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final ProfileService profileService;

    @PostMapping("register")
    public Mono<AuthResponse> register(@RequestBody Registration registration) {
        return profileService.registerUser(registration);
    }

    @PostMapping("login")
    public Mono<AuthResponse> login(@RequestBody Login login) {
        return profileService.login(login);
    }
}
