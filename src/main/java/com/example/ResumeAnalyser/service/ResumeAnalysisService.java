package com.example.ResumeAnalyser.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ResumeAnalysisService {


    // =====================================================
    // ANALYZE RESUME
    // =====================================================

    public AnalysisResult analyzeResume(
            List<String> resumeSkills,
            String requiredSkillsText) {


        // =================================================
        // REQUIRED SKILLS
        // =================================================

        List<String> requiredSkills =
                Arrays.stream(
                        requiredSkillsText.split(",")
                )
                .map(String::trim)
                .filter(skill -> !skill.isEmpty())
                .toList();


        // =================================================
        // MATCHED AND MISSING SKILLS
        // =================================================

        List<String> matchedSkills =
                new ArrayList<>();

        List<String> missingSkills =
                new ArrayList<>();


        // =================================================
        // COMPARE SKILLS
        // =================================================

        for (String requiredSkill : requiredSkills) {

            boolean matched = false;


            for (String resumeSkill : resumeSkills) {

                if (resumeSkill.equalsIgnoreCase(
                        requiredSkill)) {

                    matched = true;
                    break;
                }
            }


            if (matched) {

                matchedSkills.add(
                        requiredSkill
                );

            } else {

                missingSkills.add(
                        requiredSkill
                );
            }
        }


        // =================================================
        // MATCH PERCENTAGE
        // =================================================

        double matchPercentage = 0;


        if (!requiredSkills.isEmpty()) {

            matchPercentage =
                    ((double) matchedSkills.size()
                    / requiredSkills.size())
                    * 100;
        }


        // Round to 2 decimal places

        matchPercentage =
                Math.round(
                        matchPercentage * 100.0
                ) / 100.0;


        // =================================================
        // SCORE
        // =================================================

        int score =
                (int) Math.round(matchPercentage);


        // =================================================
        // GRADE
        // =================================================

        String grade;

        if (score >= 90) {

            grade = "A+";

        } else if (score >= 80) {

            grade = "A";

        } else if (score >= 70) {

            grade = "B";

        } else if (score >= 60) {

            grade = "C";

        } else if (score >= 50) {

            grade = "D";

        } else {

            grade = "F";
        }


        // =================================================
        // PERFORMANCE
        // =================================================

        String performance;

        if (score >= 90) {

            performance = "Excellent";

        } else if (score >= 80) {

            performance = "Very Good";

        } else if (score >= 70) {

            performance = "Good";

        } else if (score >= 60) {

            performance = "Average";

        } else if (score >= 50) {

            performance = "Needs Improvement";

        } else {

            performance = "Poor";
        }


        // =================================================
        // RECOMMENDATION
        // =================================================

        String recommendation;


        if (score >= 90) {

            recommendation =
                    "Excellent resume match! "
                    + "Your resume contains almost all "
                    + "required skills.";

        } else if (score >= 80) {

            recommendation =
                    "Very strong match. "
                    + "Add the missing skills to make "
                    + "your resume even stronger.";

        } else if (score >= 70) {

            recommendation =
                    "Good match. "
                    + "Focus on learning and adding "
                    + "the missing skills.";

        } else if (score >= 60) {

            recommendation =
                    "Average match. "
                    + "Your resume needs more relevant "
                    + "skills for this job.";

        } else if (score >= 50) {

            recommendation =
                    "Your resume has limited matching "
                    + "skills. Consider improving your "
                    + "technical skill set.";

        } else {

            recommendation =
                    "Low match. "
                    + "You should add more job-relevant "
                    + "skills and projects.";
        }


        // =================================================
        // RETURN RESULT
        // =================================================

        return new AnalysisResult(
                matchedSkills,
                missingSkills,
                matchPercentage,
                score,
                grade,
                performance,
                recommendation
        );
    }
}