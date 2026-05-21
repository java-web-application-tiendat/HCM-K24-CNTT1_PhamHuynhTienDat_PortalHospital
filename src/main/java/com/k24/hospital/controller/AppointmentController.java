package com.k24.hospital.controller;

import com.k24.hospital.entity.Appointment;
import com.k24.hospital.entity.Doctor;
import com.k24.hospital.entity.User;
import com.k24.hospital.enums.AppointmentStatus;
import com.k24.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class AppointmentController {

    private final SpecialtyRepository specialtyRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @GetMapping("/book")
    public String bookingForm(Model model) {

        model.addAttribute("specialties",
                specialtyRepository.findAll());

        model.addAttribute("doctors",
                doctorRepository.findAll());

        return "patient/book-appointment";
    }

    @PostMapping("/book")
    public String bookAppointment(
            Authentication authentication,
            @RequestParam Long doctorId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime appointmentTime
    ) {
        User patient = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        if (patient == null) {
            return "redirect:/login";
        }

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElse(null);

        if (doctor == null) {
            return "redirect:/patient/book?doctorNotFound";
        }

        LocalDateTime appointmentDateTime =
                LocalDateTime.of(appointmentDate, appointmentTime);

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

    @GetMapping("/appointments")
    public String myAppointments(
            Authentication authentication,
            Model model
    ) {

        User patient = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        model.addAttribute(
                "appointments",
                appointmentRepository.findByPatient(patient)
        );

        return "patient/appointments";
    }

    @GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(
            @PathVariable Long id,
            Authentication authentication
    ) {

        User patient = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        Appointment appointment =
                appointmentRepository
                        .findById(id)
                        .orElse(null);

        if (appointment == null) {
            return "redirect:/patient/appointments";
        }

        if (!appointment.getPatient()
                .getId()
                .equals(patient.getId())) {

            return "redirect:/patient/appointments";
        }

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        appointmentRepository.save(appointment);

        LocalDateTime appointmentDateTime =
                LocalDateTime.of(
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime()
                );

        long hours =
                Duration.between(
                        LocalDateTime.now(),
                        appointmentDateTime
                ).toHours();

        if (hours < 24) {
            return "redirect:/patient/appointments?late";
        }

        return "redirect:/patient/appointments?cancelled";
    }
}