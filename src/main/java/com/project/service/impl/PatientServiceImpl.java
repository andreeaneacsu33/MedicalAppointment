package com.project.service.impl;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Patient;
import com.project.persistence.impl.PatientRepository;
import com.project.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private PatientRepository repoPatient;

    @Override
    public Patient findPatient(String username) {
        return repoPatient.findOne(username);
    }
}
