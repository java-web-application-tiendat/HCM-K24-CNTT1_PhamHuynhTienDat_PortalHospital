package com.k24.hospital.repository;

import com.k24.hospital.entity.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionDetailRepository
        extends JpaRepository<PrescriptionDetail, Long> {
}