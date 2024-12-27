package com.itsc.votesphere.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private GroupService groupService ;

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

    @GetMapping("/members/add")
    public String getAddMember(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
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

        if (user.getMemberOf() == null){
            response.sendRedirect("/polls");
            return null;  

        }

        model.addAttribute("isAdmin", user.getIsAdmin());



        return "add_member";
    }

    @GetMapping("/members")
    public String getMembers(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject();  

        User user = userService.findUserByUsername(username);

        if (user == null){
            response.sendRedirect("/auth/login");  
            return null;
        }

        Group g = user.getMemberOf();

        if (g == null){
            response.sendRedirect("/polls");
            return null;  
        }

        List<Map<String, Object>> members = groupService.getGroup(g)
        .stream()
        .map(member -> {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", member.getId());
            userData.put("username", member.getUsername());
            return userData; // Ensure you return the map for each member
        })
        .toList(); 

        model.addAttribute("members", members);
        model.addAttribute("isAdmin", user.getIsAdmin());
        model.addAttribute("groupName", g.getGroupName());
        model.addAttribute("currentUser", user.getId());



        return "members";
    }

   
}
