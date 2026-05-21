package com.k24.hospital.service;

import com.k24.hospital.dto.AdminDashboardDTO;
import com.k24.hospital.enums.AppointmentStatus;
import com.k24.hospital.enums.PrescriptionStatus;
import com.k24.hospital.enums.Role;
import com.k24.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public AdminDashboardDTO getDashboardData() {
        return AdminDashboardDTO.builder()
                .totalUsers(userRepository.count())
                .totalDoctors(userRepository.countByRole(Role.DOCTOR))
                .totalPatients(userRepository.countByRole(Role.PATIENT))

                .totalAppointments(appointmentRepository.count())
                .waitingAppointments(appointmentRepository.countByStatus(AppointmentStatus.WAITING))
                .completedAppointments(appointmentRepository.countByStatus(AppointmentStatus.COMPLETED))
                .cancelledAppointments(appointmentRepository.countByStatus(AppointmentStatus.CANCELLED))

                .totalMedicines(medicineRepository.count())
                .lowStockMedicines(medicineRepository.countByQuantityLessThan(10))

                .waitingPrescriptions(prescriptionRepository.countByStatus(PrescriptionStatus.WAITING_DISPENSE))
                .dispensedPrescriptions(prescriptionRepository.countByStatus(PrescriptionStatus.DISPENSED))

                .totalMedicalRecords(medicalRecordRepository.count())
                .build();
    }
}