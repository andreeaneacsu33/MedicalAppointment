package com.project.controller;

import com.project.model.Patient;
import com.project.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping(value = "/patient/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String email){
        Patient patient= patientService.findPatient(email);
        if(patient==null){
            return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Patient>(patient,HttpStatus.OK);
    }
}
