package com.k24.hospital.repository;

import com.k24.hospital.entity.Appointment;
import com.k24.hospital.entity.Doctor;
import com.k24.hospital.entity.User;
import com.k24.hospital.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorAndStatus(Doctor doctor, AppointmentStatus status);

    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );

    boolean existsByDoctorAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status
    );

    List<Appointment> findByPatient(User patient);

    Long countByStatus(AppointmentStatus status);
}