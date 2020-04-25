package com.project.decorator;

import com.project.model.Email;
import com.project.model.Patient;
import com.project.model.Review;
import com.project.model.User;
import com.project.model.dto.UserDTO;

public class ReviewEmailDecorator extends EmailDecorator {
    private Review review;
    private Patient patient;
    public ReviewEmailDecorator(IEmail decoratedEmail, Patient patient, Review review) {
        super(decoratedEmail);
        this.patient=patient;
        this.review=review;
    }

    @Override
    public Email getEmail() {
        Email email=super.getEmail();
        String decoratedSubject="[REVIEW EMAIL]: "+email.getSubject();
        String decoratedText="<p>\n" +
                "Hi, " +patient.getFirstName()+" "+patient.getLastName()+" and thank you for submitting your review."+
                "</p>\n"+
                "<p>\n"+
                "We are happy to let you know that dr. "+review.getIdDoctor().getFirstName()+" "+review.getIdDoctor().getLastName()+" will soon be able to see your opinion in an anonymous way in order to protect your identity.\n"+
                "You can see your review by accessing Home Page and click-ing on the review button of the doctor."+
                "We are thankful for your contribution to the doctor review system. Now other people can be guided by your thoughts!"+
                "</p>\n" +email.getText();
        email.setTo(patient.getEmail());
        email.setSubject(decoratedSubject);
        email.setText(decoratedText);
        return email;
    }


}
