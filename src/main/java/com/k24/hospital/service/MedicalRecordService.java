package com.k24.hospital.service;

import com.k24.hospital.entity.*;
import com.k24.hospital.enums.AppointmentStatus;
import com.k24.hospital.enums.PrescriptionStatus;
import com.k24.hospital.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    @Transactional
    public void examine(
            Long appointmentId,
            String symptoms,
            String diagnosis,
            String note,
            Long medicineId,
            Integer quantity,
            String dosage
    ) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointment == null) {
            return;
        }

        MedicalRecord record = MedicalRecord.builder()
                .appointment(appointment)
                .symptoms(symptoms)
                .diagnosis(diagnosis)
                .note(note)
                .createdAt(LocalDateTime.now())
                .build();

        medicalRecordRepository.save(record);

        Prescription prescription = Prescription.builder()
                .medicalRecord(record)
                .status(PrescriptionStatus.WAITING_DISPENSE)
                .createdAt(LocalDateTime.now())
                .build();

        prescriptionRepository.save(prescription);

        Medicine medicine = medicineRepository.findById(medicineId).orElse(null);

        if (medicine == null) {
            throw new RuntimeException("Không tìm thấy thuốc");
        }

        PrescriptionDetail detail = PrescriptionDetail.builder()
                .prescription(prescription)
                .medicine(medicine)
                .quantity(quantity)
                .dosage(dosage)
                .build();

        prescriptionDetailRepository.save(detail);

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    public List<MedicalRecord> getRecordsForPatient(String username) {
        User patient = userRepository.findByUsername(username).orElse(null);
        if (patient == null) {
            return Collections.emptyList();
        }
        return medicalRecordRepository.findByAppointmentPatient(patient);
    }
}