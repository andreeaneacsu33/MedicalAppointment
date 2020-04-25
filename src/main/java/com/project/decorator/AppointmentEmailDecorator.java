package com.project.decorator;

import com.project.model.Appointment;
import com.project.model.Email;
import com.project.model.Patient;

public class AppointmentEmailDecorator extends EmailDecorator {
    private Appointment appointment;
    private Patient patient;

    public AppointmentEmailDecorator(IEmail decoratedEmail, Patient patient, Appointment appointment) {
        super(decoratedEmail);
        this.appointment=appointment;
        this.patient=patient;
    }

    @Override
    public Email getEmail() {
        Email email=decoratedEmail.getEmail();
        String decoratedSubject="[APPOINTMENT EMAIL]: "+email.getSubject();
        String decoratedText="<p>\n" +
                "Hi, " +patient.getFirstName()+" "+patient.getLastName()+" and thank you for booking your appointment."+
                "</p>\n"+
                "<p>\n"+
                "We are happy to let you know that your appointment was successfully booked. Dr. "+appointment.getDoctor().getFirstName()+" "+appointment.getDoctor().getLastName()+" will be waiting for you on "+appointment.getStartDate()+".\n"+
                "After your appointment you can review your experience by adding a review to your doctor."+
                "By doing so, other people will have the chance to be guided in making a decision.\n"+
                "In case you can no longer reach your appointment on the specified date, you can delete it by accessing the Upcoming appointments page."+
                "</p>\n" +email.getText();
        email.setSubject(decoratedSubject);
        email.setText(decoratedText);
        email.setTo(patient.getEmail());
        return email;
    }
}
