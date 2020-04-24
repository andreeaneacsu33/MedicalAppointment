package com.project.filtering;

import com.project.model.Affiliation;
import com.project.model.Doctor;

import java.util.List;

public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Doctor> filterStrategy(List<Affiliation> affiliations, List<String> criteria){
        return strategy.filter(affiliations,criteria);
    }
}
