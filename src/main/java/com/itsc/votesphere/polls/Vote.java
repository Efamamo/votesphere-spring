package com.itsc.votesphere.polls;

import com.itsc.votesphere.users.User;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;  

    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private Poll poll;  

    @ManyToOne
    @JoinColumn(name = "choice_id", referencedColumnName = "id")
    private Choice choice;  

}
