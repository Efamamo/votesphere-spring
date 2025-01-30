package com.itsc.votesphere.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EmailEventListener {

    @Autowired
    private EmailSend emailService;

    @EventListener
    public void handleEmailEvent(EmailEvent event) {
        emailService.sendEmail(event.getTo(), event.getBody(), event.getSubject(), event.getFrom());
    }
}

