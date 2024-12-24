package com.itsc.votesphere.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid User user){
        Boolean emailTaken = userService.emailTaken(user.getEmail());

        if (emailTaken){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is Taken");
        }

        Boolean usernameTaken = userService.usernameTaken(user.getUsername());

        if (usernameTaken){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is Taken");

        }

        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid Login loginData){
        User user = userService.findUserByUsername(loginData.getUsername());
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Boolean passwordMatch = userService.checkPassword(loginData.getPassword(), user.getPassword());
        if (!passwordMatch){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logged In Successfu");


    }


    
}
