package com.k24.hospital.service;

import com.k24.hospital.entity.User;
import com.k24.hospital.entity.UserProfile;
import com.k24.hospital.enums.Role;
import com.k24.hospital.repository.UserProfileRepository;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User registerPatient(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PATIENT);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        UserProfile profile = UserProfile.builder()
                .fullName(user.getUsername())
                .user(savedUser)
                .build();
        userProfileRepository.save(profile);

        return savedUser;
    }
}
