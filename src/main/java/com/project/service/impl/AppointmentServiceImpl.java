package com.project.service.impl;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;
import com.project.persistence.impl.AffiliationRepository;
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

    private final AffiliationRepository repoAffiliaion;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repoAppointment, DoctorRepository repoDoctor, PatientRepository repoPatient, AffiliationRepository repoAffiliaion) {
        this.repoAppointment = repoAppointment;
        this.repoDoctor = repoDoctor;
        this.repoPatient = repoPatient;
        this.repoAffiliaion = repoAffiliaion;
    }

    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {
        try{
            Appointment appointment=new Appointment();
            appointment.setDoctor(repoDoctor.findOne(Integer.parseInt(appointmentDTO.getIdDoctor())));
            appointment.setPatient(repoPatient.findOne(Integer.parseInt(appointmentDTO.getIdPatient())));
            appointment.setAffiliation(repoAffiliaion.findOneById(Integer.parseInt(appointmentDTO.getIdAffiliation())));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
    public List<Appointment> findDoctorAppointments(int idDoctor) {
        List<Appointment> appointments=new ArrayList<>();
        repoAppointment.getAll().forEach(appointments::add);
        return appointments.stream().filter(app->app.getDoctor().getId()==idDoctor).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findPatientAppointments(int idPatient) {
        List<Appointment> appointments=new ArrayList<>();
        repoAppointment.getAll().forEach(appointments::add);
        return appointments.stream().filter(app->app.getPatient().getId()==idPatient).collect(Collectors.toList());
    }
}
