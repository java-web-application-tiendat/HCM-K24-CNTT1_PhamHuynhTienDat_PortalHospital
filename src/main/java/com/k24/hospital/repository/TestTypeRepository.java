package com.k24.hospital.repository;

import com.k24.hospital.entity.TestType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTypeRepository extends JpaRepository<TestType, Long> {
}