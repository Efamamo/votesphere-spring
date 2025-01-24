package com.itsc.votesphere.users;

import java.util.List;
import java.util.Set;

import com.itsc.votesphere.group.Group;
import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.polls.Vote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(nullable = false, unique = true) // Added constraints
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true) // Added constraints
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private Boolean isAdmin;

    private String otp;

    private Boolean isVerified;

    @OneToOne(mappedBy = "admin")
    private Group group; 

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group memberOf; 

    @OneToMany(mappedBy = "pollOwner")
    private Set<Poll> polls;  

    @OneToMany(mappedBy = "user")
    private List<Vote> votes;
}
