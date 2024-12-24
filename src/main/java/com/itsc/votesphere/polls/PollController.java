package com.itsc.votesphere.polls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping("")
    public String getPosts(Model model) {
        model.addAttribute("polls", pollRepository.findAll());
        return "polls";
    }
}

