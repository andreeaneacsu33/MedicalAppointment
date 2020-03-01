package com.project.service;

import com.project.model.Qualification;
import com.project.model.dto.QualificationDTO;

import java.util.List;

public interface QualificationService {
    List<Qualification> findQualifications();
    Qualification save(QualificationDTO user);
    Qualification findQualification(int idDoctor);
}
