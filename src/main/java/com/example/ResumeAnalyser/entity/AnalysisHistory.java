package com.example.ResumeAnalyser.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "analysis_history")
public class AnalysisHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resumeFileName;

    private String jobTitle;

    private double matchPercentage;

    @Column(columnDefinition = "LONGTEXT")
    private String matchedSkills;

    @Column(columnDefinition = "LONGTEXT")
    private String missingSkills;

    private LocalDateTime analyzedAt;

    // ==============================
    // USER RELATION
    // ==============================

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // ==============================
    // CONSTRUCTOR
    // ==============================

    public AnalysisHistory() {
    }


    // ==============================
    // GETTERS AND SETTERS
    // ==============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public String getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(String matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public String getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(String missingSkills) {
        this.missingSkills = missingSkills;
    }

    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}