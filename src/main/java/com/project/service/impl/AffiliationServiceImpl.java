package com.project.service.impl;

import com.project.model.Affiliation;
import com.project.model.Doctor;
import com.project.model.dto.AffiliationDTO;
import com.project.persistence.impl.AffiliationRepository;
import com.project.persistence.impl.DoctorRepository;
import com.project.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AffiliationServiceImpl implements AffiliationService {
    private final AffiliationRepository repoAffiliation;
    private final DoctorRepository repoDoctor;

    @Autowired
    public AffiliationServiceImpl(AffiliationRepository repoAffiliation, DoctorRepository repoDoctor) {
        this.repoAffiliation = repoAffiliation;
        this.repoDoctor = repoDoctor;
    }

    @Override
    public List<Affiliation> findAffiliations() {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        return affiliations;
    }

    @Override
    public Affiliation save(AffiliationDTO affiliationDTO) {
        try {
            Affiliation affiliation = new Affiliation();
            affiliation.setIdDoctor(repoDoctor.findOne(affiliationDTO.getEmail()));
            affiliation.setCity(affiliationDTO.getCity());
            affiliation.setCountry(affiliationDTO.getCountry());
            affiliation.setHospitalName(affiliationDTO.getHospitalName());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = df.parse(affiliationDTO.getStartDate());
            affiliation.setStartDate(startDate);
            return repoAffiliation.save(affiliation);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Affiliation findAffiliation(int idDoctor) {
        Doctor doc = repoDoctor.findOne(idDoctor);
        if (doc != null) {
            return repoAffiliation.findOne(doc.getId());
        }
        return null;
    }
}
