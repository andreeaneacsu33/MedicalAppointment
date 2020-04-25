package com.project.service.impl;

import com.project.filtering.CityFilter;
import com.project.filtering.Context;
import com.project.filtering.FilterType;
import com.project.filtering.HospitalFilter;
import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Affiliation;
import com.project.model.Doctor;
import com.project.persistence.impl.AffiliationRepository;
import com.project.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilterServiceImpl implements FilterService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private AffiliationRepository repoAffiliation;
    private static final int PAGE_SIZE=6;

    private List<Doctor> filterDoctors(List<String> criteria, FilterType type){
        Context context=new Context();
        List<Affiliation> affiliations = new ArrayList<>();
        repoAffiliation.getAll().forEach(affiliations::add);
        if(type==FilterType.CITY){
            context.setStrategy(new CityFilter());
        }
        if(type==FilterType.HOSPITAL){
            context.setStrategy(new HospitalFilter());
        }
        return context.filterStrategy(affiliations,criteria);
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
        List<String> cityList=convertVectorToList(cities);
        int totalSize=filterDoctors(cityList,FilterType.CITY).size();
        return (totalSize+PAGE_SIZE-1)/PAGE_SIZE;
    }

    @Override
    public int findTotalPagesForHospitalFilter(String[] hospitals) {
        List<String> hospitalList=convertVectorToList(hospitals);
        List<Doctor> doctors=filterDoctors(hospitalList,FilterType.HOSPITAL);
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
        List<String> cityList=convertVectorToList(cities);
        return filterDoctors(cityList,FilterType.CITY).stream().skip((page-1)*PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findPaginatedForHospitalFilter(int page, String[] hospitals) {
        List<String> hospitalList=convertVectorToList(hospitals);
        return filterDoctors(hospitalList,FilterType.HOSPITAL).stream().skip((page-1)*PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findPaginatedForCityAndHospitalFilter(int page, String[] cities, String[] hospitals) {
        List<String> citiesArray=convertVectorToList(cities);
        List<String> hospitalsArray=convertVectorToList(hospitals);
        return filterDoctorsFromCityAndHospital(citiesArray,hospitalsArray).stream().skip((page-1)*PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }
}
