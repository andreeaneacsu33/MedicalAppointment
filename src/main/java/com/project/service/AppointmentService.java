package com.project.service;

import com.project.model.Appointment;
import com.project.model.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    Appointment save(AppointmentDTO appointmentDTO);
    List<Appointment> findAppointments();
    List<Appointment> findAppointments(int idDoctor);
}