package com.example.ResumeAnalyser.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.example.ResumeAnalyser.entity.Resume;
import com.example.ResumeAnalyser.entity.User;

import com.example.ResumeAnalyser.service.ResumeService;
import com.example.ResumeAnalyser.service.SkillExtractorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillExtractorService skillExtractorService;


    // =========================
    // Upload Page
    // =========================

    @GetMapping("/upload-resume")
    public String showUploadPage(
            HttpSession session) {

        User user =
                (User) session.getAttribute(
                        "loggedInUser");

        if (user == null) {

            return "redirect:/login";
        }

        return "upload-resume";
    }


    // =========================
    // Upload Resume
    // =========================

    @PostMapping("/upload-resume")
    public String uploadResume(

            @RequestParam("file")
            MultipartFile file,

            Model model,

            HttpSession session) {


        User user =
                (User) session.getAttribute(
                        "loggedInUser");


        // Login check
        if (user == null) {

            return "redirect:/login";
        }


        try {


            // Empty file check
            if (file.isEmpty()) {

                model.addAttribute(
                        "error",
                        "Please select a PDF file."
                );

                return "upload-resume";
            }


            // PDF check
            String fileName =
                    file.getOriginalFilename();


            if (fileName == null ||
                !fileName.toLowerCase()
                        .endsWith(".pdf")) {

                model.addAttribute(
                        "error",
                        "Only PDF files are allowed."
                );

                return "upload-resume";
            }


            // Save Resume With User
            Resume resume =
                    resumeService.uploadResume(
                            file,
                            user
                    );


            // Extract Skills
            List<String> skills =
                    skillExtractorService
                            .extractSkills(
                                    resume.getExtractedText()
                            );


            model.addAttribute(
                    "success",
                    "Resume uploaded successfully!"
            );


            model.addAttribute(
                    "fileName",
                    resume.getFileName()
            );


            model.addAttribute(
                    "extractedText",
                    resume.getExtractedText()
            );


            model.addAttribute(
                    "skills",
                    skills
            );


        } catch (IOException e) {

            model.addAttribute(
                    "error",
                    "Error while processing PDF: "
                    + e.getMessage()
            );
        }


        return "upload-resume";
    }
}