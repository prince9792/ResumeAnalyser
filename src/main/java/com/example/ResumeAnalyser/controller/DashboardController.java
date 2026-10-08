package com.example.ResumeAnalyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(
            HttpSession session,
            HttpServletResponse response) {

        // Prevent browser caching
        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        response.setHeader(
                "Pragma",
                "no-cache"
        );

        response.setDateHeader(
                "Expires",
                0
        );

        // Login check
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        return "dashboard";
    }
}