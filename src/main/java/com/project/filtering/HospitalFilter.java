package com.project.filtering;

import com.project.model.Affiliation;
import com.project.model.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class HospitalFilter implements Strategy {
    @Override
    public List<Doctor> filter(List<Affiliation> affiliations, List<String> criteria) {
        return affiliations.stream().filter(x -> criteria.contains(x.getHospitalName())).map(Affiliation::getIdDoctor).collect(Collectors.toList());
    }
}
