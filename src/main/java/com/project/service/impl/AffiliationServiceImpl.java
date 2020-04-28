package com.project.service.impl;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Affiliation;
import com.project.model.dto.AffiliationDTO;
import com.project.persistence.impl.AffiliationRepository;
import com.project.persistence.impl.DoctorRepository;
import com.project.service.AffiliationService;
import com.project.service.adapter.AffiliationObjectAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AffiliationServiceImpl implements AffiliationService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private AffiliationRepository repoAffiliation;

    @Autowired
    private DoctorRepository repoDoctor;

    @Override
    public List<Affiliation> findAffiliations() {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);

        return affiliations;
    }

    @Override
    public Affiliation save(AffiliationDTO affiliationDTO) {
        AffiliationObjectAdapter affiliationObjectAdapter = new AffiliationObjectAdapter(repoDoctor);
        Affiliation affiliation = (Affiliation) affiliationObjectAdapter.convertFromClientToModel(affiliationDTO);
        return repoAffiliation.save(affiliation);
    }

    @Override
    public List<Affiliation> findAffiliations(int idDoctor) {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        logger.log(AbstractLogger.INFO, "Retrieve all affiliations");
        return affiliations.stream().filter(affiliation -> affiliation.getIdDoctor().getId() == idDoctor).collect(Collectors.toList());
    }

    @Override
    public List<String> findDistinctCities() {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        return affiliations.stream().map(Affiliation::getCity).distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
    public List<String> findDistinctHospitals() {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        return affiliations.stream().map(Affiliation::getHospitalName).distinct().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

}
