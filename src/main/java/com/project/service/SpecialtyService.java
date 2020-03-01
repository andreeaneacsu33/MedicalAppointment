package com.project.service;

import com.project.model.Specialty;

import java.util.List;

public interface SpecialtyService {
    List<Specialty> findSpecialties();
    Specialty findSpecialty(int id);
}
