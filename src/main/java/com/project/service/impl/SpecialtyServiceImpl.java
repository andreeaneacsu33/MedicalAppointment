package com.project.service.impl;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Specialty;
import com.project.persistence.impl.SpecialtyRepository;
import com.project.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private SpecialtyRepository repoSpecialty;

    @Override
    public List<Specialty> findSpecialties() {
        List<Specialty> specialties=new ArrayList<>();
        repoSpecialty.getAll().forEach(specialties::add);
        logger.log(AbstractLogger.INFO,"Retrieve all specialties.");
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Retrieved specialties",SpecialtyServiceImpl.class));
        return specialties;
    }

    @Override
    public Specialty findSpecialty(int id) {
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Specialty found",SpecialtyServiceImpl.class));
        return repoSpecialty.findOne(id);
    }

}
