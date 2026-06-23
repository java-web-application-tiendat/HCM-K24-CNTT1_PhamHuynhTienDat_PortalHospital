package com.k24.hospital.controller;

import com.k24.hospital.entity.User;
import com.k24.hospital.entity.UserProfile;
import com.k24.hospital.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping({
            "/patient/profile",
            "/doctor/profile",
            "/admin/profile"
    })
    public String profilePage(Authentication authentication, Model model) {
        UserProfile profile = userProfileService.getOrCreateProfile(authentication.getName());

        model.addAttribute("user", profile.getUser());
        model.addAttribute("profile", profile);

        return "profile/profile";
    }

    @PostMapping({
            "/patient/profile/save",
            "/doctor/profile/save",
            "/admin/profile/save"
    })
    public String saveProfile(
            Authentication authentication,
            @Valid @ModelAttribute("profile") UserProfile profileForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            UserProfile profile = userProfileService.getOrCreateProfile(authentication.getName());
            model.addAttribute("user", profile.getUser());
            return "profile/profile";
        }

        User user = userProfileService.saveProfile(authentication.getName(), profileForm);

        if (user.getRole().name().equals("ADMIN")) {
            return "redirect:/admin/profile?saved";
        }

        if (user.getRole().name().equals("DOCTOR")) {
            return "redirect:/doctor/profile?saved";
        }

        return "redirect:/patient/profile?saved";
    }
}