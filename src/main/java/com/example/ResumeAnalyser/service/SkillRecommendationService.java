package com.example.ResumeAnalyser.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ResumeAnalyser.entity.SkillRecommendation;
import com.example.ResumeAnalyser.repository.SkillRecommendationRepository;

@Service
public class SkillRecommendationService {

    @Autowired
    private SkillRecommendationRepository recommendationRepository;

    // =====================================================
    // GET ALL RECOMMENDATIONS
    // =====================================================

    public List<SkillRecommendation> getAllRecommendations() {

        return recommendationRepository
                .findAllByOrderBySkillAsc();
    }


    // =====================================================
    // GET RECOMMENDATION FOR ONE SKILL
    // =====================================================

    public SkillRecommendation getRecommendation(
            String skill) {

        List<SkillRecommendation> recommendations =
                recommendationRepository.findAll();

        for (SkillRecommendation recommendation
                : recommendations) {

            if (recommendation.getSkill()
                    .equalsIgnoreCase(skill)) {

                return recommendation;
            }
        }

        return createDefaultRecommendation(skill);
    }


    // =====================================================
    // GET RECOMMENDATIONS FOR MISSING SKILLS
    // =====================================================

    public List<SkillRecommendation>
    getRecommendationsForSkills(
            List<String> missingSkills) {

        List<SkillRecommendation> result =
                new ArrayList<>();

        if (missingSkills == null ||
                missingSkills.isEmpty()) {

            return result;
        }

        for (String skill : missingSkills) {

            if (skill == null ||
                    skill.isBlank()) {

                continue;
            }

            SkillRecommendation recommendation =
                    getRecommendation(skill);

            result.add(recommendation);
        }

        return result;
    }


    // =====================================================
    // DEFAULT RECOMMENDATION
    // =====================================================

    private SkillRecommendation
    createDefaultRecommendation(
            String skill) {

        SkillRecommendation recommendation =
                new SkillRecommendation();

        recommendation.setSkill(skill);

        recommendation.setDescription(
                "Improve your knowledge of "
                + skill
                + " through practical projects "
                + "and hands-on practice."
        );

        recommendation.setPriority("MEDIUM");

        recommendation.setResource(
                "Practice "
                + skill
                + " by building real-world projects."
        );

        return recommendation;
    }
}