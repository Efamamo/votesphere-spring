package com.itsc.votesphere.dislikes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.polls.PollsService;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DisikeController {
    @Autowired
    private UserService userService;

    @Autowired
    private PollsService pollsService;

    @Autowired
    private DislikeService dislikeService;

    @PatchMapping("/polls/{id}/dislike")
    public ResponseEntity<Map<String, Object>> like(@PathVariable Long id, HttpServletRequest request) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");

        String username = claims.getSubject(); 

        User user = userService.findUserByUsername(username);

        Map<String,Object> response = new HashMap<>();

        if (user == null){
            response.put("error", "User not found");
            return ResponseEntity.status(404).body(response);
        }

        Poll poll = pollsService.findPollById(id);


        if (poll == null){
            response.put("error", "Poll not found");
            return ResponseEntity.status(404).body(response);
        }

        if (poll.getGroup() != user.getMemberOf()){
            response.put("error", "User doesnt belong to this group");
            return ResponseEntity.status(401).body(response);
        }

        if (!dislikeService.dislike(poll, user)){
            response.put("error", "Error Occured while disliking");
            return ResponseEntity.status(400).body(response);
        }

        response.put("message", "Successfully disliked");
        return ResponseEntity.status(200).body(response);
    } 

    @PatchMapping("/polls/{id}/undislike")
    public ResponseEntity<Map<String, Object>> unlike(@PathVariable Long id, HttpServletRequest request) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");

        String username = claims.getSubject(); 

        User user = userService.findUserByUsername(username);

        Map<String,Object> response = new HashMap<>();

        if (user == null){
            response.put("error", "User not found");
            return ResponseEntity.status(404).body(response);
        }

        Poll poll = pollsService.findPollById(id);


        if (poll == null){
            response.put("error", "Poll not found");
            return ResponseEntity.status(404).body(response);
        }

        if (poll.getGroup() != user.getMemberOf()){
            response.put("error", "User doesnt belong to this group");
            return ResponseEntity.status(401).body(response);
        }

        if (!dislikeService.undislike(poll, user)){
            response.put("error", "Error Occured while undisliking");
            return ResponseEntity.status(400).body(response);
        }

        response.put("message", "Successfully undisliked");
        return ResponseEntity.status(200).body(response);
    } 
    
}
