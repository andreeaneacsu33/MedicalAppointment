package com.project.decorator;

import com.project.model.Email;
import com.project.model.dto.UserDTO;

public class EmailImpl implements IEmail {
    @Override
    public Email getEmail() {
        Email email=new Email();
        email.setSubject("MyDOC notification service");
        String text="<h4>\n" +
                "This is a notification message from MyDOC team!\n" +
                "</h4>\n" +
                "<span>\n" +
                "Thank you for your contribution to the medical appointment service."+
                "</span>\n" +
                "<br/>\n" +
                "<span>\n" +
                "We are glad that you are using our product."+
                "</span>\n" +
                "<br/>\n" +
                "<h4>\n" +
                "Kind regards from MyDOC team\n" +
                "</h4>";
        email.setText(text);
        return email;
    }
}
