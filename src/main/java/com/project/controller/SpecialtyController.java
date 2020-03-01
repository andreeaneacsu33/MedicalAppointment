package com.project.controller;

import com.project.model.Specialty;
import com.project.service.impl.SpecialtyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SpecialtyController {
    private final SpecialtyServiceImpl specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyServiceImpl specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping({"/specialties"})
    public ResponseEntity<List<Specialty>> getAllEpisodes(){
        List<Specialty> specialties = specialtyService.findSpecialties();
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    @RequestMapping(value = "/specialty/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecialty(@PathVariable int id){
        Specialty specialty= specialtyService.findSpecialty(id);
        if(specialty==null){
            return new ResponseEntity<String>("Not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Specialty>(specialty,HttpStatus.OK);
    }
}
