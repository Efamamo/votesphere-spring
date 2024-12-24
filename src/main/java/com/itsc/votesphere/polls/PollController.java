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
    public String getPolls(Model model) {
        model.addAttribute("polls", pollRepository.findAll());
        return "polls";
    }

    @GetMapping("/add")
    public String getAddPolls() {
        return "add_poll";
    }
}

