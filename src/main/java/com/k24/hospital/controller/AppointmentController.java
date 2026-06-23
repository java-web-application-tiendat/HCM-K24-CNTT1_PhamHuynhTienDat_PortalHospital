package com.k24.hospital.controller;

import com.k24.hospital.service.AppointmentService;
import com.k24.hospital.service.DoctorService;
import com.k24.hospital.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class AppointmentController {

    private final SpecialtyService specialtyService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @GetMapping("/book")
    public String bookingForm(Model model) {
        model.addAttribute("specialties", specialtyService.findAll());
        model.addAttribute("doctors", doctorService.findAll());
        return "patient/book-appointment";
    }

    @PostMapping("/book")
    public String bookAppointment(
            Authentication authentication,
            @RequestParam Long doctorId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime appointmentTime
    ) {
        return appointmentService.bookAppointment(
                authentication.getName(),
                doctorId,
                appointmentDate,
                appointmentTime
        );
    }

    @GetMapping("/appointments")
    public String myAppointments(
            Authentication authentication,
            Model model
    ) {
        model.addAttribute(
                "appointments",
                appointmentService.getAppointmentsByPatient(authentication.getName())
        );
        return "patient/appointments";
    }

    @GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return appointmentService.cancelAppointment(id, authentication.getName());
    }
}