package com.mohamed.halim.goodreads.model.dto;

import com.mohamed.halim.goodreads.model.Profile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Validated
public class Registration {
    @NotEmpty
    private String username;
    @NotEmpty
    @Email(regexp = ".*@.*\\..*")
    private String email;
    @NotEmpty
    private String password;

    public static Profile toProfile(Registration registration) {
        return Profile.builder().username(registration.username).email(registration.email).password(registration.password).build();
    }

}
