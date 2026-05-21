package com.k24.hospital.controller;

import com.k24.hospital.service.AdminDashboardService;
import com.k24.hospital.repository.SpecialtyRepository;
import com.k24.hospital.repository.TestTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminDashboardService adminDashboardService;
    private final SpecialtyRepository specialtyRepository;
    private final TestTypeRepository testTypeRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("dashboard", adminDashboardService.getDashboardData());
        return "admin/dashboard";
    }

    @GetMapping("/admin/specialties")
    public String specialties(Model model) {
        model.addAttribute("specialties", specialtyRepository.findAll());
        return "admin/specialties";
    }

    @GetMapping("/admin/test-types")
    public String testTypes(Model model) {
        model.addAttribute("testTypes", testTypeRepository.findAll());
        return "admin/test-types";
    }
}