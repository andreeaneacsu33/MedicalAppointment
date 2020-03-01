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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private AppointmentRepository repoAppointment;

    @Autowired
    private DoctorRepository repoDoctor;

    @Autowired
    private PatientRepository repoPatient;

    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {
        try{
            Appointment appointment=new Appointment();
            appointment.setDoctor(repoDoctor.findOne(Integer.parseInt(appointmentDTO.getIdDoctor())));
            appointment.setPatient(repoPatient.findOne(Integer.parseInt(appointmentDTO.getIdPatient())));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date startDate=df.parse(appointmentDTO.getStartDate());
            Date endDate=df.parse(appointmentDTO.getEndDate());
            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setDescription(appointmentDTO.getDescription());
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
}
