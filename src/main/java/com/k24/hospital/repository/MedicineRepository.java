package com.k24.hospital.repository;

import com.k24.hospital.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Long countByQuantityLessThan(Integer quantity);
}