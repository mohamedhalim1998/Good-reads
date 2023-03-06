package com.mohamed.halim.goodreads.model.dto;

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
public class Login {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
