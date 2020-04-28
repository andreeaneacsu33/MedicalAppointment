package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Doctor;
import com.project.model.User;
import com.project.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DoctorController {
    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private DoctorServiceImpl doctorService;

    @RequestMapping(value = "/doctor/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String email){
        Doctor doctor= doctorService.findDoctor(email);
        if(doctor==null){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor failed",DoctorController.class));
            return new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND);
        }
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor",DoctorController.class));
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/page/{page}")
    public ResponseEntity<?> findPaginated(@PathVariable("page") int page){
        int totalPages=doctorService.findTotalPages();
        System.out.println(totalPages);
        if(page>totalPages || page<1){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's page failed",DoctorController.class));
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=doctorService.findPaginated(page);
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctors from page {1}",DoctorController.class, page));
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/total")
    public ResponseEntity<?> findTotal(){
        try{
            int totalPages=doctorService.findTotalPages();
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's total pages",DoctorController.class));
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's total pages failed with message: {1}",DoctorController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/rating")
    public ResponseEntity<?> findOverallRating(@PathVariable int idDoctor){
        try {
            double rating = doctorService.findOverallRating(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's rating",DoctorController.class));
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve overall doctor's rating failed with message: {1}",DoctorController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall rating!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/rating/statistics")
    public ResponseEntity<?> findOverallRatingStatistics(@PathVariable int idDoctor){
        try {
            Map<Double,Integer> rating = doctorService.findOverallRatingStatistics(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's rating statistics",DoctorController.class));
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's rating statistics failed with message: {1}",DoctorController .class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall rating statistics!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/waiting-time/statistics")
    public ResponseEntity<?> findOverallWaitingTimeStatistics(@PathVariable int idDoctor){
        try {
            Map<Integer,Integer> rating = doctorService.findOverallWaitingTimeStatistics(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's waiting time statistics",DoctorController.class));
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's waiting time statistics failed with message: {1}",DoctorController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall waiting time statistics!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/waiting-time")
    public ResponseEntity<?> findOverallWaitingTime(@PathVariable int idDoctor){
        try {
            double waitingTime=doctorService.findOverallWaitingTime(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved overall doctor's waiting time",DoctorController.class));
            return new ResponseEntity<>(waitingTime, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve overall doctor's waiting time failed with message: {1}",DoctorController.class,ex.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall waiting time!", HttpStatus.BAD_REQUEST);
        }
    }
}
