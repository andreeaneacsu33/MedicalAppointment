package com.project.service.impl;

import com.project.model.Affiliation;
import com.project.model.Doctor;
import com.project.persistence.impl.AffiliationRepository;
import com.project.service.FilterService;
import com.project.service.util.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private AffiliationRepository repoAffiliation;
    private static final int PAGE_SIZE=6;

    private List<Doctor> filterDoctorsFromCity(List<String> cities) {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        return affiliations.stream().filter(x -> cities.contains(x.getCity())).map(Affiliation::getIdDoctor).collect(Collectors.toList());
    }

    private List<Doctor> filterDoctorsFromHospital(List<String> hospitals) {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        return affiliations.stream().filter(x -> hospitals.contains(x.getHospitalName())).map(Affiliation::getIdDoctor).collect(Collectors.toList());
    }

    private List<Doctor> filterDoctorsFromCityAndHospital(List<String> cities, List<String> hospitals) {
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        List<Doctor> doctorsFromCity = affiliations.stream().filter(x -> cities.contains(x.getCity())).map(Affiliation::getIdDoctor).collect(Collectors.toList());
        List<Doctor> doctorsFromHospital = affiliations.stream().filter(x -> hospitals.contains(x.getHospitalName())).map(Affiliation::getIdDoctor).collect(Collectors.toList());
        return Stream.concat(doctorsFromCity.stream(), doctorsFromHospital.stream()).collect(Collectors.toList());
    }

    private List<String> convertVectorToList(String[] vector){
        return new ArrayList<>(Arrays.asList(vector));
    }


    @Override
    public int findTotalPagesForCityFilter(String[] cities) {
        List<String> citiesArray=convertVectorToList(cities);
        int totalSize=filterDoctorsFromCity(citiesArray).size();
        return (totalSize+PAGE_SIZE-1)/PAGE_SIZE;
    }

    @Override
    public int findTotalPagesForHospitalFilter(String[] hospitals) {
        List<String> hospitalsArray=convertVectorToList(hospitals);
        List<Doctor> doctors=filterDoctorsFromHospital(hospitalsArray);
        int totalSize=doctors.size();
        return (totalSize+PAGE_SIZE-1)/PAGE_SIZE;
    }

    @Override
    public int findTotalPagesForCityAndHospitalFilter(String[] cities, String[] hospitals) {
        List<String> citiesArray=convertVectorToList(cities);
        List<String> hospitalsArray=convertVectorToList(hospitals);
        int totalSize=filterDoctorsFromCityAndHospital(citiesArray,hospitalsArray).size();
        return (totalSize+PAGE_SIZE-1)/PAGE_SIZE;
    }

    @Override
    public List<Doctor> findPaginatedForCityFilter(int page, String[] cities) {
        List<String> citiesArray=convertVectorToList(cities);
        return filterDoctorsFromCity(citiesArray).stream().skip((page-1)*PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findPaginatedForHospitalFilter(int page, String[] hospitals) {
        List<String> hospitalsArray=convertVectorToList(hospitals);
        return filterDoctorsFromHospital(hospitalsArray).stream().skip((page-1)*PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findPaginatedForCityAndHospitalFilter(int page, String[] cities, String[] hospitals) {
        List<String> citiesArray=convertVectorToList(cities);
        List<String> hospitalsArray=convertVectorToList(hospitals);
        return filterDoctorsFromCityAndHospital(citiesArray,hospitalsArray).stream().skip((page-1)*PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }
}
