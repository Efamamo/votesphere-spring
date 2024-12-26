package com.itsc.votesphere.group;

import java.util.Set;

import com.itsc.votesphere.users.User;
import com.itsc.votesphere.polls.Poll;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor 
@Entity
@Table(name = "`group`")

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Group Name is required")
    @Column(nullable = false, unique = true)
    private String groupName;

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin;  

    @OneToMany(mappedBy = "group")
    private Set<Poll> polls; 

    @OneToMany(mappedBy = "memberOf")
    private Set<User> members; 

   
}
