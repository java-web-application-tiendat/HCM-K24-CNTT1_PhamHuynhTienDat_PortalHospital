package com.k24.hospital.controller;

import com.k24.hospital.entity.Medicine;
import com.k24.hospital.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicines", medicineService.findAll());
        return "admin/medicines";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "admin/medicine-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("medicine") Medicine medicine, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/medicine-form";
        }
        medicineService.save(medicine);
        return "redirect:/admin/medicines";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Medicine medicine = medicineService.findById(id);

        if (medicine == null) {
            return "redirect:/admin/medicines";
        }

        model.addAttribute("medicine", medicine);
        return "admin/medicine-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        medicineService.deleteById(id);
        return "redirect:/admin/medicines";
    }
}