package com.itsc.votesphere.users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.itsc.votesphere.group.Group;
import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.polls.Vote;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Avoid recursion issues in hashCode and equals
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Use ID for hashCode and equals
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private Boolean isAdmin;

    private String otp;

    private Boolean isVerified;

    private LocalDateTime otpExpirationDate;

    // A user can be an admin of one group
    @OneToOne(mappedBy = "admin", fetch = FetchType.LAZY)
    private Group group;

    // A user can be a member of one group
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group memberOf;

    // A user can create multiple polls
    @OneToMany(mappedBy = "pollOwner", fetch = FetchType.LAZY)
    private Set<Poll> polls;

    // A user can cast multiple votes
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Vote> votes;
}
