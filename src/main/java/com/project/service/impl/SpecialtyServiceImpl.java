package com.project.service.impl;

import com.project.model.Specialty;
import com.project.persistence.impl.SpecialtyRepository;
import com.project.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository repoSpecialty;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository repoSpecialty) {
        this.repoSpecialty = repoSpecialty;
    }

    @Override
    public List<Specialty> findSpecialties() {
        List<Specialty> specialties=new ArrayList<>();
        repoSpecialty.getAll().forEach(specialties::add);
        return specialties;
    }

    @Override
    public Specialty findSpecialty(int id) {
        return repoSpecialty.findOne(id);
    }

}
