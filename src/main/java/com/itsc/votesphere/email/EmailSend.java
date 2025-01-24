package com.itsc.votesphere.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSend {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to,String body, String subject){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ephrem.mamo@a2sv.org");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail Sent Successfully");
    }
    
}
