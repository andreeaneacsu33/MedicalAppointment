package com.project.service;

import com.project.model.Affiliation;
import com.project.model.Doctor;
import com.project.model.dto.AffiliationDTO;

import java.util.List;

public interface AffiliationService {
    List<Affiliation> findAffiliations();
    Affiliation save(AffiliationDTO affiliationDTO);
    List<Affiliation> findAffiliations(int idDoctor);
    List<String> findDistinctCities();
    List<String> findDistinctHospitals();
}
