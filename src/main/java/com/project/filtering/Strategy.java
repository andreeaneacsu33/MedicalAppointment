package com.project.filtering;

import com.project.model.Affiliation;
import com.project.model.Doctor;

import java.util.List;

public interface Strategy {

    List<Doctor> filter(List<Affiliation> affiliations, List<String> criteria);
}
