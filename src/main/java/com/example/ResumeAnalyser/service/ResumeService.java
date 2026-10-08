package com.example.ResumeAnalyser.service;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ResumeAnalyser.entity.Resume;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.repository.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    // Upload Resume
    public Resume uploadResume(MultipartFile file, User user)
            throws IOException {

        byte[] fileBytes = file.getBytes();

        try (PDDocument document = Loader.loadPDF(fileBytes)) {

            PDFTextStripper pdfTextStripper =
                    new PDFTextStripper();

            String extractedText =
                    pdfTextStripper.getText(document);

            Resume resume = new Resume();

            resume.setFileName(file.getOriginalFilename());
            resume.setExtractedText(extractedText);
            resume.setUser(user);

            return resumeRepository.save(resume);
        }
    }

    // Get resumes of logged-in user
    public List<Resume> getResumesByUser(User user) {
        return resumeRepository.findAllByUserOrderByIdDesc(user);
    }

    // Get resume by ID
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

    // Get all resumes - Admin
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    // Total resumes - Admin dashboard
    public long getTotalResumes() {
        return resumeRepository.count();
    }
}