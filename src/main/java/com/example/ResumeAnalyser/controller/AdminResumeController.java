package com.example.ResumeAnalyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ResumeAnalyser.entity.Resume;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.ResumeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/admin/resumes")
    public String showAllResumes(
            Model model,
            HttpSession session) {

        User loggedInUser =
                (User) session.getAttribute("loggedInUser");

        // Login check
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Admin check
        if (!"ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            return "redirect:/dashboard";
        }

        List<Resume> resumes =
                resumeService.getAllResumes();

        model.addAttribute("resumes", resumes);

        return "admin/resumes";
    }
}