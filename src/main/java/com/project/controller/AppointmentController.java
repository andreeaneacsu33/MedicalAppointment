package com.project.controller;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;
import com.project.service.impl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    @Autowired
    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @RequestMapping(value = "/appointment",method = RequestMethod.POST)
    public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO){
        try{
            Appointment appointment=appointmentService.save(appointmentDTO);
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
