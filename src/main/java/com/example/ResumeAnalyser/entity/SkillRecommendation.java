package com.example.ResumeAnalyser.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_recommendations")
public class SkillRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skill;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String priority;

    private String resource;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public SkillRecommendation() {
    }


    // =====================================================
    // GETTERS AND SETTERS
    // =====================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}