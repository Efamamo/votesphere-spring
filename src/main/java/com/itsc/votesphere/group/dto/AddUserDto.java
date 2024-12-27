package com.itsc.votesphere.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class AddUserDto {

    @NotBlank(message = "username is required")
    private String username;

}
