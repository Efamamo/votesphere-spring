package com.itsc.votesphere.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsc.votesphere.users.UserService;
import com.itsc.votesphere.users.User;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/groups")
public class GroupsPageController {
    

     @Autowired
    private UserService userService ;

    @GetMapping("/add")
    public String getAddGroup(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
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


        return "add_group";
    }

   
}

