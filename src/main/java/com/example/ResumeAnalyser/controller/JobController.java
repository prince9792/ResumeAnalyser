package com.example.ResumeAnalyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ResumeAnalyser.entity.Job;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.JobService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    // =========================
    // CREATE JOB PAGE
    // =========================
    @GetMapping("/admin/job/create")
    public String showCreateJobPage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("job", new Job());

        return "create-job";
    }

    // =========================
    // SAVE JOB
    // =========================
    @PostMapping("/admin/job/save")
    public String saveJob(
            @Valid Job job,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard";
        }

        if (bindingResult.hasErrors()) {
            return "create-job";
        }

        jobService.saveJob(job);

        model.addAttribute("success", "Job created successfully!");
        model.addAttribute("job", new Job());

        return "create-job";
    }

    // =========================
    // VIEW ALL JOBS
    // =========================
    @GetMapping("/admin/jobs")
    public String showJobs(
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard";
        }

        List<Job> jobs = jobService.getAllJobs();

        model.addAttribute("jobs", jobs);

        return "jobs";
    }

    // =========================
    // DELETE JOB
    // =========================
    @PostMapping("/admin/job/delete")
    public String deleteJob(
            @RequestParam("id") Long id,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dashboard";
        }

        jobService.deleteJob(id);

        return "redirect:/admin/jobs";
    }
}