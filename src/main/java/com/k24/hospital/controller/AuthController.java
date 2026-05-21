package com.k24.hospital.controller;

import com.k24.hospital.entity.User;
import com.k24.hospital.entity.UserProfile;
import com.k24.hospital.enums.Role;
//import com.k24.hospital.repository.UserProfileRepository;
import com.k24.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
//    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

        @GetMapping("/register")
        public String registerPage(Model model) {
            model.addAttribute("user", new User());
            return "auth/register";
        }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {

        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "auth/register";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã tồn tại");
            return "auth/register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PATIENT);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        UserProfile profile = UserProfile.builder()
                .fullName(user.getUsername())
                .user(savedUser)
                .build();

    //        userProfileRepository.save(profile);

        return "redirect:/login?registered";
    }
}