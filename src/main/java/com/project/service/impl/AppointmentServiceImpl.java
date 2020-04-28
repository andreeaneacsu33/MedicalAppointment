package com.project.service.impl;

import com.project.decorator.*;
import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Appointment;
import com.project.model.Email;
import com.project.model.Patient;
import com.project.model.User;
import com.project.model.dto.AppointmentDTO;
import com.project.model.dto.UserDTO;
import com.project.persistence.impl.*;
import com.project.service.AppointmentService;
import com.project.service.adapter.AppointmentObjectAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private AppointmentRepository repoAppointment;

    @Autowired
    private DoctorRepository repoDoctor;

    @Autowired
    private PatientRepository repoPatient;

    @Autowired
    private AffiliationRepository repoAffiliation;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {
        AppointmentObjectAdapter aoAdapter = new AppointmentObjectAdapter(repoAffiliation, repoDoctor, repoPatient);
        Appointment appointment = (Appointment) aoAdapter.convertFromClientToModel(appointmentDTO);
        Appointment appointmentSaved = repoAppointment.save(appointment);
        sendEmail(appointment.getPatient(), appointmentSaved);
        return appointmentSaved;
    }

    @Override
    public List<Appointment> findAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        repoAppointment.getAll().forEach(appointments::add);
        return appointments;
    }

    @Override
    public List<Appointment> findDoctorAppointments(int idDoctor) {
        List<Appointment> appointments = new ArrayList<>();
        repoAppointment.getAll().forEach(appointments::add);
        return appointments.stream().filter(app -> app.getDoctor().getId() == idDoctor).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findDoctorAppointmentsWithDate(int idDoctor, String currentDate) {
        try {
            List<Appointment> appointments = new ArrayList<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date current = df.parse(currentDate);
            repoAppointment.getAll().forEach(appointments::add);
            return appointments.stream().filter(app -> app.getDoctor().getId() == idDoctor &&
                    app.getStartDate().getYear() == current.getYear() &&
                    app.getStartDate().getDate() == current.getDate() &&
                    app.getStartDate().getMonth() == current.getMonth() &&
                    app.getStartDate().getTime() > current.getTime()).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> findPatientAppointments(int idPatient, String currentDate) {
        try {
            List<Appointment> appointments = new ArrayList<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC+0300"));
            Date current = df.parse(currentDate);
            repoAppointment.getAll().forEach(appointments::add);
            return appointments.stream().filter(app -> app.getPatient().getId() == idPatient && app.getStartDate().compareTo(current) > 0).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Appointment removeAppointment(int id) {
        try {
            Appointment appointment = repoAppointment.findOne(id);
            repoAppointment.delete(appointment);
            return appointment;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void sendEmail(Patient patient, Appointment appointment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            IEmail email = new AppointmentEmailDecorator(new EmailImpl(), patient, appointment);
            Email emailContent = email.getEmail();
            helper.setTo(emailContent.getTo());
            helper.setSubject(emailContent.getSubject());
            message.setContent(emailContent.getText(), "text/html");
            emailSender.send(message);
            System.out.println("Email successfully sent!");
        } catch (MessagingException ex) {
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Send email failed with message: {0}", ex.getMessage()));
            ex.printStackTrace();
        }
    }
}
