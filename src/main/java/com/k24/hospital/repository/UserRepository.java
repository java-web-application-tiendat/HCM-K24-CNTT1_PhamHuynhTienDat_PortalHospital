package com.k24.hospital.repository;

import com.k24.hospital.entity.User;
import com.k24.hospital.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Long countByRole(Role role);
}