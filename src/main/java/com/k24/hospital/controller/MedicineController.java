package com.k24.hospital.controller;

import com.k24.hospital.entity.Medicine;
import com.k24.hospital.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/medicines")
public class MedicineController {

    private final MedicineRepository medicineRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("medicines", medicineRepository.findAll());
        return "admin/medicines";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "admin/medicine-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Medicine medicine) {
        medicineRepository.save(medicine);
        return "redirect:/admin/medicines";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Medicine medicine = medicineRepository.findById(id).orElse(null);

        if (medicine == null) {
            return "redirect:/admin/medicines";
        }

        model.addAttribute("medicine", medicine);
        return "admin/medicine-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        medicineRepository.deleteById(id);
        return "redirect:/admin/medicines";
    }
}