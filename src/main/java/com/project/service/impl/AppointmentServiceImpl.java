package com.project.service.impl;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;
import com.project.persistence.impl.AppointmentRepository;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;
import com.project.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    private final AppointmentRepository repoAppointment;

    private final DoctorRepository repoDoctor;

    private final PatientRepository repoPatient;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repoAppointment, DoctorRepository repoDoctor, PatientRepository repoPatient) {
        this.repoAppointment = repoAppointment;
        this.repoDoctor = repoDoctor;
        this.repoPatient = repoPatient;
    }

    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {
        try{
            Appointment appointment=new Appointment();
            appointment.setDoctor(repoDoctor.findOne(Integer.parseInt(appointmentDTO.getIdDoctor())));
            appointment.setPatient(repoPatient.findOne(Integer.parseInt(appointmentDTO.getIdPatient())));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC+0200"));
            Date startDate=df.parse(appointmentDTO.getStartDate());
            Date endDate=df.parse(appointmentDTO.getEndDate());
            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setTitle(appointmentDTO.getTitle());
            appointment.setNotes(appointmentDTO.getNotes());
            return repoAppointment.save(appointment);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> findAppointments() {
        List<Appointment> appointments=new ArrayList<>();
        repoAppointment.getAll().forEach(appointments::add);
        return appointments;
    }

    @Override
    public List<Appointment> findAppointments(int idDoctor) {
        List<Appointment> appointments=new ArrayList<>();
        repoAppointment.getAll().forEach(appointments::add);
        return appointments.stream().filter(app->app.getDoctor().getId()==idDoctor).collect(Collectors.toList());
    }
}
