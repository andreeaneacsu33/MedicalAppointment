package com.project.filtering;

import com.project.model.Affiliation;
import com.project.model.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class CityFilter implements Strategy {

    @Override
    public List<Doctor> filter(List<Affiliation> affiliations, List<String> criteria) {
        return affiliations.stream().filter(x -> criteria.contains(x.getCity())).map(Affiliation::getIdDoctor).collect(Collectors.toList());
    }
}
