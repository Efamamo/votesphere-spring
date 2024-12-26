package com.itsc.votesphere.polls;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itsc.votesphere.group.Group;
import com.itsc.votesphere.group.GroupRepository;
import com.itsc.votesphere.polls.dto.CreatePollDto;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;

@RestController
public class PollsController {

    @Autowired
    private PollsService pollsService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping("/polls")
    public ResponseEntity<Map<String, Object>> addPoll(@RequestBody @Valid CreatePollDto poll,HttpServletRequest request) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");

        String username = claims.getSubject(); 

        User user = userService.findUserByUsername(username);

        Map<String,Object> response = new HashMap<>();

       
        if (user == null){
            response.put("error", "Unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        }

        if (!user.getIsAdmin()){
            response.put("error", "You are not admon");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        int count = 0;
        for (String choice : poll.getChoices()) {
            if (choice != null && !choice.trim().isEmpty()) {
                count++;
            }
        }


        if (count < 1){
            response.put("choices", "Minimum of 2 choices are required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Group group = user.getMemberOf();



        if (group == null){
            response.put("group", "Group Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User u = group.getAdmin();

        if (u.getUsername() != user.getUsername()){
            response.put("error", "Group doesnt belong to you");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        System.out.println("passed the test");
    
        pollsService.addPoll(poll, group, user, poll.getChoices());
        response.put("success", "added poll successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }
    
    
}
