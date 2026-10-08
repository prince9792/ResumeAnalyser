package com.example.ResumeAnalyser.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ResumeAnalyser.entity.AnalysisHistory;
import com.example.ResumeAnalyser.entity.Job;
import com.example.ResumeAnalyser.entity.Resume;
import com.example.ResumeAnalyser.entity.SkillRecommendation;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.AnalysisHistoryService;
import com.example.ResumeAnalyser.service.AnalysisResult;
import com.example.ResumeAnalyser.service.JobService;
import com.example.ResumeAnalyser.service.ResumeAnalysisService;
import com.example.ResumeAnalyser.service.ResumeService;
import com.example.ResumeAnalyser.service.SkillExtractorService;
import com.example.ResumeAnalyser.service.SkillRecommendationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AnalysisController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SkillExtractorService skillExtractorService;

    @Autowired
    private ResumeAnalysisService resumeAnalysisService;

    @Autowired
    private AnalysisHistoryService analysisHistoryService;

    @Autowired
    private SkillRecommendationService recommendationService;


    // =========================================================
    // SHOW ANALYSIS PAGE
    // =========================================================

    @GetMapping("/analyze")
    public String showAnalysisPage(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("loggedInUser");

        // Login check
        if (user == null) {
            return "redirect:/login";
        }

        // Get only logged-in user's resumes
        List<Resume> resumes =
                resumeService.getResumesByUser(user);

        // Get all available jobs
        List<Job> jobs =
                jobService.getAllJobs();

        model.addAttribute("resumes", resumes);
        model.addAttribute("jobs", jobs);

        return "analyze-resume";
    }


    // =========================================================
    // ANALYZE RESUME
    // =========================================================

    @PostMapping("/analyze")
    public String analyzeResume(
            @RequestParam("resumeId") Long resumeId,
            @RequestParam("jobId") Long jobId,
            Model model,
            HttpSession session) {

        // Get logged-in user
        User user =
                (User) session.getAttribute("loggedInUser");

        // Login check
        if (user == null) {
            return "redirect:/login";
        }


        // -----------------------------------------------------
        // Find Resume
        // -----------------------------------------------------

        Resume resume =
                resumeService.getResumeById(resumeId);


        // -----------------------------------------------------
        // Find Job
        // -----------------------------------------------------

        Job job =
                jobService.getJobById(jobId);


        // -----------------------------------------------------
        // Check Resume / Job
        // -----------------------------------------------------

        if (resume == null || job == null) {

            model.addAttribute(
                    "error",
                    "Resume or Job not found!"
            );

            model.addAttribute(
                    "resumes",
                    resumeService.getResumesByUser(user)
            );

            model.addAttribute(
                    "jobs",
                    jobService.getAllJobs()
            );

            return "analyze-resume";
        }


        // -----------------------------------------------------
        // Security Check
        // User can only analyze his own resume
        // -----------------------------------------------------

        if (resume.getUser() == null
                || !resume.getUser()
                         .getId()
                         .equals(user.getId())) {

            model.addAttribute(
                    "error",
                    "You are not allowed to access this resume."
            );

            model.addAttribute(
                    "resumes",
                    resumeService.getResumesByUser(user)
            );

            model.addAttribute(
                    "jobs",
                    jobService.getAllJobs()
            );

            return "analyze-resume";
        }


        // -----------------------------------------------------
        // Extract Skills From Resume
        // -----------------------------------------------------

        List<String> resumeSkills =
                skillExtractorService.extractSkills(
                        resume.getExtractedText()
                );


        // -----------------------------------------------------
        // Analyze Resume According To Job
        // -----------------------------------------------------

        AnalysisResult result =
                resumeAnalysisService.analyzeResume(
                        resumeSkills,
                        job.getRequiredSkills()
                );


        // -----------------------------------------------------
        // Get Recommendations For Missing Skills
        // -----------------------------------------------------

        List<SkillRecommendation> recommendations =
                recommendationService
                        .getRecommendationsForSkills(
                                result.getMissingSkills()
                        );


        // =====================================================
        // SAVE ANALYSIS HISTORY
        // =====================================================

        AnalysisHistory history =
                new AnalysisHistory();

        history.setUser(user);

        history.setResumeFileName(
                resume.getFileName()
        );

        history.setJobTitle(
                job.getJobTitle()
        );

        history.setMatchPercentage(
                result.getMatchPercentage()
        );

        history.setMatchedSkills(
                String.join(
                        ", ",
                        result.getMatchedSkills()
                )
        );

        history.setMissingSkills(
                String.join(
                        ", ",
                        result.getMissingSkills()
                )
        );

        history.setAnalyzedAt(
                LocalDateTime.now()
        );

        // Save history into database
        analysisHistoryService.saveHistory(history);


        // =====================================================
        // SEND DATA TO RESULT PAGE
        // =====================================================

        model.addAttribute(
                "resume",
                resume
        );

        model.addAttribute(
                "job",
                job
        );

        model.addAttribute(
                "result",
                result
        );

        model.addAttribute(
                "resumeSkills",
                resumeSkills
        );

        model.addAttribute(
                "recommendations",
                recommendations
        );


        return "analysis-result";
    }


    // =========================================================
    // SHOW USER ANALYSIS HISTORY
    // =========================================================

    @GetMapping("/history")
    public String showHistory(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("loggedInUser");

        // Login check
        if (user == null) {
            return "redirect:/login";
        }


        // Get only logged-in user's history
        List<AnalysisHistory> histories =
                analysisHistoryService
                        .getHistoryByUser(user);


        model.addAttribute(
                "histories",
                histories
        );


        return "history";
    }


    // =========================================================
    // DELETE USER'S HISTORY
    // =========================================================

    @GetMapping("/history/delete")
    public String deleteHistory(
            @RequestParam("id") Long id,
            HttpSession session) {

        User user =
                (User) session.getAttribute("loggedInUser");

        // Login check
        if (user == null) {
            return "redirect:/login";
        }


        // Delete only the history belonging
        // to the logged-in user
        analysisHistoryService.deleteHistory(
                id,
                user
        );


        return "redirect:/history";
    }
}