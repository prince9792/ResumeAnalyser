package com.example.ResumeAnalyser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ResumeAnalyser.entity.Resume;
import com.example.ResumeAnalyser.entity.User;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findAllByUserOrderByIdDesc(User user);

    void deleteAllByUser(User user);
}