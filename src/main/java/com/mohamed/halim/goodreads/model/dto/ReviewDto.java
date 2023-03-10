package com.mohamed.halim.goodreads.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohamed.halim.goodreads.model.Book;
import com.mohamed.halim.goodreads.model.Profile;
import com.mohamed.halim.goodreads.model.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Validated
public class ReviewDto {
    private Long id;
    private String comment;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    private String bookId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    private String username;
    @NotNull
    @Min(0)
    @Max(5)
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
                .username(profile.getUsername())
                .bookId(book.getISBN())
                .build();
    }

    public static ReviewDto fromReview(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rate(review.getRate())
                .username(review.getUserId())
                .bookId(review.getBookId())
                .build();
    }

    public static Review toReview(ReviewDto review) {
        return Review.builder()
                .userId(review.username)
                .bookId(review.bookId)
                .comment(review.getComment())
                .rate(review.getRate())
                .build();
    }
}
