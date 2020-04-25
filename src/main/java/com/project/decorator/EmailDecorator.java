package com.project.decorator;

import com.project.model.Email;
import com.project.model.User;

public abstract class EmailDecorator implements IEmail {
    protected IEmail decoratedEmail;

    public EmailDecorator(IEmail decoratedEmail){
        this.decoratedEmail=decoratedEmail;
    }

    public Email getEmail(){
        return decoratedEmail.getEmail();
    }

}
