package com.itsc.votesphere.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Main {

    @GetMapping("/")
    public String getHome() {
        return "Welcome to VoteSphere";
    }
    
}
