package com.example.ResumeAnalyser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    // =====================================================
    // REGISTER PAGE
    // =====================================================

    @GetMapping("/register")
    public String showRegisterPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }


    // =====================================================
    // REGISTER USER
    // =====================================================

    @PostMapping("/register")
    public String registerUser(
            @Valid User user,
            BindingResult bindingResult,
            Model model) {

        // Validation check
        if (bindingResult.hasErrors()) {
            return "register";
        }


        // Remove extra spaces and convert email to lowercase
        String email = user.getEmail()
                .trim()
                .toLowerCase();

        user.setEmail(email);


        // Check whether email already exists
        User existingUser =
                userService.findByEmail(email);

        if (existingUser != null) {

            model.addAttribute(
                    "error",
                    "Email already registered!"
            );

            return "register";
        }


        // Save user
        // Password will be encrypted using BCrypt
        // inside UserService
        userService.registerUser(user);


        model.addAttribute(
                "success",
                "Registration successful! You can now login."
        );

        // Empty form after successful registration
        model.addAttribute(
                "user",
                new User()
        );

        return "register";
    }


    // =====================================================
    // LOGIN PAGE
    // =====================================================

    @GetMapping("/login")
    public String showLoginPage() {

        return "login";
    }


    // =====================================================
    // LOGIN USER
    // =====================================================

    @PostMapping("/login")
    public String loginUser(
            String email,
            String password,
            Model model,
            HttpSession session) {

        // Clean email
        email = email.trim().toLowerCase();


        // Find user by email
        User user =
                userService.findByEmail(email);


        // Check email + password
        if (user != null
                && userService.checkPassword(
                        password,
                        user.getPassword())) {


            // Store logged-in user in session
            session.setAttribute(
                    "loggedInUser",
                    user
            );


            // Check role
            if ("ADMIN".equalsIgnoreCase(
                    user.getRole())) {

                return "redirect:/admin/dashboard";
            }


            // Normal USER
            return "redirect:/dashboard";
        }


        // Invalid login
        model.addAttribute(
                "error",
                "Invalid email or password!"
        );

        return "login";
    }


    // =====================================================
    // LOGOUT
    // =====================================================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // Destroy current session
        session.invalidate();

        // Go back to login page
        return "redirect:/login";
    }
}