package com.example.ResumeAnalyser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ResumeAnalyser.entity.SkillRecommendation;

public interface SkillRecommendationRepository
        extends JpaRepository<SkillRecommendation, Long> {

    List<SkillRecommendation>
    findAllByOrderBySkillAsc();
}