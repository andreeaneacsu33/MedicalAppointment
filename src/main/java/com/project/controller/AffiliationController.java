package com.project.controller;

import com.project.model.Affiliation;
import com.project.model.dto.AffiliationDTO;
import com.project.service.impl.AffiliationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AffiliationController {
    private final AffiliationServiceImpl affiliationService;

    @Autowired
    public AffiliationController(AffiliationServiceImpl affiliationService) {
        this.affiliationService = affiliationService;
    }

    @RequestMapping(value = "/affiliation",method = RequestMethod.POST)
    public ResponseEntity<?> saveAffiliation(@RequestBody AffiliationDTO affiliationDTO){
        try{
            Affiliation affiliation=affiliationService.save(affiliationDTO);
            return new ResponseEntity<>(affiliation, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/affiliation/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getAffiliation(@PathVariable int idDoctor){
        try{
            List<Affiliation> affiliations=affiliationService.findAffiliations(idDoctor);
            return new ResponseEntity<>(affiliations, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/affiliation/cities", method = RequestMethod.GET)
    public ResponseEntity<?> getCities(){
        try{
            List<String> cities=affiliationService.findDistinctCities();
            return new ResponseEntity<>(cities,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/affiliation/hospitals", method = RequestMethod.GET)
    public ResponseEntity<?> getHospitals(){
        try{
            List<String> hospitals=affiliationService.findDistinctHospitals();
            return new ResponseEntity<>(hospitals,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
