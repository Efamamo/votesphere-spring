package com.itsc.votesphere.group;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itsc.votesphere.group.dto.AddUserDto;
import com.itsc.votesphere.group.dto.CreateGroupDto;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserRepository;
import com.itsc.votesphere.users.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private GroupRepository groupRepository;

    @PostMapping("/groups")
    public ResponseEntity<Map<String, Object>> createGroup(@RequestBody @Valid CreateGroupDto group, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject();
        User user = userService.findUserByUsername(username);

        Map<String, Object> response = new HashMap<>();

        if (user == null) {
            response.put("error", "User not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (!user.getIsAdmin()){
            response.put("error", "User is not admin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        if (user.getGroup() != null){
            response.put("error", "User already have a group.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        try {
            if (groupRepository.existsByGroupName(group.getGroupName())){
                response.put("error", "Group Name Taken");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            groupService.createGroup(group.getGroupName(), user);

           

            response.put("success", "Group created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/groups/members")
    public ResponseEntity<Map<String, Object>> addMember(@RequestBody @Valid AddUserDto u, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject();
        User user = userService.findUserByUsername(username);

        Map<String, Object> response = new HashMap<>();

        if (user == null) {
            response.put("error", "User not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (!user.getIsAdmin()){
            response.put("error", "User is not admin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        

        try {
            Group group = user.getMemberOf();

            if (group == null){
                response.put("error", "User has no group.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User userToAdd = userService.findUserByUsername(u.getUsername());

            

            if (userToAdd == null){
                response.put("error", "User Not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            if (userToAdd .getUsername() == user.getUsername()){
                response.put("error", "You cant add Yourself");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            if (userToAdd.getIsAdmin()){
                response.put("error", "You Cant add another admin");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            if (userToAdd.getMemberOf() != null){
                if (userToAdd.getMemberOf().getGroupName() == group.getGroupName()){
                    response.put("error", "User Is Already in your group");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                }
                response.put("error", "User Belongs to another Group");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            userToAdd.setMemberOf(group);
            userRepository.save(userToAdd);


            response.put("success", "User Added Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }
}
