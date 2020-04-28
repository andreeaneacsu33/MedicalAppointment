package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Specialty;
import com.project.service.impl.SpecialtyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SpecialtyController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private SpecialtyServiceImpl specialtyService;

    @GetMapping({"/specialties"})
    public ResponseEntity<List<Specialty>> getAllEpisodes(){
        List<Specialty> specialties = specialtyService.findSpecialties();
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved all specialties",SpecialtyController.class));
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    @RequestMapping(value = "/specialty/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecialty(@PathVariable int id){
        Specialty specialty= specialtyService.findSpecialty(id);
        if(specialty==null){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Specialties not found",SpecialtyController.class));
            return new ResponseEntity<>("Not found!", HttpStatus.NOT_FOUND);
        }
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved specialty",SpecialtyController.class));
        return new ResponseEntity<>(specialty,HttpStatus.OK);
    }
}
