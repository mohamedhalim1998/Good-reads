package com.mohamed.halim.goodreads.controller;


import com.mohamed.halim.goodreads.model.dto.ReviewDto;
import com.mohamed.halim.goodreads.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/profiles")
@AllArgsConstructor
public class ProfileController {
    private ProfileService profileService;


    @PostMapping("/{username}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReviewDto> postReview(@RequestBody ReviewDto reviewDto, @PathVariable("username") String username) {
        return profileService.saveBookReview(reviewDto, username);
    }

}
