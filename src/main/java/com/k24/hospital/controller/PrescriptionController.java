package com.k24.hospital.controller;

import com.k24.hospital.enums.PrescriptionStatus;
import com.k24.hospital.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute(
                "prescriptions",
                prescriptionService.getPrescriptionsByStatus(PrescriptionStatus.WAITING_DISPENSE)
        );
        return "admin/prescriptions";
    }

    @GetMapping("/dispense/{id}")
    public String dispense(@PathVariable Long id) {
        try {
            prescriptionService.dispensePrescription(id);
            return "redirect:/admin/prescriptions?success";
        } catch (RuntimeException e) {
            return "redirect:/admin/prescriptions?stockError";
        }
    }
}