package com.project.service;

import com.project.model.Doctor;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    Doctor findDoctor(String username);
    int findTotalPages();
    List<Doctor> findPaginated(int page);
    double findOverallRating(int idDoctor);
    Map<Double,Integer> findOverallRatingStatistics(int idDoctor);
    Map<Integer,Integer> findOverallWaitingTimeStatistics(int idDoctor);
    double findOverallWaitingTime(int idDoctor);
}
