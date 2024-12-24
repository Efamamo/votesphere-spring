package com.itsc.votesphere.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthPageController {
     @GetMapping("/auth/signup")
    public String signupPage() {
        return "signup"; 
    }

    @GetMapping("/auth/login")
    public String loginPage() {

        return "login"; 
    }

}
