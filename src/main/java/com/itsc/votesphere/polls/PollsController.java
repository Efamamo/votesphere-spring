package com.itsc.votesphere.polls;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class PollsController {

    @Autowired
    private PollsService pollsService;

    @PostMapping("/polls")
    public ResponseEntity<Map<String, Object>> addPoll(@RequestBody @Valid Poll poll) {
        int count = 0;
        for (String choice : poll.getChoices()) {
            if (choice != null && !choice.trim().isEmpty()) {
                count++;
            }
        }

        Map<String, Object> response = new HashMap<>();

        if (count < 1){
            response.put("choices", "Minimum of 2 choices are required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    
        Poll p = pollsService.addPoll(poll);
        response.put("success", p);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }
    
    
}
