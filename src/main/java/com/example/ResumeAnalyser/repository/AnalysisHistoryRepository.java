package com.example.ResumeAnalyser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ResumeAnalyser.entity.AnalysisHistory;
import com.example.ResumeAnalyser.entity.User;

public interface AnalysisHistoryRepository
        extends JpaRepository<AnalysisHistory, Long> {

    List<AnalysisHistory> findAllByOrderByAnalyzedAtDesc();

    List<AnalysisHistory> findAllByUserOrderByAnalyzedAtDesc(User user);

    AnalysisHistory findByIdAndUser(Long id, User user);

    void deleteAllByUser(User user);
}