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
            return new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/page/{page}")
    public ResponseEntity<?> findPaginated(@PathVariable("page") int page){
        int totalPages=doctorService.findTotalPages();
        System.out.println(totalPages);
        if(page>totalPages || page<1){
            logger.log(AbstractLogger.ERROR, "Page not found!");
            return new ResponseEntity<>("Page not found!", HttpStatus.NOT_FOUND);
        }
        List<Doctor> doctors=doctorService.findPaginated(page);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctors/total")
    public ResponseEntity<?> findTotal(){
        try{
            int totalPages=doctorService.findTotalPages();
            return new ResponseEntity<>(totalPages, HttpStatus.OK);
        }catch (Exception e){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Retrieve total pages failed with message: {0}",e.getMessage()));
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/rating")
    public ResponseEntity<?> findOverallRating(@PathVariable int idDoctor){
        try {
            double rating = doctorService.findOverallRating(idDoctor);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception e){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Retrieve overall doctor's rating failed with message: {0}",e.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall rating!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/rating/statistics")
    public ResponseEntity<?> findOverallRatingStatistics(@PathVariable int idDoctor){
        try {
            Map<Double,Integer> rating = doctorService.findOverallRatingStatistics(idDoctor);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception e){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Retrieve doctor's rating statistics failed with message: {0}",e.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall rating statistics!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/waiting-time/statistics")
    public ResponseEntity<?> findOverallWaitingTimeStatistics(@PathVariable int idDoctor){
        try {
            Map<Integer,Integer> rating = doctorService.findOverallWaitingTimeStatistics(idDoctor);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception e){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Retrieve doctor's waiting time statistics failed with message: {0}",e.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall waiting time statistics!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/waiting-time")
    public ResponseEntity<?> findOverallWaitingTime(@PathVariable int idDoctor){
        try {
            double waitingTime=doctorService.findOverallWaitingTime(idDoctor);
            return new ResponseEntity<>(waitingTime, HttpStatus.OK);
        }catch (Exception e){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Retrieve overall doctor's waiting time failed with message: {0}",e.getMessage()));
            return new ResponseEntity<>("Error on retrieving overall waiting time!", HttpStatus.BAD_REQUEST);
        }
    }
}
