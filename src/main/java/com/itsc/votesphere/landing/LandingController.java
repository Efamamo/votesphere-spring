package com.itsc.votesphere.landing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
    @GetMapping("/")   
    public String getLanding(){
        return "landing";
    } 

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }
}
