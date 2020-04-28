package com.project.service.adapter;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;
import com.project.persistence.impl.AffiliationRepository;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentObjectAdapter implements ObjectAdapter {
    private AffiliationRepository repoAffiliation;
    private DoctorRepository repoDoctor;
    private PatientRepository repoPatient;

    public AppointmentObjectAdapter(AffiliationRepository repoAffiliation, DoctorRepository repoDoctor, PatientRepository repoPatient) {
        this.repoAffiliation = repoAffiliation;
        this.repoDoctor = repoDoctor;
        this.repoPatient = repoPatient;
    }

    @Override
    public ModelObject convertFromClientToModel(ClientObject clientObject) {
        try {
            Appointment appointment = new Appointment();
            AppointmentDTO appointmentDTO = (AppointmentDTO) clientObject;
            appointment.setDoctor(repoDoctor.findOne(Integer.parseInt(appointmentDTO.getIdDoctor())));
            appointment.setPatient(repoPatient.findOne(Integer.parseInt(appointmentDTO.getIdPatient())));
            appointment.setAffiliation(repoAffiliation.findOneById(Integer.parseInt(appointmentDTO.getIdAffiliation())));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date startDate = df.parse(appointmentDTO.getStartDate());
            Date endDate = df.parse(appointmentDTO.getEndDate());
            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setTitle(appointmentDTO.getTitle());
            appointment.setNotes(appointmentDTO.getNotes());
            return appointment;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
