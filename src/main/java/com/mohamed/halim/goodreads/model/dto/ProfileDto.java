package com.mohamed.halim.goodreads.model.dto;


import com.mohamed.halim.goodreads.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mohamed.halim.goodreads.config.ConfigProperties.hostname;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProfileDto {
    private String username;
    private String fullName;
    private String profilePic;
    private String reviews;
    private String books;
    private String lists;
    private String shelves;


    public static ProfileDto fromProfile(Profile profile) {
        return ProfileDto.builder()
                .username(profile.getUsername())
                .fullName(profile.getFullName())
                .profilePic(profile.getProfilePic())
                .reviews(hostname + "/" + profile.getUsername() + "/reviews")
                .books(hostname + "/" + profile.getUsername() + "/books")
                .lists(hostname + "/" + profile.getUsername() + "/lists")
                .shelves(hostname + "/" + profile.getUsername() + "/shelves")
                .build();
    }

  }
