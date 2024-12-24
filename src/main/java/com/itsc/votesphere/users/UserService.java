package com.itsc.votesphere.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public User addUser(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User u = userRepository.save(user);
        return u;

    }

    public Boolean emailTaken(String email){
        User user = userRepository.findByEmail(email);

        return user != null;
    }

    public Boolean usernameTaken(String username){
        User user = userRepository.findByUsername(username);

        return user != null;
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
