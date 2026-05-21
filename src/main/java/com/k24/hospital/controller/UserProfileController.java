package com.k24.hospital.controller;

import com.k24.hospital.entity.User;
import com.k24.hospital.entity.UserProfile;
import com.k24.hospital.repository.UserProfileRepository;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @GetMapping({
            "/patient/profile",
            "/doctor/profile",
            "/admin/profile"
    })
    public String profilePage(Authentication authentication, Model model) {

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        UserProfile profile = userProfileRepository
                .findByUser(user)
                .orElse(UserProfile.builder().user(user).build());

        model.addAttribute("user", user);
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
            @ModelAttribute UserProfile profileForm
    ) {
        User user = userRepository
                .findByUsername(authentication.getName())
                .orElse(null);

        UserProfile profile = userProfileRepository
                .findByUser(user)
                .orElse(new UserProfile());

        profile.setUser(user);
        profile.setFullName(profileForm.getFullName());
        profile.setPhone(profileForm.getPhone());
        profile.setAddress(profileForm.getAddress());
        profile.setDateOfBirth(profileForm.getDateOfBirth());
        profile.setGender(profileForm.getGender());

        userProfileRepository.save(profile);

        if (user.getRole().name().equals("ADMIN")) {
            return "redirect:/admin/profile?saved";
        }

        if (user.getRole().name().equals("DOCTOR")) {
            return "redirect:/doctor/profile?saved";
        }

        return "redirect:/patient/profile?saved";
    }
}