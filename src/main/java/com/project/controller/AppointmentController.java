package com.project.controller;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;
import com.project.service.impl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointments/doctor/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getDoctorAppointments(@PathVariable int idDoctor){
        try{
            List<Appointment> appointments=appointmentService.findDoctorAppointments(idDoctor);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointments/patient/{idPatient}",method = RequestMethod.GET)
    public ResponseEntity<?> getPatientAppointments(@PathVariable int idPatient){
        try{
            List<Appointment> appointments=appointmentService.findPatientAppointments(idPatient);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
