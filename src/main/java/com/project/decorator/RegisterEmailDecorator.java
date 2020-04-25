package com.project.decorator;

import com.project.model.Email;
import com.project.model.User;
import com.project.model.dto.UserDTO;

public class RegisterEmailDecorator extends EmailDecorator {
    private UserDTO userDTO;
    public RegisterEmailDecorator(IEmail decoratedEmail, UserDTO user) {
        super(decoratedEmail);
        this.userDTO=user;
    }

    @Override
    public Email getEmail() {
        Email email=decoratedEmail.getEmail();
        String decoratedSubject="[REGISTRATION EMAIL]: "+email.getSubject();
        String decoratedText="<p>\n" +
                "Hi, " +userDTO.getFirstName()+" "+userDTO.getLastName()+" and welcome to our service."+
                "</p>\n"+
                "<p>\n"+
                "We are happy to let you know that your registration completed successfully. Now you have "+
                "access in the section reserved to "+userDTO.getRole()+"s.\n"+
                "You can see your booked appointments, review your experience and many more. Let's make a difference in the medical appointment system!"+
                "</p>\n" +email.getText();
        email.setSubject(decoratedSubject);
        email.setText(decoratedText);
        email.setTo(userDTO.getEmail());
        return email;
    }
}
