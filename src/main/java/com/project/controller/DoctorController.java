package com.project.controller;

import com.project.model.Doctor;
import com.project.model.User;
import com.project.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DoctorController {
    private final DoctorServiceImpl doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

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
            return new ResponseEntity<>("Error on retrieving pages!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/rating")
    public ResponseEntity<?> findOverallRating(@PathVariable int idDoctor){
        try {
            double rating = doctorService.findOverallRating(idDoctor);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error on retrieving overall rating!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/doctor/{idDoctor}/waiting-time")
    public ResponseEntity<?> findOverallWaitingTime(@PathVariable int idDoctor){
        try {
            double waitingTime=doctorService.findOverallWaitingTime(idDoctor);
            return new ResponseEntity<>(waitingTime, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error on retrieving overall waiting time!", HttpStatus.BAD_REQUEST);
        }
    }
}
