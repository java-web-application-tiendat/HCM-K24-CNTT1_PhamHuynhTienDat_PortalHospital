package com.k24.hospital.repository;

import com.k24.hospital.entity.MedicalRecord;
import com.k24.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByAppointmentPatient(User patient);
}