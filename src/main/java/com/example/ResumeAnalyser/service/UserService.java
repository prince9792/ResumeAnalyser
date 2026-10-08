package com.example.ResumeAnalyser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.repository.AnalysisHistoryRepository;
import com.example.ResumeAnalyser.repository.ResumeRepository;
import com.example.ResumeAnalyser.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private AnalysisHistoryRepository analysisHistoryRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();


    // ==========================================
    // REGISTER USER
    // ==========================================

    public User registerUser(User user) {

        String encryptedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        user.setRole("USER");

        return userRepository.save(user);
    }


    // ==========================================
    // FIND USER BY EMAIL
    // ==========================================

    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }


    // ==========================================
    // CHECK PASSWORD
    // ==========================================

    public boolean checkPassword(
            String enteredPassword,
            String encryptedPassword) {

        return passwordEncoder.matches(
                enteredPassword,
                encryptedPassword);
    }


    // ==========================================
    // TOTAL USERS
    // ==========================================

    public long getTotalUsers() {

        return userRepository.count();
    }


    // ==========================================
    // GET ALL USERS
    // ==========================================

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    // ==========================================
    // GET USER BY ID
    // ==========================================

    public User getUserById(Long id) {

        return userRepository.findById(id).orElse(null);
    }


    // ==========================================
    // UPDATE USER ROLE
    // ==========================================

    public boolean updateRole(Long id, String role) {

        User user = getUserById(id);

        if (user == null) {
            return false;
        }

        // Only USER or ADMIN allowed
        if (!role.equalsIgnoreCase("USER")
                && !role.equalsIgnoreCase("ADMIN")) {

            return false;
        }

        user.setRole(role.toUpperCase());

        userRepository.save(user);

        return true;
    }


    // ==========================================
    // DELETE USER
    // ==========================================

    @Transactional
    public boolean deleteUser(Long id) {

        User user = getUserById(id);

        if (user == null) {
            return false;
        }

        /*
         * First delete user's analysis history
         */
        analysisHistoryRepository.deleteAllByUser(user);


        /*
         * Then delete user's resumes
         */
        resumeRepository.deleteAllByUser(user);


        /*
         * Finally delete the user
         */
        userRepository.delete(user);

        return true;
    }

}