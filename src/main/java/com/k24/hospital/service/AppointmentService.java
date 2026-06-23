package com.k24.hospital.service;

import com.k24.hospital.entity.Appointment;
import com.k24.hospital.entity.Doctor;
import com.k24.hospital.entity.User;
import com.k24.hospital.enums.AppointmentStatus;
import com.k24.hospital.repository.AppointmentRepository;
import com.k24.hospital.repository.DoctorRepository;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public List<Appointment> getAppointmentsByPatient(String username) {
        User patient = userRepository.findByUsername(username).orElse(null);
        if (patient == null) {
            return Collections.emptyList();
        }
        return appointmentRepository.findByPatient(patient);
    }

    public List<Appointment> getWaitingAppointmentsForDoctor(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }
        Doctor doctor = doctorRepository.findByUser(user).orElse(null);
        if (doctor == null) {
            return Collections.emptyList();
        }
        return appointmentRepository.findByDoctorAndStatus(doctor, AppointmentStatus.WAITING);
    }

    @Transactional
    public String bookAppointment(String username, Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime) {
        User patient = userRepository.findByUsername(username).orElse(null);
        if (patient == null) {
            return "redirect:/login";
        }

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return "redirect:/patient/book?doctorNotFound";
        }

        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            return "redirect:/patient/book?past";
        }

        boolean exists = appointmentRepository
                .existsByDoctorAndAppointmentDateAndAppointmentTimeAndStatusNot(
                        doctor,
                        appointmentDate,
                        appointmentTime,
                        AppointmentStatus.CANCELLED
                );

        if (exists) {
            return "redirect:/patient/book?conflict";
        }

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(appointmentDate)
                .appointmentTime(appointmentTime)
                .status(AppointmentStatus.WAITING)
                .build();

        appointmentRepository.save(appointment);

        return "redirect:/patient/appointments?success";
    }

    @Transactional
    public String cancelAppointment(Long id, String username) {
        User patient = userRepository.findByUsername(username).orElse(null);
        if (patient == null) {
            return "redirect:/patient/appointments";
        }

        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment == null) {
            return "redirect:/patient/appointments";
        }

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            return "redirect:/patient/appointments";
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        LocalDateTime appointmentDateTime = LocalDateTime.of(
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime()
        );

        long hours = Duration.between(LocalDateTime.now(), appointmentDateTime).toHours();
        if (hours < 24) {
            return "redirect:/patient/appointments?late";
        }

        return "redirect:/patient/appointments?cancelled";
    }
}
