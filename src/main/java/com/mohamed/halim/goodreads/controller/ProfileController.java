package com.mohamed.halim.goodreads.controller;


import com.mohamed.halim.goodreads.model.dto.*;
import com.mohamed.halim.goodreads.model.joins.ProfileBook;
import com.mohamed.halim.goodreads.model.joins.ProfileBookList;
import com.mohamed.halim.goodreads.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

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


    @GetMapping("/{username}/reviews")
    public Flux<ReviewDto> getProfileReviews(@PathVariable String username, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return profileService.getReviews(username, page);
    }

    @GetMapping("/{username}")
    public Mono<ProfileDto> getProfileInfo(@PathVariable String username) {
        return profileService.getProfile(username);
    }

    @PostMapping(value = "/{username}")
    public Mono<ProfileDto> postProfileInfo(@PathVariable String username,
                                            @RequestPart("profile") ProfileDto dto,
                                            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic) throws IOException {
        return profileService.saveProfileInfo(username, dto, profilePic);
    }

    @GetMapping("/{username}/books")
    public Flux<BookDto> getProfileBooks(@PathVariable String username,  @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return profileService.getBooks(username, page);
    }
    @PostMapping("/{username}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProfileBook> postProfileBook(@PathVariable String username, @RequestBody ProfileBook profileBook) {
        return profileService.addBook(username, profileBook);
    }
    @GetMapping("/{username}/list")
    public Flux<ListDto> getProfileLists(@PathVariable String username,  @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return profileService.getLists(username, page);
    }

    @PostMapping("/{username}/list")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProfileBookList> postProfileList(@PathVariable String username, @RequestBody ProfileBookList profileBookList) {
        return profileService.addList(username, profileBookList);
    }

    @GetMapping("/{username}/shelf")
    public Flux<ShelfDto> getProfileShelves(@PathVariable String username, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return profileService.getShelves(username, page);
    }

    @PostMapping("/{username}/shelf")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ShelfDto> postProfileShelves(@PathVariable String username, @RequestBody ShelfDto shelfDto) {
        return profileService.addShelf(username, shelfDto);
    }

}
