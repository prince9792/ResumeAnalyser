package com.example.ResumeAnalyser.service;

import java.util.List;

public class AnalysisResult {

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private double matchPercentage;

    private int score;

    private String grade;

    private String performance;

    private String recommendation;


    // =====================================================
    // DEFAULT CONSTRUCTOR
    // =====================================================

    public AnalysisResult() {
    }


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public AnalysisResult(
            List<String> matchedSkills,
            List<String> missingSkills,
            double matchPercentage,
            int score,
            String grade,
            String performance,
            String recommendation) {

        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.matchPercentage = matchPercentage;
        this.score = score;
        this.grade = grade;
        this.performance = performance;
        this.recommendation = recommendation;
    }


    // =====================================================
    // GETTERS AND SETTERS
    // =====================================================

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(
            List<String> matchedSkills) {

        this.matchedSkills = matchedSkills;
    }


    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(
            List<String> missingSkills) {

        this.missingSkills = missingSkills;
    }


    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(
            double matchPercentage) {

        this.matchPercentage = matchPercentage;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }


    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(
            String recommendation) {

        this.recommendation = recommendation;
    }
}