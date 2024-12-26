package com.itsc.votesphere.polls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itsc.votesphere.users.UserService;
import com.itsc.votesphere.users.User;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/polls")
public class PollsPageController {
    

    @Autowired
    private PollRepository pollRepository;

     @Autowired
    private UserService userService ;

    @GetMapping("")
    public String getPolls(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");

        String username = claims.getSubject();  // Assuming "sub" is the subject claim

        

        User user = userService.findUserByUsername(username);

        if (user == null){
            response.sendRedirect("/auth/login");  // Redirecting to /auth/
            return null;

        }

       
     
        model.addAttribute("polls", pollRepository.findAll());
        return "polls";
    }

    @GetMapping("/add")
    public String getAddPolls(HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");

        String username = claims.getSubject(); 

        User user = userService.findUserByUsername(username);

       
        if (user == null){
            response.sendRedirect("/auth/login"); 
            return null;

        }

        if (!user.getIsAdmin()){
            response.sendRedirect("/polls");  
            return null;
        }
        

        return  "add_poll";
    }
}

