package com.example.ResumeAnalyser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ResumeAnalyser.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}