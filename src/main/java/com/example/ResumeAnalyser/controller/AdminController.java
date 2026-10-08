package com.example.ResumeAnalyser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.AnalysisHistoryService;
import com.example.ResumeAnalyser.service.JobService;
import com.example.ResumeAnalyser.service.ResumeService;
import com.example.ResumeAnalyser.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AnalysisHistoryService analysisHistoryService;


    @GetMapping("/admin/dashboard")
    public String adminDashboard(
            HttpSession session,
            HttpServletResponse response,
            Model model) {

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


        // Get logged-in user
        User user =
                (User) session.getAttribute("loggedInUser");


        // Login check
        if (user == null) {
            return "redirect:/login";
        }


        // Admin role check
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard";
        }


        // Dashboard statistics
        long totalUsers =
                userService.getTotalUsers();

        long totalJobs =
                jobService.getTotalJobs();

        long totalResumes =
                resumeService.getTotalResumes();

        long totalAnalyses =
                analysisHistoryService.getTotalAnalyses();


        // Send data to Thymeleaf
        model.addAttribute(
                "totalUsers",
                totalUsers
        );

        model.addAttribute(
                "totalJobs",
                totalJobs
        );

        model.addAttribute(
                "totalResumes",
                totalResumes
        );

        model.addAttribute(
                "totalAnalyses",
                totalAnalyses
        );


        return "admin/dashboard";
    }
}