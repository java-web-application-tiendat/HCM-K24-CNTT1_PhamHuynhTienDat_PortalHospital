package com.k24.hospital.controller;

import com.k24.hospital.entity.User;
import com.k24.hospital.enums.Role;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(Authentication authentication) {

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {

            return "index";
        }

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "index";
        }

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        if (user.getRole() == Role.DOCTOR) {
            return "redirect:/doctor/dashboard";
        }

        return "redirect:/patient/dashboard";
    }

    @GetMapping("/redirect-by-role")
    public String redirectByRole(Authentication authentication) {

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        if (user.getRole() == Role.DOCTOR) {
            return "redirect:/doctor/dashboard";
        }

        return "redirect:/patient/dashboard";
    }
}