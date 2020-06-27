package com.project.service;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    Appointment save(AppointmentDTO appointmentDTO);
    List<Appointment> findAppointments();
    List<Appointment> findDoctorAppointments(int idDoctor);
    List<Appointment> findDoctorAppointmentsWithDate(int idDoctor,String currentDate);
    List<Appointment> findPatientAppointments(int idPatient,String currentDate);
    Appointment removeAppointment(int id);
    Appointment updateAppointment(AppointmentDTO appointmentDTO);
}
