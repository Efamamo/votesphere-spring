package com.itsc.votesphere.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;
import jakarta.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, String>>signup(@RequestBody @Valid User user){
        Map<String, String> response = new HashMap<>();

        Boolean emailTaken = userService.emailTaken(user.getEmail());
        if (emailTaken){
            response.put("email", "Email is Taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Boolean usernameTaken = userService.usernameTaken(user.getUsername());
        if (usernameTaken){
            response.put("username", "Username is Taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        userService.addUser(user);
        response.put("success", "Signedup Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid Login loginData){
        Map<String, String> response = new HashMap<>();
        User user = userService.findUserByUsername(loginData.getUsername());
        if (user == null){
            response.put("error", "Unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Boolean passwordMatch = userService.checkPassword(loginData.getPassword(), user.getPassword());
        if (!passwordMatch){
            response.put("error", "Unauthorized");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        response.put("success", "Logged In Successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

   

    
}
