package com.itsc.votesphere.comments;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.polls.PollsService;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

@RestController()
public class CommentController {
    @Autowired
    public UserService userService;

    @Autowired
    public PollsService pollsService;


    @Autowired
    public CommentService commentService;

    @PostMapping("/polls/{id}/comments")
    public ResponseEntity<Map<String, Object>> addComment(@PathVariable Long id, @RequestBody CommentData body, HttpServletRequest request) throws IOException, java.io.IOException{
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

        commentService.addComment(body.getContent(), poll, user);

        response.put("success", "Comment Added Successfully");
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/polls/{id}/comments/{comId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long id,@PathVariable Long comId,  HttpServletRequest request) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject(); 

        User user = userService.findUserByUsername(username);

        Map<String,Object> response = new HashMap<>();

        if (user == null){
            response.put("error", "User not found");
            return ResponseEntity.status(404).body(response);
        }

        Comment com = commentService.getCommentById(comId);

        if (com == null){
            response.put("error", "Comment not found");
            return ResponseEntity.status(404).body(response);
        }

        if (!com.getUser().equals(user)){
            response.put("error", "Comment doesnt belong to the user");
            return ResponseEntity.status(403).body(response);
        }

        Poll poll = pollsService.findPollById(id);
        if (poll == null){
            response.put("error", "Poll not found");
            return ResponseEntity.status(404).body(response);
        }

        if (!com.getPoll().equals(poll)){
            response.put("error", "Comment Doesnt belong to the poll");
            return ResponseEntity.status(400).body(response);
        }

        commentService.deleteComment(com);
        response.put("success", "Comment deleted successfully");
        return ResponseEntity.status(200).body(response);


    }
}
