package com.k24.hospital.controller;

import com.k24.hospital.entity.User;
import com.k24.hospital.repository.MedicalRecordRepository;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final UserRepository userRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "patient/dashboard";
    }

    @GetMapping("/history")
    public String history(Authentication authentication, Model model) {

        User patient = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        model.addAttribute(
                "records",
                medicalRecordRepository.findByAppointmentPatient(patient)
        );

        return "patient/history";
    }
}