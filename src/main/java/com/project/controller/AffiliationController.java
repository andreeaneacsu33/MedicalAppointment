package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Affiliation;
import com.project.model.dto.AffiliationDTO;
import com.project.service.impl.AffiliationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AffiliationController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private AffiliationServiceImpl affiliationService;

    @RequestMapping(value = "/affiliation",method = RequestMethod.POST)
    public ResponseEntity<?> saveAffiliation(@RequestBody AffiliationDTO affiliationDTO){
        try{
            Affiliation affiliation=affiliationService.save(affiliationDTO);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Save affiliation",AffiliationController.class));
            return new ResponseEntity<>(affiliation, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Save affiliation failed with message: {1}",AffiliationController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/affiliation/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getAffiliation(@PathVariable int idDoctor){
        try{
            List<Affiliation> affiliations=affiliationService.findAffiliations(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's affiliations",AffiliationController.class));
            return new ResponseEntity<>(affiliations, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's affiliations failed with message: {1}",AffiliationController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/affiliation/cities", method = RequestMethod.GET)
    public ResponseEntity<?> getCities(){
        try{
            List<String> cities=affiliationService.findDistinctCities();
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved all affiliation cities",AffiliationController.class));
            return new ResponseEntity<>(cities,HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve all affiliation cities failed with message: {1}",AffiliationController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/affiliation/hospitals", method = RequestMethod.GET)
    public ResponseEntity<?> getHospitals(){
        try{
            List<String> hospitals=affiliationService.findDistinctHospitals();
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved all affiliation hospitals",AffiliationController.class));
            return new ResponseEntity<>(hospitals,HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve all affiliation hospitals failed with message: {1}",AffiliationController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
