package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Doctor;
import com.project.service.impl.FilterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
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
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved number of total pages of doctors filtered by city",FilterController.class));
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve number of total pages of doctors filtered by city failed with message: {1}",FilterController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctors/filter/hospitals/{hospitals}/total", method = RequestMethod.GET)
    public ResponseEntity<?> findTotalPagesForHospitalFilter(@PathVariable("hospitals") String[] hospitals){
        try{
            int totalPages=filterService.findTotalPagesForHospitalFilter(hospitals);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved number of total pages of doctors filtered hospital",FilterController.class));
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve number of total pages of doctors filtered by hospital failed with message: {1}",FilterController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctors/filter/cities/{cities}/hospitals/{hospitals}/total")
    public ResponseEntity<?> findTotalPagesForCitiesAndHospitalsFilter(@PathVariable String[] cities,@PathVariable String[] hospitals){
        try{
            int totalPages=filterService.findTotalPagesForCityAndHospitalFilter(cities,hospitals);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved number of total pages of doctors filtered by city and hospital",FilterController.class));
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve number of total pages of doctors filtered by city and hospital failed with message: {1}",FilterController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctors/filter/cities/{cities}/page/{page}")
    public ResponseEntity<?> findPaginatedForCityFilter(@PathVariable String[] cities,@PathVariable int page){
        int totalPages=filterService.findTotalPagesForCityFilter(cities);
        if(page>totalPages || page<1){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieving doctors filtered by city on page {1} failed",FilterController.class,page));
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=filterService.findPaginatedForCityFilter(page,cities);
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctors filtered by city on page {1}",FilterController.class,page));
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/filter/hospitals/{hospitals}/page/{page}")
    public ResponseEntity<?> findPaginatedForHospitalFilter(@PathVariable String[] hospitals,@PathVariable int page){
        int totalPages=filterService.findTotalPagesForHospitalFilter(hospitals);
        if(page>totalPages || page<1){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieved doctors filtered by hospital on page {1} failed",FilterController.class,page));
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=filterService.findPaginatedForHospitalFilter(page,hospitals);
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctors filtered by hospital on page {1}",FilterController.class,page));
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/filter/cities/{cities}/hospitals/{hospitals}/page/{page}")
    public ResponseEntity<?> findPaginatedForCityAndHospitalFilter(@PathVariable String[] cities,@PathVariable String[] hospitals,@PathVariable int page){
        int totalPages=filterService.findTotalPagesForCityAndHospitalFilter(cities,hospitals);
        if(page>totalPages || page<1){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieved doctors filtered by city and hospital on page {1} failed",FilterController.class,page));
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=filterService.findPaginatedForCityAndHospitalFilter(page,cities,hospitals);
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctors filtered by city and hospital on page {1}",FilterController.class,page));
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}
