package com.example.ResumeAnalyser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ResumeAnalyser.entity.Job;
import com.example.ResumeAnalyser.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Save Job
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // Get All Jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get Job By ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    // Delete Job
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // Total Jobs
    public long getTotalJobs() {
        return jobRepository.count();
    }
}