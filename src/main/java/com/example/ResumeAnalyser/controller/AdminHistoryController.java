package com.example.ResumeAnalyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ResumeAnalyser.entity.AnalysisHistory;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.AnalysisHistoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminHistoryController {

    @Autowired
    private AnalysisHistoryService analysisHistoryService;

    // ==============================
    // SHOW ALL USERS ANALYSIS HISTORY
    // ==============================
    @GetMapping("/admin/history")
    public String showAllHistory(
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

        // Get all analysis history
        List<AnalysisHistory> historyList =
                analysisHistoryService.getAllHistory();

        System.out.println(
                "ADMIN HISTORY COUNT = "
                + historyList.size()
        );

        model.addAttribute(
                "historyList",
                historyList
        );

        return "admin/history";
    }

    // ==============================
    // DELETE ANALYSIS HISTORY
    // ==============================
    @PostMapping("/admin/history/delete")
    public String deleteHistory(
            @RequestParam("id") Long id,
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

        analysisHistoryService.deleteById(id);

        return "redirect:/admin/history";
    }
}