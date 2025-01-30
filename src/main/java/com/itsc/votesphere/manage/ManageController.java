package com.itsc.votesphere.manage;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsc.votesphere.group.Group;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ManageController {

    @Autowired
    private UserService userService;
   

    @RequestMapping("/profile")
    public String getProfile(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject();  

        User user = userService.findUserByUsername(username);


        if (user == null){
            response.sendRedirect("/auth/login");  
            return null;
        }
        

        if (!user.getIsVerified()){
            response.sendRedirect("/auth/otp");  
            return null;
        }

        Group userGroup = user.getGroup();


        model.addAttribute("name", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("group", user.getGroup().getGroupName());
        model.addAttribute("isAdmin", user.getIsAdmin());
        
        model.addAttribute("polls", userGroup.getPolls() != null ? userGroup.getPolls().size() : 0);
        



        return "profile";
    }
}
