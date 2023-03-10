package com.mohamed.halim.goodreads.model.dto;


import com.mohamed.halim.goodreads.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProfileDto {
    private String username;
    private String fullName;
    private String profilePic;

    public static ProfileDto fromProfile(Profile profile) {
        return ProfileDto.builder()
                .username(profile.getUsername())
                .fullName(profile.getFullName())
                .profilePic(profile.getProfilePic())
                .build();
    }
}
