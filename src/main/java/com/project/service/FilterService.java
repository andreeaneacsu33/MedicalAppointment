package com.project.service;

import com.project.model.Doctor;
import com.project.service.util.FilterType;

import java.util.List;

public interface FilterService {
    int findTotalPagesForCityFilter(String[] cities);
    int findTotalPagesForHospitalFilter(String[] hospitals);
    int findTotalPagesForCityAndHospitalFilter(String[] cities,String[] hospitals);
    List<Doctor> findPaginatedForCityFilter(int page,String[] cities);
    List<Doctor> findPaginatedForHospitalFilter(int page,String[] hospitals);
    List<Doctor> findPaginatedForCityAndHospitalFilter(int page,String[] cities,String[] hospitals);
}
