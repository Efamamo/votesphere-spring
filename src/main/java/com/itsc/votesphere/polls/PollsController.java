package com.itsc.votesphere.polls;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itsc.votesphere.group.Group;
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
    private ChoiceRepository choiceRepository;

    @Autowired
    private VoteRepository voteRepository;

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

    @PatchMapping("/polls/{id}")
    public ResponseEntity<Map<String, Object>> vote(@PathVariable Long id, @RequestBody Map<String, Long> body, HttpServletRequest request) throws IOException, java.io.IOException{
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

        Choice choice = choiceRepository.findById(body.get("choiceId")).orElse(null);

        if (choice == null){
            response.put("error", "Choice not found");
            return ResponseEntity.status(404).body(response);
        }

        if (choice.getPoll().getId() != poll.getId()){
            response.put("error", "Choice doesnt belong to the Poll");
            return ResponseEntity.status(409).body(response);
        }

        Vote userVote = null;

        for (Vote vote: user.getVotes()){
            if (vote.getPoll() == poll){
                userVote = vote;
                break;
            }
        }

        if (userVote != null){
            voteRepository.deleteById(userVote.getId());
        }

        Vote newVote = new Vote();
        newVote.setChoice(choice);
        newVote.setPoll(poll);
        newVote.setUser(user);

        voteRepository.save(newVote);

        response.put("success", "User Voted Successfully");
        return ResponseEntity.status(200).body(response);


        
    }
    
    
}
