package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RegistrationTest {

    @Test
    public void test_topProfile() {
        Profile profile = Profile.builder().username("user1").password("password").email("a@e.com").build();
        Assertions.assertEquals(profile, Registration.toProfile(new Registration(
                profile.getUsername(),
                profile.getEmail(),
                profile.getPassword()
        )));
    }

}