package com.project.controller;

import com.project.model.Affiliation;
import com.project.model.Qualification;
import com.project.model.dto.QualificationDTO;
import com.project.service.impl.QualificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class QualificationController {
    private final QualificationServiceImpl qualificationService;

    @Autowired
    public QualificationController(QualificationServiceImpl qualificationService) {
        this.qualificationService = qualificationService;
    }

    @RequestMapping(value = "/qualification",method = RequestMethod.POST)
    public ResponseEntity<?> saveQualification(@RequestBody QualificationDTO qualificationDTO){
        try{
            Qualification qualification=qualificationService.save(qualificationDTO);
            return new ResponseEntity<Qualification>(qualification, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/qualification/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getQualification(@PathVariable int idDoctor){
        try{
            Qualification qualification=qualificationService.findQualification(idDoctor);
            return new ResponseEntity<Qualification>(qualification, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
