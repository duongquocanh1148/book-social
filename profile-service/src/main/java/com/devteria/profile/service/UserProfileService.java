package com.devteria.profile.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.request.UserProfileUpdateRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(UserProfileCreationRequest request) {
//        if (userProfileRepository.existsByUserId(request.getUserId())) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "User profile existed");
//        }

        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        log.info("ABC: ", userProfile);
        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

    public List<UserProfileResponse> getProfiles() {
        log.info("In method get Profiles");
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toUserProfileResponse)
                .toList();
    }

    public UserProfileResponse getProfile(String profileId) {
        return userProfileMapper.toUserProfileResponse(findProfile(profileId));
    }

    public UserProfileResponse getProfileById(String id) {
        UserProfile userProfile = userProfileRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse updateProfile(String profileId, UserProfileUpdateRequest request) {
        UserProfile userProfile = findProfile(profileId);

        userProfileMapper.updateUserProfile(userProfile, request);

        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

    public void deleteProfile(String profileId) {
        userProfileRepository.deleteById(profileId);
    }

    private UserProfile findProfile(String profileId) {
        return userProfileRepository
                .findById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));
    }
}
