package com.mohamed.halim.goodreads.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profilePic;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String reviews;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String books;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lists;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String shelves;


    public static ProfileDto fromProfile(Profile profile) {
        return ProfileDto.builder()
                .username(profile.getUsername())
                .fullName(profile.getFullName())
                .profilePic(hostname  + "img/" + profile.getProfilePic())
                .reviews(hostname + "profiles/" + profile.getUsername() + "/reviews")
                .books(hostname + "profiles/" + profile.getUsername() + "/books")
                .lists(hostname + "profiles/" + profile.getUsername() + "/lists")
                .shelves(hostname + "profiles/" + profile.getUsername() + "/shelves")
                .build();
    }

    public static Profile toProfile(ProfileDto dto) {
        return Profile.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .profilePic(dto.getProfilePic())
                .build();
    }

    public static Profile updateProfileFromDto(Profile profile, ProfileDto dto) {
        return profile.toBuilder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .build();
    }
}
