package com.k24.hospital.service;

import com.k24.hospital.entity.User;
import com.k24.hospital.entity.UserProfile;
import com.k24.hospital.repository.UserProfileRepository;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfile getOrCreateProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        return userProfileRepository.findByUser(user)
                .orElseGet(() -> UserProfile.builder().user(user).build());
    }

    @Transactional
    public User saveProfile(String username, UserProfile profileForm) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElse(new UserProfile());

        profile.setUser(user);
        profile.setFullName(profileForm.getFullName());
        profile.setPhone(profileForm.getPhone());
        profile.setAddress(profileForm.getAddress());
        profile.setDateOfBirth(profileForm.getDateOfBirth());
        profile.setGender(profileForm.getGender());

        userProfileRepository.save(profile);
        return user;
    }
}
