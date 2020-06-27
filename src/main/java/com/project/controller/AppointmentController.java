package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;
import com.project.service.impl.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AppointmentController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private AppointmentServiceImpl appointmentService;

    @RequestMapping(value = "/appointment",method = RequestMethod.POST)
    public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO){
        try{
            Appointment appointment=appointmentService.save(appointmentDTO);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Saved appointment",AppointmentController.class));
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Save appointment failed with message: {1}",AppointmentController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointments/doctor/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getDoctorAppointments(@PathVariable int idDoctor){
        try{
            List<Appointment> appointments=appointmentService.findDoctorAppointments(idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's appointments",AppointmentController.class));
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's appointments failed with message: {1}",AppointmentController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointments/doctor/{idDoctor}/date/{currentDate}",method = RequestMethod.GET)
    public ResponseEntity<?> getDoctorAppointmentsWithDate(@PathVariable int idDoctor, @PathVariable String currentDate){
        try{
            List<Appointment> appointments=appointmentService.findDoctorAppointmentsWithDate(idDoctor,currentDate);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's appointments on date",AppointmentController.class));
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve doctor's appointments on date failed with message: {1}",AppointmentController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointments/patient/{idPatient}/date/{currentDate}",method = RequestMethod.GET)
    public ResponseEntity<?> getPatientAppointments(@PathVariable int idPatient, @PathVariable String currentDate){
        try{
            List<Appointment> appointments=appointmentService.findPatientAppointments(idPatient,currentDate);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved patient's appointments on date",AppointmentController.class));
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve patient's appointments on date failed with message: {1}",AppointmentController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointment/{idAppointment}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeAppointment(@PathVariable int idAppointment){
        try{
            Appointment appointment=appointmentService.removeAppointment(idAppointment);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Removed appointment",AppointmentController.class));
            return new ResponseEntity<>(appointment,HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Remove appointment failed with message: {1}",AppointmentController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/appointment", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAppointment(@RequestBody AppointmentDTO appointment){
        try{
            System.out.println(appointment);
            Appointment appointmentUpd=appointmentService.updateAppointment(appointment);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Updated appointment",AppointmentController.class));
            System.out.println(appointment);
            return new ResponseEntity<>(appointmentUpd, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Update appointment failed with message: {1}",AppointmentController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
