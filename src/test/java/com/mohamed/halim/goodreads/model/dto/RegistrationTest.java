package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mohamed.halim.goodreads.config.ConfigProperties.DEFAULT_PROFILE_PIC;


class RegistrationTest {

    @Test
    public void test_topProfile() {
        Profile profile = Profile.builder().username("user1").profilePic(DEFAULT_PROFILE_PIC).fullName("user1").password("password").email("a@e.com").build();
        Assertions.assertEquals(profile, Registration.toProfile(new Registration(
                profile.getUsername(),
                profile.getEmail(),
                profile.getPassword()
        )));
    }

}