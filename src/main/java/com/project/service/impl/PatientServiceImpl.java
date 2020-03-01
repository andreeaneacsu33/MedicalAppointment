package com.project.service.impl;

import com.project.model.Patient;
import com.project.persistence.impl.PatientRepository;
import com.project.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repoPatient;

    @Autowired
    public PatientServiceImpl(PatientRepository repoPatient) {
        this.repoPatient = repoPatient;
    }

    @Override
    public Patient findPatient(String username) {
        return repoPatient.findOne(username);
    }
}
