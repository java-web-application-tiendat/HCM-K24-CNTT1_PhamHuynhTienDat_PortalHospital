package com.k24.hospital.repository;

import com.k24.hospital.entity.Prescription;
import com.k24.hospital.enums.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByStatus(PrescriptionStatus status);

    Long countByStatus(PrescriptionStatus status);
}