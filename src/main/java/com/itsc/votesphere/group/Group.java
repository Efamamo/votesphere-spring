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
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Avoid recursion issues in hashCode and equals
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Use ID for hashCode and equals
    private Long id;

    @NotBlank(message = "Group Name is required")
    @Column(nullable = false, unique = true)
    private String groupName;

    // A group can have one admin
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin;

    // A group can have multiple polls
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Poll> polls;

    // A group can have multiple members
    @OneToMany(mappedBy = "memberOf", fetch = FetchType.LAZY)
    private Set<User> members;
}
