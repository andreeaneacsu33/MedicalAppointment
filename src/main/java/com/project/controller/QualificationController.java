package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Affiliation;
import com.project.model.Qualification;
import com.project.model.dto.QualificationDTO;
import com.project.service.impl.QualificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class QualificationController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private QualificationServiceImpl qualificationService;

    @RequestMapping(value = "/qualification",method = RequestMethod.POST)
    public ResponseEntity<?> saveQualification(@RequestBody QualificationDTO qualificationDTO){
        try{
            Qualification qualification=qualificationService.save(qualificationDTO);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Saved qualification",QualificationController.class));
            return new ResponseEntity<>(qualification, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Save qualification failed with message: {1}",QualificationController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/qualification/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getQualification(@PathVariable int idDoctor){
        try{
            Qualification qualification=qualificationService.findQualification(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's qualification",QualificationController.class));
            return new ResponseEntity<>(qualification, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's qualification failed with message: {1}",QualificationController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
