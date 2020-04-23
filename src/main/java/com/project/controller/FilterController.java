package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Doctor;
import com.project.service.impl.FilterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FilterController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private FilterServiceImpl filterService;

    @RequestMapping(value = "/doctors/filter/cities/{cities}/total")
    public ResponseEntity<?> findTotalPagesForCitiesFilter(@PathVariable String[] cities){
        try{
            int totalPages=filterService.findTotalPagesForCityFilter(cities);
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctors/filter/hospitals/{hospitals}/total", method = RequestMethod.GET)
    public ResponseEntity<?> findTotalPagesForHospitalFilter(@PathVariable("hospitals") String[] hospitals){
        try{
            int totalPages=filterService.findTotalPagesForHospitalFilter(hospitals);
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctors/filter/cities/{cities}/hospitals/{hospitals}/total")
    public ResponseEntity<?> findTotalPagesForCitiesAndHospitalsFilter(@PathVariable String[] cities,@PathVariable String[] hospitals){
        try{
            int totalPages=filterService.findTotalPagesForCityAndHospitalFilter(cities,hospitals);
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctors/filter/cities/{cities}/page/{page}")
    public ResponseEntity<?> findPaginatedForCityFilter(@PathVariable String[] cities,@PathVariable int page){
        int totalPages=filterService.findTotalPagesForCityFilter(cities);
        if(page>totalPages || page<1){
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=filterService.findPaginatedForCityFilter(page,cities);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/filter/hospitals/{hospitals}/page/{page}")
    public ResponseEntity<?> findPaginatedForHospitalFilter(@PathVariable String[] hospitals,@PathVariable int page){
        int totalPages=filterService.findTotalPagesForHospitalFilter(hospitals);
        if(page>totalPages || page<1){
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=filterService.findPaginatedForHospitalFilter(page,hospitals);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/filter/cities/{cities}/hospitals/{hospitals}/page/{page}")
    public ResponseEntity<?> findPaginatedForCityAndHospitalFilter(@PathVariable String[] cities,@PathVariable String[] hospitals,@PathVariable int page){
        int totalPages=filterService.findTotalPagesForCityAndHospitalFilter(cities,hospitals);
        if(page>totalPages || page<1){
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=filterService.findPaginatedForCityAndHospitalFilter(page,cities,hospitals);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}
