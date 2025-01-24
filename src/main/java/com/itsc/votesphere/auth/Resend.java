package com.itsc.votesphere.auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resend {

    @Email(message = "Email should be valid")
    private String email;

    


}