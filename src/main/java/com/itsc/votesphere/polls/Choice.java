package com.itsc.votesphere.polls;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;  

    private int count = 0;  

    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private Poll poll;  

}

