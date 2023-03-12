package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.Exception.UserNotFoundException;
import com.mohamed.halim.goodreads.model.dto.*;
import com.mohamed.halim.goodreads.repository.ProfileRepository;
import com.mohamed.halim.goodreads.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReviewService reviewService;
    private final ImageService imageService;

    public Mono<AuthResponse> registerUser(Registration registration) {
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));
        return profileRepository.save(Registration.toProfile(registration)).map(profile -> {
            String token = jwtService.generateToken(profile);
            return AuthResponse.builder().username(profile.getUsername()).email(profile.getEmail()).token(token).build();
        });
    }

    public Mono<AuthResponse> login(Login login) {
        return profileRepository.findByUsername(login.getUsername()).map(profile -> {
            if (passwordEncoder.matches(login.getPassword(), profile.getPassword())) {
                String token = jwtService.generateToken(profile);
                return AuthResponse.builder().username(profile.getUsername()).email(profile.getEmail()).token(token).build();
            } else {
                throw new UserNotFoundException();
            }
        }).switchIfEmpty(Mono.error(new UserNotFoundException()));
    }

    public Mono<ReviewDto> saveBookReview(ReviewDto reviewDto, String username) {
        reviewDto.setUsername(username);
        return reviewService.saveBookReview(reviewDto);
    }

    public Flux<ReviewDto> getReviews(String username, int page) {
        return reviewService.findUserReviews(username, page);
    }

    public Mono<ProfileDto> getProfile(String username) {
        return profileRepository.findByUsername(username).map(ProfileDto::fromProfile);
    }


    public Mono<ProfileDto> saveProfileInfo(String username, ProfileDto dto, MultipartFile profilePic) throws IOException {

        return profileRepository.findByUsername(username).flatMap(profile -> {
            profile = ProfileDto.updateProfileFromDto(profile, dto);
            if (profilePic != null) {
                try {
                    profile.setProfilePic(imageService.saveImage(profilePic));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return profileRepository.save(profile);
        }).map(ProfileDto::fromProfile);
    }
}
