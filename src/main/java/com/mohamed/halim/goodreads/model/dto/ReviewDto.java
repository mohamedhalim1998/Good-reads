package com.mohamed.halim.goodreads.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReviewDto {
    private Long id;
    private String comment;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String bookId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    private double rate;
    @JsonProperty(value = "profile", access = JsonProperty.Access.READ_ONLY)
    private ProfileDto profileDto;
    @JsonProperty(value = "book", access = JsonProperty.Access.READ_ONLY)
    private BookDto bookDto;


    public static ReviewDto fromReview(Review review, Profile profile, Book book) {
        return ReviewDto.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rate(review.getRate())
                .bookDto(BookDto.fromBook(book))
                .profileDto(ProfileDto.fromProfile(profile))
                .build();
    }

    public static ReviewDto fromReview(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rate(review.getRate())
                .build();
    }

    public static Review toReview(ReviewDto review) {
        return Review.builder()
                .comment(review.getComment())
                .rate(review.getRate())
                .bookId(review.getBookId())
                .build();
    }
}
