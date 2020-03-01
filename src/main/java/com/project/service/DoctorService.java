package com.project.service;

import com.project.model.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor findDoctor(String username);
    int findTotalPages();
    List<Doctor> findPaginated(int page);
    double findOverallRating(int idDoctor);
    double findOverallWaitingTime(int idDoctor);
}
