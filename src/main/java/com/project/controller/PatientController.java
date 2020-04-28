package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Patient;
import com.project.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PatientController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/patient/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String email){
        Patient patient= patientService.findPatient(email);
        if(patient==null){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Patient not found",PatientController.class));
            return new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND);
        }
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved patient",PatientController.class));
        return new ResponseEntity<>(patient,HttpStatus.OK);
    }
}
