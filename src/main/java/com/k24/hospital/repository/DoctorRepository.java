package com.k24.hospital.repository;

import com.k24.hospital.entity.Doctor;
import com.k24.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUser(User user);
}