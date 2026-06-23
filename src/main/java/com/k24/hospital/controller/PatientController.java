package com.k24.hospital.controller;

import com.k24.hospital.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "patient/dashboard";
    }

    @GetMapping("/history")
    public String history(Authentication authentication, Model model) {
        model.addAttribute(
                "records",
                medicalRecordService.getRecordsForPatient(authentication.getName())
        );
        return "patient/history";
    }
}