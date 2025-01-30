package com.itsc.votesphere.contact;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ContactMessage {
  

    @NotBlank(message = "name is required")
    private String name;

    @Email(message = "email is invalid")
    private String email;

    @NotBlank(message = "message is required")
    private String message;


}
