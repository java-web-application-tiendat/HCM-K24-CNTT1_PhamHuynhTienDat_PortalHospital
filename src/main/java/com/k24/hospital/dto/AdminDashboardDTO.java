package com.k24.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardDTO {

    private Long totalUsers;
    private Long totalDoctors;
    private Long totalPatients;

    private Long totalAppointments;
    private Long waitingAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;

    private Long totalMedicines;
    private Long lowStockMedicines;

    private Long waitingPrescriptions;
    private Long dispensedPrescriptions;

    private Long totalMedicalRecords;
}