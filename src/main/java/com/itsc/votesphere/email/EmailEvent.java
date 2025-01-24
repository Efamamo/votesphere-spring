package com.itsc.votesphere.email;
import org.springframework.context.ApplicationEvent;



public class EmailEvent extends ApplicationEvent {
    private final String to;
    private final String body;
    private final String subject;

    public EmailEvent(Object source, String to, String body, String subject) {
        super(source);
        this.to = to;
        this.body = body;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }
}
