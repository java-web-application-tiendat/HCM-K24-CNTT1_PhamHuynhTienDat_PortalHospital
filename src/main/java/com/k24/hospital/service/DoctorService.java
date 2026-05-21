package com.k24.hospital.service;

import com.k24.hospital.entity.*;
import com.k24.hospital.enums.AppointmentStatus;
import com.k24.hospital.enums.PrescriptionStatus;
import com.k24.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final MedicineRepository medicineRepository;

    @Transactional
    public void completeExamination(
            Appointment appointment,
            String symptoms,
            String diagnosis,
            String note,
            List<Long> medicineIds
    ) {

        MedicalRecord medicalRecord =
                MedicalRecord.builder()
                        .appointment(appointment)
                        .symptoms(symptoms)
                        .diagnosis(diagnosis)
                        .note(note)
                        .createdAt(LocalDateTime.now())
                        .build();

        medicalRecordRepository.save(medicalRecord);

        Prescription prescription =
                Prescription.builder()
                        .medicalRecord(medicalRecord)
                        .status(PrescriptionStatus.WAITING_DISPENSE)
                        .createdAt(LocalDateTime.now())
                        .build();

        prescriptionRepository.save(prescription);

        for (Long medicineId : medicineIds) {

            Medicine medicine =
                    medicineRepository
                            .findById(medicineId)
                            .orElseThrow();

            PrescriptionDetail detail =
                    PrescriptionDetail.builder()
                            .prescription(prescription)
                            .medicine(medicine)
                            .quantity(1)
                            .dosage("2 viên/ngày")
                            .build();

            prescriptionDetailRepository.save(detail);
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        appointmentRepository.save(appointment);
    }
}