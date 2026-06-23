package com.k24.hospital.controller;

import com.k24.hospital.service.AppointmentService;
import com.k24.hospital.service.MedicalRecordService;
import com.k24.hospital.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final AppointmentService appointmentService;
    private final MedicineService medicineService;
    private final MedicalRecordService medicalRecordService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "doctor/dashboard";
    }

    @GetMapping("/appointments")
    public String appointments(Authentication authentication, Model model) {
        model.addAttribute(
                "appointments",
                appointmentService.getWaitingAppointmentsForDoctor(authentication.getName())
        );
        return "doctor/appointments";
    }

    @GetMapping("/examination/{id}")
    public String examinationForm(@PathVariable Long id, Model model) {
        model.addAttribute("appointmentId", id);
        model.addAttribute("medicines", medicineService.findAll());
        return "doctor/examination";
    }

    @PostMapping("/examination/save")
    public String saveExamination(
            @RequestParam Long appointmentId,
            @RequestParam String symptoms,
            @RequestParam String diagnosis,
            @RequestParam String note,
            @RequestParam Long medicineId,
            @RequestParam Integer quantity,
            @RequestParam String dosage
    ) {
        medicalRecordService.examine(
                appointmentId,
                symptoms,
                diagnosis,
                note,
                medicineId,
                quantity,
                dosage
        );
        return "redirect:/doctor/appointments";
    }
}