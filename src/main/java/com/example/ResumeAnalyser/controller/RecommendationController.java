package com.example.ResumeAnalyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ResumeAnalyser.entity.SkillRecommendation;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.SkillRecommendationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecommendationController {

    @Autowired
    private SkillRecommendationService recommendationService;


    // =====================================================
    // ALL RECOMMENDATIONS
    // =====================================================

    @GetMapping("/recommendations")
    public String showRecommendations(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<SkillRecommendation> recommendations =
                recommendationService
                        .getAllRecommendations();

        model.addAttribute(
                "recommendations",
                recommendations
        );

        return "recommendations";
    }
}