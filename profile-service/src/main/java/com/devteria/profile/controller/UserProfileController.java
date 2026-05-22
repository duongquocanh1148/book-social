package com.devteria.profile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.devteria.profile.dto.ApiResponse;
import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.request.UserProfileUpdateRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody UserProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserProfileResponse>> getProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getProfiles())
                .build();
    }

    @GetMapping("/{profileId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(profileId))
                .build();
    }

    @GetMapping("/user/{id}")
    ApiResponse<UserProfileResponse> getProfileByUserId(@PathVariable String id) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfileById(id))
                .build();
    }

    @PutMapping("/{profileId}")
    ApiResponse<UserProfileResponse> updateProfile(
            @PathVariable String profileId, @RequestBody UserProfileUpdateRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.updateProfile(profileId, request))
                .build();
    }

    @DeleteMapping("/{profileId}")
    ApiResponse<String> deleteProfile(@PathVariable String profileId) {
        userProfileService.deleteProfile(profileId);
        return ApiResponse.<String>builder()
                .result("User profile has been deleted")
                .build();
    }
}
