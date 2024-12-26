package com.itsc.votesphere.polls.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePollDto {
    

    @NotBlank(message = "question is required")
    private String question;

    private List<String> choices; 
}
