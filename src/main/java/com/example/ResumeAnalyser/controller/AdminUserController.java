package com.example.ResumeAnalyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminUserController {


    @Autowired
    private UserService userService;


    // ==========================================
    // SHOW ALL USERS
    // ==========================================

    @GetMapping("/admin/users")
    public String showUsers(
            Model model,
            HttpSession session) {


        User loggedInUser =
                (User) session.getAttribute("loggedInUser");


        // Login check
        if (loggedInUser == null) {

            return "redirect:/login";
        }


        // Admin check
        if (!"ADMIN".equalsIgnoreCase(
                loggedInUser.getRole())) {

            return "redirect:/dashboard";
        }


        // Get all users
        List<User> users =
                userService.getAllUsers();


        model.addAttribute("users", users);


        return "admin/users";
    }



    // ==========================================
    // UPDATE USER ROLE
    // ==========================================

    @PostMapping("/admin/users/update-role")
    public String updateRole(
            @RequestParam("id") Long id,
            @RequestParam("role") String role,
            HttpSession session) {


        User loggedInUser =
                (User) session.getAttribute("loggedInUser");


        // Login check
        if (loggedInUser == null) {

            return "redirect:/login";
        }


        // Admin check
        if (!"ADMIN".equalsIgnoreCase(
                loggedInUser.getRole())) {

            return "redirect:/dashboard";
        }


        // Admin cannot change own role
        if (loggedInUser.getId().equals(id)) {

            return "redirect:/admin/users?error=self";
        }


        // Update role
        userService.updateRole(id, role);


        return "redirect:/admin/users";
    }



    // ==========================================
    // DELETE USER
    // ==========================================

    @PostMapping("/admin/users/delete")
    public String deleteUser(
            @RequestParam("id") Long id,
            HttpSession session) {


        User loggedInUser =
                (User) session.getAttribute("loggedInUser");


        // Login check
        if (loggedInUser == null) {

            return "redirect:/login";
        }


        // Admin check
        if (!"ADMIN".equalsIgnoreCase(
                loggedInUser.getRole())) {

            return "redirect:/dashboard";
        }


        // Admin cannot delete himself
        if (loggedInUser.getId().equals(id)) {

            return "redirect:/admin/users?error=self";
        }


        // Delete user
        userService.deleteUser(id);


        return "redirect:/admin/users";
    }

}