package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Registration {
    private String username;
    private String email;
    private String password;

    public static Profile toProfile(Registration registration) {
        return Profile.builder().username(registration.username).email(registration.email).password(registration.password).build();
    }

}
