package com.example.ResumeAnalyser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ResumeAnalyser.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}