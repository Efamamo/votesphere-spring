package com.itsc.votesphere.contact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itsc.votesphere.email.EmailEvent;
import com.itsc.votesphere.polls.dto.CreatePollDto;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class ContactController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody @Valid ContactMessage body) throws IOException, java.io.IOException{

        Map<String,Object> response = new HashMap<>();
        EmailEvent emailEvent = new EmailEvent(this, "ephrem.mamo@a2sv.org", body.getName() + " Said " + body.getMessage()  , "From The User", body.getEmail());
        applicationEventPublisher.publishEvent(emailEvent);

        response.put("success", "You send the message successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
