package com.k24.hospital.repository;

import com.k24.hospital.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository
        extends JpaRepository<Specialty, Long> {
}