package com.k24.hospital.repository;

import com.k24.hospital.entity.User;
import com.k24.hospital.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);
}