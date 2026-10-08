package com.example.ResumeAnalyser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ResumeAnalyser.entity.AnalysisHistory;
import com.example.ResumeAnalyser.entity.User;
import com.example.ResumeAnalyser.repository.AnalysisHistoryRepository;

@Service
public class AnalysisHistoryService {

    @Autowired
    private AnalysisHistoryRepository analysisHistoryRepository;


    // =====================================================
    // SAVE HISTORY
    // =====================================================

    public AnalysisHistory saveHistory(
            AnalysisHistory history) {

        return analysisHistoryRepository.save(history);
    }


    // =====================================================
    // GET ALL HISTORY
    // ADMIN
    // =====================================================

    public List<AnalysisHistory> getAllHistory() {

        return analysisHistoryRepository
                .findAllByOrderByAnalyzedAtDesc();
    }


    // =====================================================
    // GET HISTORY BY USER
    // USER
    // =====================================================

    public List<AnalysisHistory> getHistoryByUser(
            User user) {

        return analysisHistoryRepository
                .findAllByUserOrderByAnalyzedAtDesc(user);
    }


    // =====================================================
    // GET ONE HISTORY BY ID + USER
    // =====================================================

    public AnalysisHistory getHistoryById(
            Long id,
            User user) {

        return analysisHistoryRepository
                .findByIdAndUser(id, user);
    }


    // =====================================================
    // DELETE USER HISTORY
    // =====================================================

    public void deleteHistory(
            Long id,
            User user) {

        AnalysisHistory history =
                analysisHistoryRepository
                        .findByIdAndUser(id, user);

        if (history != null) {

            analysisHistoryRepository.delete(history);
        }
    }


    // =====================================================
    // DELETE HISTORY
    // ADMIN
    // =====================================================

    public void deleteById(Long id) {

        if (analysisHistoryRepository.existsById(id)) {

            analysisHistoryRepository.deleteById(id);
        }
    }


    // =====================================================
    // TOTAL ANALYSES
    // ADMIN DASHBOARD
    // =====================================================

    public long getTotalAnalyses() {

        return analysisHistoryRepository.count();
    }
}