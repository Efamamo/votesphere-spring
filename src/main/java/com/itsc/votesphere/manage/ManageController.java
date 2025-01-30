package com.itsc.votesphere.manage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ManageController {
    @RequestMapping("/reset-password")
    public String resetPassword(){
        return "reset-password";
    }

    @RequestMapping("/settings")
    public String settings(){
        return "settings";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }
}
