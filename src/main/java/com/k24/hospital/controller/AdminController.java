package com.k24.hospital.controller;

import com.k24.hospital.service.AdminDashboardService;
import com.k24.hospital.service.SpecialtyService;
import com.k24.hospital.service.TestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminDashboardService adminDashboardService;
    private final SpecialtyService specialtyService;
    private final TestTypeService testTypeService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("dashboard", adminDashboardService.getDashboardData());
        return "admin/dashboard";
    }

    @GetMapping("/admin/specialties")
    public String specialties(Model model) {
        model.addAttribute("specialties", specialtyService.findAll());
        return "admin/specialties";
    }

    @GetMapping("/admin/test-types")
    public String testTypes(Model model) {
        model.addAttribute("testTypes", testTypeService.findAll());
        return "admin/test-types";
    }
}